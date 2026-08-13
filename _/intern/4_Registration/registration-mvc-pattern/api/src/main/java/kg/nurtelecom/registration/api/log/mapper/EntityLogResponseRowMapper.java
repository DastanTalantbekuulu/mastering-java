package kg.nurtelecom.registration.api.log.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import kg.nurtelecom.registration.common.enums.Action;
import kg.nurtelecom.registration.common.payload.response.log.EntityLogResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EntityLogResponseRowMapper implements RowMapper<EntityLogResponse> {
    public EntityLogResponse mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new EntityLogResponse(
                resultSet.getLong("id"),
                resultSet.getString("log_time"),
                resultSet.getString("username"),
                Action.valueOf(resultSet.getString("action")),
                resultSet.getString("entity"),
                resultSet.getLong("data_id") > 0 ? resultSet.getLong("data_id") : null,
                resultSet.getInt("version") > 0 ? resultSet.getInt("version") : null
        );
    }
}
