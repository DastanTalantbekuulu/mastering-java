package kg.nurtelecom.registration.api.repository.jdbctemplate;

import kg.nurtelecom.registration.common.entity.Person;
import kg.nurtelecom.registration.common.enums.Status;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClientJdbcTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public ClientJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Person> CLIENT_ROW_MAPPER = new RowMapper<>() {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            person.setId(rs.getLong("id"));
            person.setFirstName(rs.getString("first_name"));
            person.setLastName(rs.getString("last_name"));
            person.setMiddleName(rs.getString("middle_name"));
            person.setPersonGender(rs.getString("person_gender"));
            person.setNationality(rs.getString("nationality"));
            person.setDateOfBirth(rs.getString("date_of_birth") != null
                    ? LocalDate.parse(rs.getString("date_of_birth"))
                    : null);
            person.setIdentificationNumber(rs.getString("identification_number"));
            person.setDateOfIssue(rs.getString("date_of_issue") != null
                    ? LocalDate.parse(rs.getString("date_of_issue"))
                    : null);
            person.setDateOfExpiry(rs.getString("date_of_expiry") != null
                    ? LocalDate.parse(rs.getString("date_of_expiry"))
                    : null);
            person.setDocumentId(rs.getString("document_id"));
            person.setIssuingAuthority(rs.getString("issuing_authority"));
            person.setStatus(Status.valueOf(rs.getObject("status").toString()));
            person.setCts(rs.getTimestamp("cts") != null
                    ? rs.getTimestamp("cts").toLocalDateTime()
                    : null);
            person.setUts(rs.getTimestamp("uts") != null
                    ? rs.getTimestamp("uts").toLocalDateTime()
                    : null);
            person.setRegionId(rs.getObject("region_id", Integer.class));
            person.setDistrictId(rs.getObject("district_id", Integer.class));
            person.setCityId(rs.getObject("city_id", Integer.class));
            person.setStreetId(rs.getObject("street_id", Integer.class));
            person.setHouse(rs.getObject("houseNumber", Integer.class));
            person.setApartment(rs.getObject("apartmentNumber", Integer.class));

            return person;

        }
    };

    public List<Person> findAll() {
        String sql = "SELECT * FROM client";
        return jdbcTemplate.query(sql, CLIENT_ROW_MAPPER);
    }

    public Person findById(Long id) {
        String sql = "SELECT * FROM client WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, CLIENT_ROW_MAPPER, id);
    }

    public List<Person> findByStatusId(Integer statusId) {
        String sql = "SELECT * FROM client WHERE status_id = ?";
        return jdbcTemplate.query(sql, CLIENT_ROW_MAPPER, statusId);
    }

    public int save(Person person) {
        String sql = "INSERT INTO client (full_name, status_id, cts) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, person.getFirstName(), person.getLastName(), person.getMiddleName(), person.getStatus(), person.getCts());
    }

    public int update(Person person) {
        String sql = "UPDATE client SET full_name = ?, status_id = ?, cts = ? WHERE id = ?";
        return jdbcTemplate.update(sql, person.getFirstName(), person.getLastName(), person.getMiddleName(), person.getStatus(), person.getCts(), person.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM client WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
