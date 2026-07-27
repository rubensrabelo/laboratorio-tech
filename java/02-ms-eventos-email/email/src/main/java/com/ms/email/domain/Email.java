package com.ms.email.domain;

import java.time.LocalDateTime;

import com.ms.email.domain.enums.EmailStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emails_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "email_to")
    private String to;
    private String subject;
    
    @Column(columnDefinition = "TEXT")
    private String body;
    
    private LocalDateTime sentAt;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;
}
