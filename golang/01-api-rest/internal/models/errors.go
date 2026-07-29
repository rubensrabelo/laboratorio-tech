package models

import "errors"

var (
	ErrFieldsRequired = errors.New("nome e email sao obrigatorios")
	ErrEmailExists    = errors.New("email ja cadastrado")
)
