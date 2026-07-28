package repositories

import "rest-api-2/internal/models"

type UserRepository interface {
	Add(user models.User) error
	GetAll() []models.User
}
