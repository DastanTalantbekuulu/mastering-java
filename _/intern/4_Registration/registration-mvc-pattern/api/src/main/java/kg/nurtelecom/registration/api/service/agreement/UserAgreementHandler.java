package kg.nurtelecom.registration.api.service.agreement;

import java.util.HashMap;
import java.util.Map;
import kg.nurtelecom.registration.api.service.person.PersonService;
import kg.nurtelecom.registration.api.util.document.DocumentProcessor;
import kg.nurtelecom.registration.common.entity.Person;
import org.springframework.stereotype.Service;

@Service
public class UserAgreementHandler implements UserAgreementService {

    private static final String NAME = "user_agreement";
    private static final String TYPE = ".docx";
    private static final String DOCX = NAME + TYPE;
    private static final String TEMPLATE = "{{name}}";
    private static final Map<String, String> REPLACEMENTS = new HashMap<>() {{
        put(TEMPLATE, "");
    }};
    private final DocumentProcessor documentProcessor;
    private final PersonService personService;
    public UserAgreementHandler(DocumentProcessor documentProcessor, PersonService personService) {
        this.documentProcessor = documentProcessor;
        this.personService = personService;
    }

    public byte[] getUserAgreement(Long peronId) {
        Person person = personService.findClientById(peronId);
        String fullName = person.getFirstName() + " " + person.getLastName() + " " + person.getMiddleName();

        REPLACEMENTS.put(TEMPLATE, fullName);
        final String documentId = NAME + "_" + peronId + TYPE;
        byte[] document = documentProcessor.generateAgreementDocument(DOCX, REPLACEMENTS, documentId);
        person.setDocumentId(documentId);
        personService.save(person);
        return document;
    }
}
