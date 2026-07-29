package handlers

import (
	"bytes"
	"encoding/json"
	"github.com/google/uuid"
	"net/http"
	"net/http/httptest"
	"rest-api-2/internal/models"
	"rest-api-2/internal/usecases"
	"testing"
)

type mockUserUseCase struct {
	onCreate func(input usecases.CreateUserInputDTO) (models.User, error)
	onList   func() []models.User
}

func (m *mockUserUseCase) Create(input usecases.CreateUserInputDTO) (models.User, error) {
	return m.onCreate(input)
}

func (m *mockUserUseCase) List() []models.User {
	return m.onList()
}

func TestUserHandler_Create_Success(t *testing.T) {
	expectedUser := models.User{
		ID:    uuid.New(),
		Name:  "Test User",
		Email: "test@example.com",
	}

	mockUC := &mockUserUseCase{
		onCreate: func(input usecases.CreateUserInputDTO) (models.User, error) {
			return expectedUser, nil
		},
	}

	handler := NewUserHandler(mockUC)

	body, _ := json.Marshal(usecases.CreateUserInputDTO{
		Name:  "Test User",
		Email: "test@example.com",
	})

	req := httptest.NewRequest(http.MethodPost, "/users", bytes.NewBuffer(body))
	res := httptest.NewRecorder()

	handler.Create(res, req)

	if res.Code != http.StatusCreated {
		t.Errorf("esperava status %d, obtido %d", http.StatusCreated, res.Code)
	}

	if res.Header().Get("Content-Type") != "application/json" {
		t.Errorf("esperava Content-Type application/json, obtido %s", res.Header().Get("Content-Type"))
	}
}

func TestUserHandler_Create_InvalidJSON(t *testing.T) {
	mockUC := &mockUserUseCase{}
	handler := NewUserHandler(mockUC)

	req := httptest.NewRequest(http.MethodPost, "/users", bytes.NewBufferString("{invalid-json}"))
	res := httptest.NewRecorder()

	handler.Create(res, req)

	if res.Code != http.StatusBadRequest {
		t.Errorf("esperava status %d, obtido %d", http.StatusBadRequest, res.Code)
	}
}

func TestUserHandler_Create_UseCaseError(t *testing.T) {
	mockUC := &mockUserUseCase{
		onCreate: func(input usecases.CreateUserInputDTO) (models.User, error) {
			return models.User{}, models.ErrEmailExists
		},
	}

	handler := NewUserHandler(mockUC)

	body, _ := json.Marshal(usecases.CreateUserInputDTO{
		Name:  "Test User",
		Email: "test@example.com",
	})

	req := httptest.NewRequest(http.MethodPost, "/users", bytes.NewBuffer(body))
	res := httptest.NewRecorder()

	handler.Create(res, req)

	if res.Code != http.StatusUnprocessableEntity {
		t.Errorf("esperava status %d, obtido %d", http.StatusUnprocessableEntity, res.Code)
	}
}

func TestUserHandler_List(t *testing.T) {
	expectedUsers := []models.User{
		{ID: uuid.New(), Name: "User 1", Email: "u1@example.com"},
		{ID: uuid.New(), Name: "User 2", Email: "u2@example.com"},
	}

	mockUC := &mockUserUseCase{
		onList: func() []models.User {
			return expectedUsers
		},
	}

	handler := NewUserHandler(mockUC)

	req := httptest.NewRequest(http.MethodGet, "/users", nil)
	res := httptest.NewRecorder()

	handler.List(res, req)

	if res.Code != http.StatusOK {
		t.Errorf("esperava status %d, obtido %d", http.StatusOK, res.Code)
	}

	var returnedUsers []models.User
	json.NewDecoder(res.Body).Decode(&returnedUsers)

	if len(returnedUsers) != len(expectedUsers) {
		t.Errorf("esperava %d usuarios, obtido %d", len(expectedUsers), len(returnedUsers))
	}
}
