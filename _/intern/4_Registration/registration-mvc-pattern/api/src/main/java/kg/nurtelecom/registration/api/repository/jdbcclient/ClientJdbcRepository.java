package kg.nurtelecom.registration.api.repository.jdbcclient;

import jakarta.persistence.EntityNotFoundException;
import kg.nurtelecom.registration.common.entity.Person;
import kg.nurtelecom.registration.common.enums.Status;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ClientJdbcRepository {
    private final JdbcClient jdbcClient;

    public ClientJdbcRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private static final String SELECT_ALL_CLIENTS = "SELECT * FROM person";
    private static final String SELECT_CLIENT_BY_ID = "SELECT * FROM person WHERE id = :id";

    public List<Person> findAll() {
        return jdbcClient.sql(SELECT_ALL_CLIENTS)
                .query(Person.class)
                .list();
    }

    public Person findById(Long id) {
        return jdbcClient.sql(SELECT_CLIENT_BY_ID)
                .param("id", id)
                .query(Person.class)
                .optional()
                .orElseThrow(() -> new EntityNotFoundException("Client with ID " + id + " not found."));
    }

    public List<Person> findByStatus(Status status) {
        return jdbcClient.sql("SELECT * FROM person WHERE status = :status")
                .param("status", status)
                .query(Person.class)
                .list();
    }

    public int save(Person person) {
        String sql = """
                INSERT INTO person (first_name, status_id, cts) 
                VALUES (:firstName, :statusId, :cts)
                """;
        return jdbcClient.sql(sql)
                .param("firstName", person.getFirstName())
                .param("statusId", person.getStatus())
                .param("cts", Timestamp.valueOf(person.getCts()))
                .update();
    }

    public int update(Person person) {
        String sql = """
                UPDATE person 
                SET first_name = :firstName, status_id = :statusId, cts = :cts 
                WHERE id = :id
                """;
        return jdbcClient.sql(sql)
                .param("id", person.getId())
                .param("firstName", person.getFirstName())
                .param("status", person.getStatus())
                .param("cts", Timestamp.valueOf(person.getCts()))
                .update();
    }

    public int deleteById(Long id) {
        return jdbcClient.sql("DELETE FROM person WHERE id = :id")
                .param("id", id)
                .update();
    }
}
