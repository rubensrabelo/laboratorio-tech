package usecases

import (
	"errors"
	"rest-api-2/internal/models"
	"rest-api-2/internal/repositories"
	"testing"
)

func TestUserUseCase_Create_Success(t *testing.T) {
	repo := repositories.NewMemoryUserRepository()
	useCase := NewUserUseCase(repo)

	input := CreateUserInputDTO{
		Name:  "Alice Doe",
		Email: "alice@example.com",
	}

	user, err := useCase.Create(input)
	if err != nil {
		t.Fatalf("esperava sucesso na criacao, mas obteve erro: %v", err)
	}

	if user.ID.String() == "" {
		t.Error("esperava que um UUID valido fosse gerado para o usuario")
	}

	if user.Name != input.Name || user.Email != input.Email {
		t.Errorf("dados salvos divergem do input. Obtido: %s, %s", user.Name, user.Email)
	}
}

func TestUserUseCase_Create_RequiredFields(t *testing.T) {
	repo := repositories.NewMemoryUserRepository()
	useCase := NewUserUseCase(repo)

	input := CreateUserInputDTO{
		Name:  "",
		Email: "invalid@example.com",
	}

	_, err := useCase.Create(input)
	if !errors.Is(err, models.ErrFieldsRequired) {
		t.Errorf("esperava erro %v, mas obteve %v", models.ErrFieldsRequired, err)
	}
}

func TestUserUseCase_Create_DuplicateEmail(t *testing.T) {
	repo := repositories.NewMemoryUserRepository()
	useCase := NewUserUseCase(repo)

	input := CreateUserInputDTO{
		Name:  "User Test",
		Email: "same@example.com",
	}

	_, _ = useCase.Create(input)

	_, err := useCase.Create(input)
	if !errors.Is(err, models.ErrEmailExists) {
		t.Errorf("esperava erro %v, mas obteve %v", models.ErrEmailExists, err)
	}
}

func TestUserUseCase_List(t *testing.T) {
	repo := repositories.NewMemoryUserRepository()
	useCase := NewUserUseCase(repo)

	input1 := CreateUserInputDTO{Name: "User One", Email: "one@example.com"}
	input2 := CreateUserInputDTO{Name: "User Two", Email: "two@example.com"}

	_, _ = useCase.Create(input1)
	_, _ = useCase.Create(input2)

	users := useCase.List()
	if len(users) != 2 {
		t.Errorf("esperava listar 2 usuarios, mas obteve %d", len(users))
	}
}
