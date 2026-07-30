package main

import (
	"log/slog"
	"os"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"

	"events-api/config"
	"events-api/internal/user/domain"
)

func main() {
	logger := slog.New(slog.NewJSONHandler(os.Stdout, nil))
	slog.SetDefault(logger)

	cfg := config.LoadEnv()

	db, err := gorm.Open(postgres.Open(cfg.GetDSN()), &gorm.Config{})
	if err != nil {
		slog.Error("Erro ao conectar no banco para migração", "erro", err.Error())
		os.Exit(1)
	}

	slog.Info("Conectado. Executando AutoMigrate baseado nas subpastas domain/...")

	err = db.AutoMigrate(&domain.User{})
	if err != nil {
		slog.Error("Falha ao aplicar migrações do GORM", "erro", err.Error())
		os.Exit(1)
	}

	slog.Info("Estrutura de tabelas sincronizada!")
}
