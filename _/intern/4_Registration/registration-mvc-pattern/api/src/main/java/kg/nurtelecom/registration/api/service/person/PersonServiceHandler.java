package kg.nurtelecom.registration.api.service.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import kg.nurtelecom.registration.api.log.annotation.Versioned;
import kg.nurtelecom.registration.api.repository.jpa.PersonRepository;
import kg.nurtelecom.registration.api.service.image.ImageService;
import kg.nurtelecom.registration.common.entity.ImageData;
import kg.nurtelecom.registration.common.entity.Person;
import kg.nurtelecom.registration.common.enums.Action;
import kg.nurtelecom.registration.common.enums.Status;
import kg.nurtelecom.registration.common.payload.request.PersonRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PersonServiceHandler implements PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonServiceHandler.class);

    private final PersonRepository clientRepository;
    private final ImageService imageService;

    public PersonServiceHandler(PersonRepository clientRepository, ImageService imageService) {
        this.clientRepository = clientRepository;
        this.imageService = imageService;
    }

    public Person save(Person person) {
        return clientRepository.save(person);
    }

    @Transactional
    @Versioned(entity = Person.class, action = Action.INSERT)
    public Person save(String personData) {
        try {
            PersonRequest personRequest = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .readValue(personData, PersonRequest.class);

            LocalDate minIssueDate = personRequest.dateOfBirth().plusYears(16);
            if (personRequest.dateOfIssue().isBefore(minIssueDate)) {
                throw new IllegalArgumentException("Passport can only be issued at 16 years old or later");
            }
            if (personRequest.dateOfExpiry().isBefore(personRequest.dateOfIssue())) {
                throw new IllegalArgumentException("Expiry date must be after issue date");
            }
            if (clientRepository.findByDocumentId(personRequest.documentId()) != null) {
                throw new IllegalArgumentException("Client with this document id: " + personRequest.documentId() + " already exists");
            }

            if (!isValidEmail(personRequest.email())) {
                throw new IllegalArgumentException("Email must be from domain @gmail.com");
            }

            validateFullName(personRequest.firstName(), personRequest.lastName(), personRequest.middleName());

            Person person = new Person();
            person.setFirstName(personRequest.firstName());
            person.setLastName(personRequest.lastName());
            person.setMiddleName(personRequest.middleName());
            person.setPersonGender(personRequest.personGender());
            person.setNationality(personRequest.nationality());
            person.setDateOfBirth(personRequest.dateOfBirth());
            person.setIdentificationNumber(personRequest.identificationNumber());
            person.setDateOfIssue(personRequest.dateOfIssue());
            person.setDateOfExpiry(personRequest.dateOfExpiry());
            person.setDocumentId(personRequest.documentId());
            person.setIssuingAuthority(personRequest.issuingAuthority());
            person.setRole(personRequest.role());
            person.setStatus(Status.PENDING);
            person.setRegionId(personRequest.regionId());
            person.setDistrictId(personRequest.districtId());
            person.setCityId(personRequest.cityId());
            person.setStreetId(personRequest.streetId());
            person.setEmail(personRequest.email());
            person.setHouse(personRequest.house());
            person.setApartment(personRequest.apartment());

            Person savedPerson = clientRepository.save(person);

            return savedPerson;
        }catch (InvalidFormatException e) {
          throw new IllegalArgumentException("Invalid format");
        } catch (IllegalArgumentException e) {
            log.warn("Validation failed: {}", e.getMessage());
            throw e;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Ошибка: Этот идентификационный номер уже зарегистрирован.");
        } catch (Exception e) {
            log.error("Error occurred: ", e);
            throw new IllegalArgumentException("Failed to save person", e);
        }
    }

    @Override
    public List<Person> getAllClients() {
        List<Person> personList = clientRepository.findAll();
        if (personList.isEmpty()) {
            throw new EntityNotFoundException("Клиенты не найдены");
        }
        return personList;
    }

    @Override
    public Person findClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Person with ID " + id + " not found"));
    }

    @Versioned(entity = Person.class, entityId = "#parameters[0]", action = Action.UPDATE)
    public Person editClient(Long id, PersonRequest personRequest) {
        Person existingPerson = findClientById(id);
        if (existingPerson == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }

        Person updatedPerson = new Person();
        updatedPerson.setId(existingPerson.getId());
        updatedPerson.setFirstName(personRequest.firstName());
        updatedPerson.setLastName(personRequest.lastName());
        updatedPerson.setMiddleName(personRequest.middleName());
        updatedPerson.setPersonGender(personRequest.personGender());
        updatedPerson.setNationality(personRequest.nationality());
        updatedPerson.setDateOfBirth(personRequest.dateOfBirth());
        updatedPerson.setIdentificationNumber(personRequest.identificationNumber());
        updatedPerson.setDateOfIssue(personRequest.dateOfIssue());
        updatedPerson.setDateOfExpiry(personRequest.dateOfExpiry());
        updatedPerson.setDocumentId(personRequest.documentId());
        updatedPerson.setIssuingAuthority(personRequest.issuingAuthority());
        updatedPerson.setRole(personRequest.role());
        updatedPerson.setStatus(personRequest.status());
        updatedPerson.setRegionId(personRequest.regionId());
        updatedPerson.setDistrictId(personRequest.districtId());
        updatedPerson.setCityId(personRequest.cityId());
        updatedPerson.setStreetId(personRequest.streetId());
        updatedPerson.setHouse(personRequest.house());
        updatedPerson.setApartment(personRequest.apartment());
        updatedPerson.setEmail(personRequest.email());


        return clientRepository.save(updatedPerson);
    }

    @Override
    public List<Person> searchUsers(Status status, LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom == null || dateTo == null) {
            return clientRepository.findByStatus(status);
        } else {
            return clientRepository.findByStatusAndCtsBetween(status, dateFrom, dateTo);
        }
    }

    @Versioned(entity = Person.class, entityId = "#parameters[0]", action = Action.DELETE)
    public void deleteById(Long id) {
        Optional<Person> person = clientRepository.findById(id);

        if (person.isPresent()) {
            List<ImageData> images = person.get().getImages();
            for (ImageData image : images) {
                deleteImageFile(image.getFilePath());
            }
            clientRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Person with ID " + id + " not found");
        }
    }


    private void deleteImageFile(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            try {
                Path path = Paths.get(filePath);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to delete image file at path: " + filePath, e);
            }
        } else {
            throw new IllegalArgumentException("Invalid file path provided.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void validateFullName(String firstName, String lastName, String middleName) {

        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("First and last name must not be empty");
        }

        String namePattern = "^[a-zA-Zа-яА-ЯёЁ]{2,30}$";

        if (!firstName.matches(namePattern) || !lastName.matches(namePattern) || !middleName.matches(namePattern)) {
            throw new IllegalArgumentException("First name must contain only letters and be between 2 and 30 characters long");
        }
    }
}
