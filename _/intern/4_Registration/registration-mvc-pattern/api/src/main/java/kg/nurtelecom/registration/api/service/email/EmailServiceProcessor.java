package kg.nurtelecom.registration.api.service.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceProcessor implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceProcessor(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendCredentialsEmail(String recipientEmail, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom("okulhanmuratbekov@gmail.com");
            message.setTo(recipientEmail);
            message.setSubject("Your credentials");
            message.setText(String.format(
                    "Login: %s\nPassword: %s\n\nBest regards,\nYour Company.", username, password
            ));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email to " + recipientEmail + ": " + e.getMessage(), e);
        }
    }
}
