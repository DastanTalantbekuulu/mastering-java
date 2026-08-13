package kg.nurtelecom.registration.api.repository.jpa;

import kg.nurtelecom.registration.common.entity.Person;
import kg.nurtelecom.registration.common.enums.Status;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @EntityGraph(attributePaths = "images")
    List<Person> findAll();

    @Query("SELECT c FROM Person c " +
            "WHERE (:status IS NULL OR c.status = :status) " +
            "AND (c.cts BETWEEN :startDate AND :endDate)")
    List<Person> findByStatusAndCtsBetween(@Param("status") Status status,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    Person findByDocumentId(String documentId);

    List<Person> findByStatus(Status status);

}
