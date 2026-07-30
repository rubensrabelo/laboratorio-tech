package service

import (
	"events-api/internal/user/domain"
	"events-api/internal/user/dto"
)

type UserService interface {
	Register(req dto.RegisterRequest) (*domain.User, error)
	GetByEmail(email string) (*domain.User, error)
	GetById(id int) (*domain.User, error)
	Update(id int, req dto.UpdateUserRequest) (*domain.User, error)
	Delete(id int) error
	ListAll() ([]domain.User, error)
}
