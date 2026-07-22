package com.ms.user.infra.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.user.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}
