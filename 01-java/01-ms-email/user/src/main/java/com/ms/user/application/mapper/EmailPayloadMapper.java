package com.ms.user.application.mapper;

import com.ms.user.application.dtos.EmailPayloadDTO;
import com.ms.user.domain.User;

public class EmailPayloadMapper {

    public static EmailPayloadDTO toPayload(User user) {
        return new EmailPayloadDTO(
            user.getId(),
            user.getEmail(),
            "Bem-vindo!",
            "Sua conta foi criada com sucesso."
        );
    }
}

