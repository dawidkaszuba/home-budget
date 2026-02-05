package pl.dawidkaszuba.homebudget.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.db.UserCredential;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleEmailService implements pl.dawidkaszuba.homebudget.service.EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendActivationEmail(UserCredential credential) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(credential.getEmail());
        message.setSubject("Aktywuj konto w Home-Budget");
        message.setText("""
            Zostałeś zaproszony do aplikacji Home-Budget.
            Kliknij link aby ustawić hasło:
            http://localhost:8080/activate?token=%s
            """.formatted(credential.getActivationToken()));

        try {
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Could not send an email with invitation to user {}", credential.getEmail());
        }
    }
}
