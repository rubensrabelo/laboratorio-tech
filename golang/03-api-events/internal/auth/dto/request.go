package dto

import "strings"

type LoginRequest struct {
	Email    string `json:"email" binding:"required,email"`
	Password string `json:"password" binding:"required,min=8"`
}

func (l *LoginRequest) Validate() map[string]string {
	errs := make(map[string]string)
	if !strings.Contains(l.Email, "@") {
		errs["email"] = "e-mail inválido"
	}
	if len(l.Password) < 8 {
		errs["password"] = "a senha deve ter no mínimo 8 caracteres"
	}
	return errs
}
