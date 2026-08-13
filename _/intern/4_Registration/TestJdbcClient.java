import javax.sql.DataSource;
import kg.nurtelecom.registration.common.enums.Action;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.simple.JdbcClient;

public class TestJdbcClient {
    public static void main(String[] args) {
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
        JdbcClient jdbcClient = JdbcClient.create(getDataSource());
        Long logId = jdbcClient.sql(sql)
                .param("action", Action.UNKNOWN.getValue())
                .param("username", "TEST_USER")
                .param("entityId", 23)
                .param("dataId", 1)
                .param("changes", "{}")
                .query(Long.class)
                .single();
        System.out.println(logId);
    }
    public static DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/test");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("postgres");
        return dataSourceBuilder.build();
    }
}
