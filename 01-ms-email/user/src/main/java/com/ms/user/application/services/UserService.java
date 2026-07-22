package com.ms.user.application.services;

import org.springframework.stereotype.Service;

import com.ms.user.application.dtos.UserCreateDTO;
import com.ms.user.application.dtos.UserResponseDTO;
import com.ms.user.application.mapper.UserMapper;
import com.ms.user.domain.User;
import com.ms.user.infra.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UserResponseDTO save(UserCreateDTO dtoCreate) {
        User user = UserMapper.toEntity(dtoCreate);
        User savedUser = repository.save(user);
        return UserMapper.toResponseDTO(savedUser);
    }
}
