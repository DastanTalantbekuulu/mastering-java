package kg.nurtelecom.registration.api.repository.jdbcnative;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kg.nurtelecom.registration.api.exception.BadRequestException;
import kg.nurtelecom.registration.api.exception.InternalException;
import kg.nurtelecom.registration.api.log.mapper.EntityLogResponseRowMapper;
import kg.nurtelecom.registration.api.log.mapper.LogResponseRowMapper;
import kg.nurtelecom.registration.api.service.log.LogQueryService;
import kg.nurtelecom.registration.api.util.ClassUtil;
import kg.nurtelecom.registration.common.entity.Person;
import kg.nurtelecom.registration.common.payload.request.log.SimplePageRequest;
import kg.nurtelecom.registration.common.payload.response.log.EntityLogResponse;
import kg.nurtelecom.registration.common.payload.response.log.LogResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import static kg.nurtelecom.registration.api.repository.jdbcnative.LogQueryBuilder.SELECT_BY_ENTITY_AND_ENTITY_ID;
import static kg.nurtelecom.registration.api.repository.jdbcnative.LogQueryBuilder.SELECT_BY_ID;
import static kg.nurtelecom.registration.api.repository.jdbcnative.LogQueryBuilder.SELECT_CHANGES;
import static kg.nurtelecom.registration.api.repository.jdbcnative.LogQueryBuilder.SELECT_CHANGE_COUNT;
import static kg.nurtelecom.registration.api.repository.jdbcnative.LogQueryBuilder.SELECT_PAGE;

@Service
public class LogQueryJdbcNativeRepository implements LogQueryService {

    private static final Logger logger = LoggerFactory.getLogger(LogQueryJdbcNativeRepository.class);

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    private final LogResponseRowMapper logResponseRowMapper;
    private final EntityLogResponseRowMapper entityLogResponseRowMapper;
    private final PagedModel<LogResponse> emptyPage;
    private final ObjectMapper mapper;

    public LogQueryJdbcNativeRepository(
            LogResponseRowMapper logResponseRowMapper,
            EntityLogResponseRowMapper entityLogResponseRowMapper, ObjectMapper mapper
    ) {
        this.logResponseRowMapper = logResponseRowMapper;
        this.entityLogResponseRowMapper = entityLogResponseRowMapper;
        this.mapper = mapper;
        emptyPage = new PagedModel<>(new PageImpl<>(Collections.emptyList()));
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            if (connection == null) {
                throw new InternalException("Connection is null");
            }
        } catch (SQLException sqle) {
            throw new InternalException("error connect to DB");
        }
        return connection;
    }

    private void closeResources(ResultSet resultSet, PreparedStatement statement, Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException sqle) {
            throw new InternalException("Error closing resources}");
        }
    }

    @Override
    public PagedModel<LogResponse> findAll(SimplePageRequest request) {
        if (request == null) {
            return findAll();
        }
        int page = request.getPage();
        int size = request.getSize();
        if (!request.hasWhere()) {
            return findAll(page, size);
        }
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String where = LogQueryBuilder.buildWhere(request);
            int offset = getOffset(page, size);
            String query = LogQueryBuilder.buildQuery(request, where);
            statement = connection.prepareStatement(query);
            int index = LogQueryBuilder.applyParams(statement, request);
            statement.setInt(++index, size);
            statement.setInt(++index, offset);
            resultSet = statement.executeQuery();
            return buildPage(resultSet, size, page, offset);
        } catch (SQLException sqle) {
            throw new InternalException(sqle.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
    }

    @Override
    public PagedModel<LogResponse> findAll() {
        return findAll(0, 5);
    }

    @Override
    public PagedModel<LogResponse> findAll(int page, int size) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int offset = getOffset(page, size);
            statement = connection.prepareStatement(SELECT_PAGE);
            statement.setInt(1, size);
            statement.setInt(2, offset);
            resultSet = statement.executeQuery();
            return buildPage(resultSet, size, page, offset);
        } catch (SQLException sqle) {
            throw new InternalException(sqle.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
    }

    public int getOffset(int page, int size) {
        if (size < 5 || 20 < size) {
            throw new BadRequestException("размер (size) должен быть от 5 до 20");
        }
        if (page < 0) {
            throw new BadRequestException("страница (page) не может быть отрицательным");
        }
        return page * size;
    }

    public PagedModel<LogResponse> buildPage(ResultSet resultSet, int size, int page, int offset) throws SQLException {
        if (!resultSet.next()) {
            return emptyPage;
        }
        List<LogResponse> logResponses = new ArrayList<>(size);
        int count = resultSet.getInt("count");
        do {
            logResponses.add(logResponseRowMapper.mapRow(resultSet, 0));
        } while (resultSet.next());
        return new PagedModel<>(new PageImpl<>(logResponses, PageRequest.of(page, size), count));
    }

    @Override
    public List<EntityLogResponse> findAllByEntityAndEntityId(String entity, Long entityId) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_ENTITY_AND_ENTITY_ID);
            statement.setString(1, entity);
            statement.setLong(2, entityId);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new EntityNotFoundException();
            }
            List<EntityLogResponse> logs = new ArrayList<>();
            do {
                logs.add(entityLogResponseRowMapper.mapRow(resultSet, 0));
            } while (resultSet.next());
            return logs;
        } catch (SQLException sqle) {
            throw new InternalException(sqle.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
    }

    @Override
    public LogResponse findById(Long id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return logResponseRowMapper.mapRow(resultSet, 0);
            }
            throw new EntityNotFoundException("Не удалось найти");
        } catch (SQLException sqle) {
            throw new InternalException(sqle.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
    }

    @Override
    public Object findChangesById(Long id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {

            statement = connection.prepareStatement(SELECT_CHANGES);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String changes =  resultSet.getString("changes");
                String clazz =  resultSet.getString("clazz");
                Class<?> aClass = ClassUtil.getClass(clazz);
                Object value = mapper.readValue(changes, aClass);
                return value;
            }
            throw new EntityNotFoundException();
        } catch (SQLException sqle) {
            logger.error(sqle.getMessage());
            throw new InternalException("SQLException: "+sqle.getMessage());
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException: " + e.getMessage());
            throw new InternalException(e.getMessage());
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException: " + e.getMessage());
            throw new InternalException(e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
    }

    @Override
    public String findCountLogByEntity(String entity) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_CHANGE_COUNT);
            statement.setString(1, entity);
            resultSet = statement.executeQuery();
            String result = resultSet.next() ? resultSet.getString(1) : null;
            if (result == null) {
                throw new EntityNotFoundException();
            }
            return result;
        } catch (SQLException sqle) {
            throw new InternalException(sqle.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
    }
}
