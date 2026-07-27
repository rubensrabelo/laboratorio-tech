package com.ms.email.application.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.email.application.dtos.EmailRequestDTO;
import com.ms.email.domain.Email;
import com.ms.email.domain.enums.EmailStatus;
import com.ms.email.infra.repositories.EmailRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public void sendEmail(EmailRequestDTO emailRequest) {
        Email email = new Email();
        email.setTo(emailRequest.to());
        email.setSubject(emailRequest.subject());
        email.setBody(emailRequest.body());
        email.setSentAt(LocalDateTime.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());

            mailSender.send(message);

            email.setStatus(EmailStatus.SENT);
            log.info("E-mail enviado com sucesso para: {}", email.getTo());
        } catch (Exception e) {
            email.setStatus(EmailStatus.ERROR);
            log.error("Falha ao enviar e-mail via SMTP para {}: {}", email.getTo(), e.getMessage());
        } finally {
            emailRepository.save(email);
        }
    }
}

