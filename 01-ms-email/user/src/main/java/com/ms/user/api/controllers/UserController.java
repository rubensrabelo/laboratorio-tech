package com.ms.user.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.user.application.dtos.UserCreateDTO;
import com.ms.user.application.dtos.UserResponseDTO;
import com.ms.user.application.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@RequestBody @Valid UserCreateDTO dtoCreate) {
        UserResponseDTO dtoResponse = service.save(dtoCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }
}
