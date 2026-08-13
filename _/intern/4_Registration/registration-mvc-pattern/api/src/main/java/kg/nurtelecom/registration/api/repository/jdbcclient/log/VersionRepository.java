package kg.nurtelecom.registration.api.repository.jdbcclient.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import kg.nurtelecom.registration.api.exception.InternalException;
import kg.nurtelecom.registration.api.log.service.VersionService;
import kg.nurtelecom.registration.api.util.TableInspector;
import kg.nurtelecom.registration.common.enums.Action;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

@Service
public class VersionRepository implements VersionService {
    private final JdbcClient jdbcClient;
    private final ObjectMapper objectMapper;

    public VersionRepository(JdbcClient jdbcClient, ObjectMapper objectMapper) {
        this.jdbcClient = jdbcClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public long save(Action action, String username, long entityId, Long dataId, String changes) {
        String sql = """
                WITH _log AS (
                    INSERT INTO log(log_time, action, username)
                    VALUES (LOCALTIMESTAMP, :action, :username)
                    RETURNING id
                ),
                _version AS (
                    SELECT COALESCE(MAX(version), 0) + 1 AS next
                    FROM version
                    WHERE entity_id = :entityId AND data_id = :dataId
                )
                INSERT INTO public.version(log_id, entity_id, data_id, version, changes)
                SELECT _log.id, :entityId, :dataId, _version.next, :changes::jsonb FROM _log, _version
                RETURNING id;
                """;
        return jdbcClient.sql(sql)
                .param("action", action.name())
                .param("username", username)
                .param("entityId", entityId)
                .param("dataId", dataId)
                .param("changes", changes)
                .query(Long.class)
                .single();
    }

    @Override
    public long findIdByClass(Class<?> clazz) {
        if (classMap.containsKey(clazz)) {
            return classMap.get(clazz);
        }
        Long id = findEntityIdByClass(clazz);
        if (id != null) {
            classMap.put(clazz, id);
            return id;
        }
        id = createEntity(clazz);
        if (id != null) {
            classMap.put(clazz, id);
            return id;
        }
        throw new EntityNotFoundException("Сущность не найдена");
    }

    private Long findEntityIdByClass(Class<?> clazz) {
        try {
            return jdbcClient.sql("SELECT id FROM entity WHERE clazz = :clazz")
                    .param("clazz", clazz.getName())
                    .query(Long.class)
                    .optional()
                    .orElse(null);
        } catch (DataAccessException e) {
            throw new InternalException("Ошибка при поиске сущности");
        }
    }

    private Long createEntity(Class<?> clazz) {
        String name = clazz.getSimpleName();
        String tableName = TableInspector.getTableName(clazz);
        String fields = TableInspector.getStringJsonArray(clazz, objectMapper);

        try {
            return jdbcClient.sql("""
                            INSERT INTO entity(name, clazz, table_name, fields)
                            VALUES (:name, :clazz, :tableName, :fields::jsonb)
                            RETURNING id
                            """)
                    .param("name", name)
                    .param("clazz", clazz.getName())
                    .param("tableName", tableName)
                    .param("fields", fields)
                    .query(Long.class)
                    .single();
        } catch (DataAccessException e) {
            throw new InternalException("Сущность не сохранена");
        }
    }
}
