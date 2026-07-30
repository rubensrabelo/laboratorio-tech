package router

import (
	"events-api/internal/auth/handler"
	"net/http"
)

func RegisterAuthRoutes(mux *http.ServeMux, h *handler.AuthHandler) {
	mux.HandleFunc("POST /api/v1/auth/login", h.Login)
}
