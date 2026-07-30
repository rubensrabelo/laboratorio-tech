package main

import (
	"log/slog"
	"net/http"
	"os"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"

	"events-api/config"
	authHandler "events-api/internal/auth/handler"
	authRouter "events-api/internal/auth/router"
	authSrv "events-api/internal/auth/service"
	"events-api/internal/shared/middleware"
	userHandler "events-api/internal/user/handler"
	userRepository "events-api/internal/user/repository"
	userRouter "events-api/internal/user/router"
	userSrv "events-api/internal/user/service"
)

func main() {
	logger := slog.New(slog.NewJSONHandler(os.Stdout, nil))
	slog.SetDefault(logger)

	cfg := config.LoadEnv()

	db, err := gorm.Open(postgres.Open(cfg.GetDSN()), &gorm.Config{})
	if err != nil {
		slog.Error("Falha crítica ao conectar no banco de dados", "erro", err.Error())
		os.Exit(1)
	}

	repo := userRepository.NewUserRepository(db)
	userService := userSrv.NewUserService(repo)
	uHandler := userHandler.NewUserHandler(userService)

	authService := authSrv.NewAuthService(userService, cfg.JWTSecret)
	aHandler := authHandler.NewAuthHandler(authService)

	publicMux := http.NewServeMux()

	userRouter.RegisterUserPublicRoutes(publicMux, uHandler)
	authRouter.RegisterAuthRoutes(publicMux, aHandler)

	protectedMux := http.NewServeMux()
	userRouter.RegisterUserProtectedRoutes(protectedMux, uHandler)

	publicMux.Handle("/", middleware.AuthRequired(cfg.JWTSecret)(protectedMux))

	slog.Info("Servidor REST ativo", "porta", cfg.Port)
	if err := http.ListenAndServe(":"+cfg.Port, publicMux); err != nil {
		slog.Error("Falha crítica no servidor HTTP", "erro", err.Error())
		os.Exit(1)
	}
}
