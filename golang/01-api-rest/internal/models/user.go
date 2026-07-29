package models

import (
	"errors"
	"github.com/google/uuid"
)

var (
	ErrFieldsRequired = errors.New("nome e email sao obrigatorios")
	ErrEmailExists    = errors.New("email ja cadastrado")
)

type User struct {
	ID    uuid.UUID `json:"id"`
	Name  string    `json:"name"`
	Email string    `json:"email"`
}
