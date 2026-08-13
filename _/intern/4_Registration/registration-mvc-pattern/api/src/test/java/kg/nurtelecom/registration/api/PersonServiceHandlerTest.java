package kg.nurtelecom.registration.api;

import kg.nurtelecom.registration.api.repository.jpa.PersonRepository;
import kg.nurtelecom.registration.api.repository.jpa.UserRepository;
import kg.nurtelecom.registration.api.repository.jpa.UserRoleRepository;
import kg.nurtelecom.registration.api.service.email.EmailServiceProcessor;
import kg.nurtelecom.registration.api.service.agreement.UserAgreementService;
import kg.nurtelecom.registration.api.service.person.PersonServiceHandler;
import kg.nurtelecom.registration.common.entity.Person;
import kg.nurtelecom.registration.common.enums.Status;
import kg.nurtelecom.registration.common.payload.request.PersonRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceHandlerTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private UserAgreementService userAgreementService;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private EmailServiceProcessor emailService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PersonServiceHandler personServiceHandler;

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setStatus(Status.PENDING);
    }

    @Test
    void testSavePerson() {
        when(personRepository.save(any(Person.class))).thenReturn(person);
        Person savedPerson = personServiceHandler.save(person);
        assertNotNull(savedPerson);
        assertEquals("John", savedPerson.getFirstName());
    }

    @Test
    void testFindClientById_NotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> personServiceHandler.findClientById(1L));
        assertTrue(exception.getMessage().contains("not found"));
    }

//    @Test
//    void testEditClient_NotFound() {
//        when(personRepository.findById(1L)).thenReturn(Optional.empty());
//        PersonRequest request = mock(PersonRequest.class);
//        assertThrows(ResponseStatusException.class, () -> personServiceHandler.editClient(1L, request));
//    }

    @Test
    void testDeleteById_Success() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        doNothing().when(personRepository).deleteById(1L);
        assertDoesNotThrow(() -> personServiceHandler.deleteById(1L));
        verify(personRepository, times(1)).deleteById(1L);
    }
}
