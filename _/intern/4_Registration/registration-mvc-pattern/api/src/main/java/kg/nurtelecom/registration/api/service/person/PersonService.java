package kg.nurtelecom.registration.api.service.person;

import kg.nurtelecom.registration.common.entity.Person;
import kg.nurtelecom.registration.common.enums.Status;
import kg.nurtelecom.registration.common.payload.request.PersonRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface PersonService {
    List<Person> getAllClients();

    Person save(Person person);

    Person save(String clientData);

    Person findClientById(Long id);

    Person editClient(Long id, PersonRequest personRequest);

    List<Person> searchUsers(Status status, LocalDateTime dateFrom, LocalDateTime dateTo);

    void deleteById(Long id);

}
