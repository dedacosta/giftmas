package net.mephys.giftmas.service;

import net.mephys.giftmas.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendSimpleEmail(Email email) {
       return sendSimpleEmail(email.getAddress(),email.getSubject(),email.getContent());
    }

    public boolean sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("santa@gmail.com"); // Replace with your email
        try {
            mailSender.send(message);
        } catch (MailException e) {
            return false;
        }
        return true;
    }
}
