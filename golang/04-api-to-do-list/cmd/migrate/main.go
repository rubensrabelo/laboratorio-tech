package main

import (
	"errors"
	"fmt"
	"log/slog"
	"os"

	"github/lab/golang/api/todo/config"

	"github.com/golang-migrate/migrate/v4"
	_ "github.com/golang-migrate/migrate/v4/database/postgres"
	_ "github.com/golang-migrate/migrate/v4/source/file"
)

func main() {
	logger := slog.New(slog.NewJSONHandler(os.Stdout, &slog.HandlerOptions{Level: slog.LevelInfo}))
	slog.SetDefault(logger)

	if len(os.Args) < 2 {
		slog.Error("comando nao informado. use 'up' ou 'down'")
		os.Exit(1)
	}
	command := os.Args[1]

	cfg, err := config.LoadEnv()
	if err != nil {
		slog.Error("falha ao carregar configuracoes", "error", err)
		os.Exit(1)
	}

	dsn := fmt.Sprintf(
		"postgres://%s:%s@%s:%s/%s?sslmode=disable",
		cfg.DBUser, cfg.DBPassword, cfg.DBHost, cfg.DBPort, cfg.DBName,
	)

	m, err := migrate.New("file://migrations", dsn)
	if err != nil {
		slog.Error("falha ao inicializar o migrator", "error", err)
		os.Exit(1)
	}
	defer m.Close()

	switch command {
	case "up":
		slog.Info("executando migrations up...")
		if err := m.Up(); err != nil && !errors.Is(err, migrate.ErrNoChange) {
			slog.Error("erro ao aplicar migracao up", "error", err)
			os.Exit(1)
		}
		slog.Info("migrations up concluidas com sucesso")
	case "down":
		slog.Info("executando migrations down...")
		if err := m.Down(); err != nil && !errors.Is(err, migrate.ErrNoChange) {
			slog.Error("erro ao reverter migracao down", "error", err)
			os.Exit(1)
		}
		slog.Info("migrations down concluidas com sucesso")
	default:
		slog.Error("comando invalido. use 'up' ou 'down'", slog.String("comando_recebido", command))
		os.Exit(1)
	}
}
