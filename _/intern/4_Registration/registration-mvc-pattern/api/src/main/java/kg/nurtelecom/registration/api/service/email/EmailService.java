package kg.nurtelecom.registration.api.service.email;

public interface EmailService {
    void sendCredentialsEmail(String recipientEmail, String username, String password);
}
