package service

import "events-api/internal/auth/dto"

type AuthService interface {
	Login(req dto.LoginRequest) (*dto.LoginResponse, error)
}
