package usecases

import (
	"rest-api-2/internal/models"
	"rest-api-2/internal/repositories"

	"github.com/google/uuid"
)

type CreateUserInputDTO struct {
	Name  string `json:"name"`
	Email string `json:"email"`
}

type UserUseCase struct {
	repo repositories.UserRepository
}

func NewUserUseCase(repo repositories.UserRepository) *UserUseCase {
	return &UserUseCase{
		repo: repo,
	}
}

func (uc *UserUseCase) Create(input CreateUserInputDTO) (models.User, error) {
	newUser := models.User{
		ID:    uuid.New(),
		Name:  input.Name,
		Email: input.Email,
	}

	err := uc.repo.Add(newUser)
	if err != nil {
		return models.User{}, err
	}

	return newUser, nil
}

func (uc *UserUseCase) List() []models.User {
	return uc.repo.GetAll()
}
