package usecases

import "rest-api-2/internal/models"

type UserUseCaseInterface interface {
	Create(input CreateUserInputDTO) (models.User, error)
	List() []models.User
}
