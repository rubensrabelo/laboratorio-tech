package repositories

import (
	"errors"
	"rest-api-2/internal/models"
	"testing"

	"github.com/google/uuid"
)

func TestAdd_Success(t *testing.T) {
	repo := NewMemoryUserRepository()
	user := models.User{
		ID:    uuid.New(),
		Name:  "John Doe",
		Email: "john@example.com",
	}

	err := repo.Add(user)
	if err != nil {
		t.Fatalf("esperava sucesso, mas obteve erro: %v", err)
	}

	users := repo.GetAll()
	if len(users) != 1 {
		t.Errorf("esperava 1 usuario salvo, mas obteve %d", len(users))
	}

	if users[0].Email != user.Email {
		t.Errorf("esperava o email %s, mas obteve %s", user.Email, users[0].Email)
	}
}

func TestAdd_RequiredFields(t *testing.T) {
	repo := NewMemoryUserRepository()

	userWithoutName := models.User{ID: uuid.New(), Email: "test@example.com"}
	err := repo.Add(userWithoutName)
	if !errors.Is(err, models.ErrFieldsRequired) {
		t.Errorf("esperava erro %v, mas obteve %v", models.ErrFieldsRequired, err)
	}

	userWithoutEmail := models.User{ID: uuid.New(), Name: "Test"}
	err = repo.Add(userWithoutEmail)
	if !errors.Is(err, models.ErrFieldsRequired) {
		t.Errorf("esperava erro %v, mas obteve %v", models.ErrFieldsRequired, err)
	}
}

func TestAdd_DuplicateEmail(t *testing.T) {
	repo := NewMemoryUserRepository()
	user1 := models.User{
		ID:    uuid.New(),
		Name:  "User One",
		Email: "duplicate@example.com",
	}
	user2 := models.User{
		ID:    uuid.New(),
		Name:  "User Two",
		Email: "duplicate@example.com",
	}

	_ = repo.Add(user1)

	err := repo.Add(user2)
	if !errors.Is(err, models.ErrEmailExists) {
		t.Errorf("esperava erro %v, mas obteve %v", models.ErrEmailExists, err)
	}
}
