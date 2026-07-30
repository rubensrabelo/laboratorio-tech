package router

import (
	"events-api/internal/user/handler"
	"net/http"
)

func RegisterUserPublicRoutes(mux *http.ServeMux, h *handler.UserHandler) {
	mux.HandleFunc("POST /api/v1/users", h.CreateUser)
}

func RegisterUserProtectedRoutes(mux *http.ServeMux, h *handler.UserHandler) {
	mux.HandleFunc("GET /api/v1/users", h.ListUsers)
	mux.HandleFunc("GET /api/v1/users/{id}", h.GetUser)
	mux.HandleFunc("PUT /api/v1/users/{id}", h.UpdateUser)
	mux.HandleFunc("DELETE /api/v1/users/{id}", h.DeleteUser)
}
