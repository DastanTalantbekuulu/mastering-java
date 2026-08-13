package kg.nurtelecom.registration.api.repository.jdbctemplate;

import kg.nurtelecom.registration.api.repository.jpa.UserRepository;
import kg.nurtelecom.registration.api.repository.jpa.UserRoleRepository;
import kg.nurtelecom.registration.api.service.WaitingListService;
import kg.nurtelecom.registration.api.service.email.EmailServiceProcessor;
import kg.nurtelecom.registration.common.entity.User;
import kg.nurtelecom.registration.common.entity.UserRole;
import kg.nurtelecom.registration.common.enums.Role;
import kg.nurtelecom.registration.common.enums.Status;
import kg.nurtelecom.registration.common.payload.response.WaitingListResponse;
import kg.nurtelecom.registration.common.utils.StaffAuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class WaitingListJdbcTemplateRepository implements WaitingListService {

    private final JdbcTemplate jdbcTemplate;
    private final EmailServiceProcessor emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleJPA;
    private static final Logger logger = LoggerFactory.getLogger(WaitingListJdbcTemplateRepository.class);


    public WaitingListJdbcTemplateRepository(JdbcTemplate jdbcTemplate, EmailServiceProcessor emailService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserRoleRepository userRoleJPA) {
        this.jdbcTemplate = jdbcTemplate;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userRoleJPA = userRoleJPA;
    }

    public List<WaitingListResponse> getWaitingList(boolean isClient) {
        String insertQuery = """
        INSERT INTO waiting_list (id, first_name, last_name, email, status, role)
        SELECT id, first_name, last_name, email, status, role
        FROM person_data
        WHERE status = ?
        ON CONFLICT (id) DO NOTHING
    """;

        jdbcTemplate.update(insertQuery, Status.PENDING.name());

        String sql;
        if (isClient) {
            sql = "SELECT * FROM waiting_list WHERE role = 'CLIENT'";
        } else {
            sql = "SELECT * FROM waiting_list WHERE role in ('ADMIN', 'MANAGER', 'REGISTRAR')";
        }
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WaitingListResponse.class));
    }

    public void approvePerson(Long personId) {
        String updateQuery = "UPDATE person_data SET status = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(updateQuery, Status.APPROVED.name(), personId);

        if (rowsUpdated > 0) {
            try {
                String personDataQuery = "SELECT id, first_name, email, role, status FROM person_data WHERE id = ?";
                Map<String, Object> personData = jdbcTemplate.queryForMap(personDataQuery, personId);

                String role = (String) personData.get("role");
                if (!Role.CLIENT.name().equals(role)) {

                    String username = StaffAuthUtils.generateUsername((String) personData.get("first_name"));
                    String password = StaffAuthUtils.generatePassword();

                    User user = new User();
                    user.setUserName(username);
                    user.setPassword(bCryptPasswordEncoder.encode(password));
                    user.setRole(mapEnumRoleToDatabaseRole(Role.valueOf(role)));
                    user.setActivated(mapEnumStatusToBoolean((String) personData.get("status")));

                    emailService.sendCredentialsEmail((String) personData.get("email"), username, password);

                    userRepository.save(user);

                    String deleteQuery = "DELETE FROM waiting_list WHERE id = ?";
                    jdbcTemplate.update(deleteQuery, personId);

                } else {
                    String deleteQuery = "DELETE FROM waiting_list WHERE id = ?";
                    jdbcTemplate.update(deleteQuery, personId);
                }
            } catch (Exception e) {
                logger.error("Error processing person approval: {}", e.getMessage(), e);
            }
        } else {
            logger.warn("No rows updated for id: {}", personId);
        }
    }


    public void rejectPerson(Long personId) {
        String updateQuery = "UPDATE person_data SET status = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(updateQuery, Status.REJECTED.name(), personId);

        if (rowsUpdated > 0) {
            String deleteQuery = "DELETE FROM waiting_list WHERE id = ?";
            jdbcTemplate.update(deleteQuery, personId);
        } else {
            logger.warn("Person not found with id: {}", personId);
        }
    }

    private boolean mapEnumStatusToBoolean(String status) {
        return Status.APPROVED.name().equals(status);
    }

    private UserRole mapEnumRoleToDatabaseRole(Role staffRole) {
        return userRoleJPA.findByName(staffRole.name())
                .orElseThrow(() -> new RuntimeException("Role not found: " + staffRole.name()));
    }
}
