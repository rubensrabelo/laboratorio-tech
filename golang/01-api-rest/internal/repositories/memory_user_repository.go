package repositories

import (
	"errors"
	"rest-api-2/internal/models"
	"sync"
)

type MemoryUserRepository struct {
	mu    sync.Mutex
	users []models.User
}

func NewMemoryUserRepository() *MemoryUserRepository {
	return &MemoryUserRepository{
		users: []models.User{},
	}
}

func (r *MemoryUserRepository) Add(user models.User) error {
	r.mu.Lock()
	defer r.mu.Unlock()

	if user.Name == "" || user.Email == "" {
		return errors.New("nome e email sao obrigatorios")
	}

	r.users = append(r.users, user)
	return nil
}

func (r *MemoryUserRepository) GetAll() []models.User {
	r.mu.Lock()
	defer r.mu.Unlock()

	return r.users
}
