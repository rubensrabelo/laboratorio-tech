package handler

import (
	"encoding/json"
	"net/http"
	"events-api/internal/auth/dto"
	"events-api/internal/auth/service"
)

type AuthHandler struct {
	srv service.AuthService
}

func NewAuthHandler(srv service.AuthService) *AuthHandler {
	return &AuthHandler{srv: srv}
}

func RegisterRoutes(mux *http.ServeMux, h *AuthHandler) {
	mux.HandleFunc("POST /auth/login", h.Login)
}

func (h *AuthHandler) Login(w http.ResponseWriter, r *http.Request) {
	var req dto.LoginRequest
	if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
		http.Error(w, "JSON inválido", http.StatusBadRequest)
		return
	}

	if validationErrs := req.Validate(); len(validationErrs) > 0 {
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusBadRequest)
		json.NewEncoder(w).Encode(validationErrs)
		return
	}

	res, err := h.srv.Login(req)
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(res)
}
