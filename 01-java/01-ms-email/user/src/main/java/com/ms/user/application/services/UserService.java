package com.ms.user.application.services;

import org.springframework.stereotype.Service;

import com.ms.user.application.dtos.UserCreateDTO;
import com.ms.user.application.dtos.UserResponseDTO;
import com.ms.user.application.mapper.EmailPayloadMapper;
import com.ms.user.application.mapper.UserMapper;
import com.ms.user.domain.User;
import com.ms.user.infra.queue.producers.EmailProducer;
import com.ms.user.infra.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository repository;
    private final EmailProducer emailProducer;

    public UserService(UserRepository repository, EmailProducer emailProducer) {
        this.repository = repository;
        this.emailProducer = emailProducer;
    }

    @Transactional
    public UserResponseDTO save(UserCreateDTO dtoCreate) {
        User user = UserMapper.toEntity(dtoCreate);
        User savedUser = repository.save(user);

        emailProducer.publishEmailMessage(EmailPayloadMapper.toPayload(savedUser));

        return UserMapper.toResponseDTO(savedUser);
    }
}
