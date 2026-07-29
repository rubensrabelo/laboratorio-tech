package repositories

import (
	"rest-api-2/internal/models"
	"sync"
)

type MemoryUserRepository struct {
	mu     sync.Mutex
	users  []models.User
	emails map[string]bool
}

func NewMemoryUserRepository() *MemoryUserRepository {
	return &MemoryUserRepository{
		users:  []models.User{},
		emails: make(map[string]bool),
	}
}

func (r *MemoryUserRepository) emailExists(email string) bool {
	return r.emails[email]
}

func (r *MemoryUserRepository) Add(user models.User) error {
	r.mu.Lock()
	defer r.mu.Unlock()

	if user.Name == "" || user.Email == "" {
		return models.ErrFieldsRequired
	}

	if r.emailExists(user.Email) {
		return models.ErrEmailExists
	}

	r.emails[user.Email] = true
	r.users = append(r.users, user)
	return nil
}

func (r *MemoryUserRepository) GetAll() []models.User {
	r.mu.Lock()
	defer r.mu.Unlock()

	return r.users
}
