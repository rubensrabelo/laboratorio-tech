package router

import (
	"net/http"
	"rest-api-2/internal/handlers"
)

func RegisterUserRoutes(mux *http.ServeMux, handler *handlers.UserHandler) {
	mux.HandleFunc("POST /users", handler.Create)
	mux.HandleFunc("GET /users", handler.List)
}
