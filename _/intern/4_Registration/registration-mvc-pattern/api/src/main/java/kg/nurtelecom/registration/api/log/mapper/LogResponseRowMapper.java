package kg.nurtelecom.registration.api.log.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import kg.nurtelecom.registration.common.enums.Action;
import kg.nurtelecom.registration.common.payload.response.log.LogResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class LogResponseRowMapper implements RowMapper<LogResponse> {
    @Override
    public LogResponse mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new LogResponse(
                resultSet.getLong("id"),
                resultSet.getString("log_time"),
                resultSet.getString("username"),
                Action.valueOf(resultSet.getString("action")),
                resultSet.getString("entity"),
                resultSet.getLong("data_id") > 0 ? resultSet.getLong("data_id") : null,
                resultSet.getInt("version") > 0 ? resultSet.getInt("version") : null,
                resultSet.getString("ip_address"),
                resultSet.getString("user_agent"),
                resultSet.getInt("response_code") > 0 ? resultSet.getInt("response_code") : null
        );
    }
}