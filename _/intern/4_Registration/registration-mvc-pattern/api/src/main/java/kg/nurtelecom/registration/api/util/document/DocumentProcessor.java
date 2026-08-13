package kg.nurtelecom.registration.api.util.document;

import java.util.Map;

public interface DocumentProcessor {
    byte[] generateAgreementDocument(String documentPath, Map<String, String> replacements, String name);
}
