package dto

import "strings"

type RegisterRequest struct {
	Email    string `json:"email" binding:"required,email"`
	Password string `json:"password" binding:"required,min=8"`
	Name     string `json:"name" binding:"required,min=2"`
}

type UpdateUserRequest struct {
	Name  string `json:"name" binding:"required,min=2"`
	Email string `json:"email" binding:"required,email"`
}

func (r *RegisterRequest) Validate() map[string]string {
	errs := make(map[string]string)
	if !strings.Contains(r.Email, "@") {
		errs["email"] = "e-mail inválido"
	}
	if len(r.Password) < 8 {
		errs["password"] = "a senha deve ter no mínimo 8 caracteres"
	}
	if len(r.Name) < 2 {
		errs["name"] = "o nome deve ter no mínimo 2 caracteres"
	}
	return errs
}

func (r *UpdateUserRequest) Validate() map[string]string {
	errs := make(map[string]string)
	if !strings.Contains(r.Email, "@") {
		errs["email"] = "e-mail inválido"
	}
	if len(r.Name) < 2 {
		errs["name"] = "o nome deve ter no mínimo 2 caracteres"
	}
	return errs
}
