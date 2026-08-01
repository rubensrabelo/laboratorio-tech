package config

import (
	"fmt"
	"os"

	"github.com/joho/godotenv"
)

type EnvConfig struct {
	DBHost     string
	DBPort     string
	DBUser     string
	DBPassword string
	DBName     string
	ServerPort string
}

func LoadEnv() (*EnvConfig, error) {
	_ = godotenv.Load()

	cfg := &EnvConfig{
		DBHost:     os.Getenv("DB_HOST"),
		DBPort:     os.Getenv("DB_PORT"),
		DBUser:     os.Getenv("DB_USER"),
		DBPassword: os.Getenv("DB_PASSWORD"),
		DBName:     os.Getenv("DB_NAME"),
		ServerPort: os.Getenv("SERVER_PORT"),
	}

	if cfg.DBHost == "" || cfg.DBPort == "" || cfg.DBUser == "" || cfg.DBPassword == "" || cfg.DBName == "" {
		return nil, fmt.Errorf("configuracoes de banco de dados incompletas no ambiente")
	}

	if cfg.ServerPort == "" {
		cfg.ServerPort = ":8080"
	}

	return cfg, nil
}
