package main

import (
	"log/slog"
	"net/http"
	"os"
	"rest-api-2/internal/handlers"
	"rest-api-2/internal/repositories"
	"rest-api-2/internal/usecases"
	"rest-api-2/internal/web/middleware"
	"rest-api-2/internal/web/router"
)

func main() {
	logger := slog.New(slog.NewJSONHandler(os.Stdout, nil))
	slog.SetDefault(logger)

	repo := repositories.NewMemoryUserRepository()
	useCase := usecases.NewUserUseCase(repo)
	handler := handlers.NewUserHandler(useCase)

	mux := http.NewServeMux()

	router.RegisterUserRoutes(mux, handler)

	mainHandler := middleware.Logger(mux)

	slog.Info("Servidor inicializado", "porta", "8080")

	if err := http.ListenAndServe(":8080", mainHandler); err != nil {
		slog.Error("Falha crítica no servidor", "erro", err.Error())
		os.Exit(1)
	}
}
