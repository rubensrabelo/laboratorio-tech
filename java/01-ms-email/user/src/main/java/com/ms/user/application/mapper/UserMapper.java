package com.ms.user.application.mapper;

import com.ms.user.application.dtos.UserCreateDTO;
import com.ms.user.application.dtos.UserResponseDTO;
import com.ms.user.domain.User;

public final class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("Esta eh uma classe utilitaria e nao pode ser instanciada");
    }

    public static User toEntity(UserCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        return new User(dto.name(), dto.email());
    }

    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}
