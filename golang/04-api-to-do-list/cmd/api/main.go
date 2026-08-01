package main

import (
	"context"
	"errors"
	"log/slog"

	"github/lab/golang/api/todo/config"
	"github/lab/golang/api/todo/internal/modules/task"
	"github/lab/golang/api/todo/internal/infra/database"
	"github/lab/golang/api/todo/shared/middleware"

	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"
)

func main() {
	logger := slog.New(slog.NewJSONHandler(os.Stdout, &slog.HandlerOptions{
		Level: slog.LevelInfo,
	}))
	slog.SetDefault(logger)

	cfg, err := config.LoadEnv()
	if err != nil {
		slog.Error("falha ao carregar as configuracoes", "error", err)
		os.Exit(1)
	}

	db, err := database.NewPostgresConnection(cfg)
	if err != nil {
		slog.Error("falha ao conectar no banco de dados", "error", err)
		os.Exit(1)
	}

	apiV1Mux := http.NewServeMux()

	tasksModule := task.NewModule(db)
	tasksModule.RegisterRoutes(apiV1Mux)

	var apiHandler http.Handler = apiV1Mux
	apiHandler = middleware.Logger(apiHandler)

	mainMux := http.NewServeMux()
	mainMux.Handle("/api/v1/", http.StripPrefix("/api/v1", apiHandler))

	srv := &http.Server{
		Addr:         cfg.ServerPort,
		Handler:      mainMux,
		ReadTimeout:  10 * time.Second,
		WriteTimeout: 15 * time.Second,
		IdleTimeout:  120 * time.Second,
	}

	ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt, syscall.SIGTERM)
	defer stop()

	go func() {
		slog.Info("servidor rodando", slog.String("url", "http://localhost"+cfg.ServerPort+"/api/v1"))
		if err := srv.ListenAndServe(); err != nil && !errors.Is(err, http.ErrServerClosed) {
			slog.Error("erro fatal ao iniciar servidor", "error", err)
			os.Exit(1)
		}
	}()

	<-ctx.Done()
	slog.Info("sinal de interrupcao recebido, desligando servidor de forma segura...")

	shutdownCtx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	if err := srv.Shutdown(shutdownCtx); err != nil {
		slog.Error("erro ao desligar servidor forcando encerramento", "error", err)
		os.Exit(1)
	}

	slog.Info("servidor finalizado com sucesso")
}