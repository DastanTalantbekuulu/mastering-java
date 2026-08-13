package kg.nurtelecom.registration.api.repository.jdbcclient.log;

import kg.nurtelecom.registration.api.exception.InternalException;
import kg.nurtelecom.registration.api.log.service.SecurityLoggingService;
import kg.nurtelecom.registration.common.enums.Action;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityLoggingRepository implements SecurityLoggingService {
    private final JdbcClient jdbcClient;

    public SecurityLoggingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public long save(Action action, String username, String ipAddress, String userAgent, int responseCode) {
        String sql = """
                WITH _log AS (
                        INSERT INTO log(log_time, action, username)
                VALUES (LOCALTIMESTAMP, :action, :username)
                RETURNING id
                            )
                INSERT INTO public.log_security(log_id, ip_address, user_agent, response_code)
                SELECT _log.id, :ipAddress::inet, :userAgent, :responseCode
                FROM _log
                RETURNING id;
                """;
        try {
            return jdbcClient.sql(sql)
                    .param("action", action.name())
                    .param("username", username)
                    .param("ipAddress", ipAddress)
                    .param("userAgent", userAgent)
                    .param("responseCode", responseCode)
                    .query(Long.class)
                    .single();
        } catch (DataAccessException e) {
            throw new InternalException("Ошибка при сохранении логирования: " + e.getMessage());
        }
    }
}
