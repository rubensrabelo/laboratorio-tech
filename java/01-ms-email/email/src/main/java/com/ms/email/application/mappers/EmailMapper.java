package com.ms.email.application.mappers;

import com.ms.email.application.dtos.EmailPayloadDTO;
import com.ms.email.domain.Email;

public class EmailMapper {

    public static Email toEntity(EmailPayloadDTO dto) {
        Email email = new Email();
        email.setUserId(dto.id());
        email.setEmailTo(dto.emailTo());
        email.setSubject(dto.subject());
        email.setText(dto.text());
        return email;
    }
}

