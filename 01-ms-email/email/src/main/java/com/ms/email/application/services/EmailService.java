package com.ms.email.application.services;

import com.ms.email.application.dtos.EmailPayloadDTO;
import com.ms.email.application.mappers.EmailMapper;
import com.ms.email.domain.Email;
import com.ms.email.domain.enums.StatusEmail;
import com.ms.email.infra.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class EmailService {

    private final EmailRepository repository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailService(EmailRepository repository, JavaMailSender mailSender) {
        this.repository = repository;
        this.mailSender = mailSender;
    }

    @Transactional
    public Email sendEmail(EmailPayloadDTO payload) {
        Email email = EmailMapper.toEntity(payload);
        try {
            email.setEmailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            
            mailSender.send(message);
            email.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            System.out.println(e);
            email.setStatusEmail(StatusEmail.ERROR);
        }
        return repository.save(email);
    }
}


