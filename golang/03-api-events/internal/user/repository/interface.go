package repository

import "events-api/internal/user/domain"

type UserRepository interface {
	Create(user *domain.User) error
	FindByEmail(email string) (*domain.User, error)
	FindById(id int) (*domain.User, error)
	Update(user *domain.User) error
	Delete(id int) error
	FindAll() ([]domain.User, error)
}
