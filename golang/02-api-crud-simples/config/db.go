package config

import (
	"database/sql"
	"fmt"
	"log"
	"os"

	"github.com/joho/godotenv"
	_ "github.com/lib/pq"
)

func SetupDB() (*sql.DB, error) {
	err := godotenv.Load()
	if err != nil {
		log.Println("Aviso: Nenhum arquivo .env encontrado, usando variáveis de ambiente do sistema.")
	}

	dbUser := os.Getenv("DB_USER")
	dbPassword := os.Getenv("DB_PASSWORD")
	dbName := os.Getenv("DB_NAME")
	dbPort := os.Getenv("DB_PORT")
	dbHost := os.Getenv("DB_HOST")

	connStr := fmt.Sprintf("host=%s port=%s user=%s password=%s dbname=%s sslmode=disable",
		dbHost, dbPort, dbUser, dbPassword, dbName)

	db, err := sql.Open("postgres", connStr)
	if err != nil {
		return nil, fmt.Errorf("erro ao abrir banco de dados: %w", err)
	}

	err = db.Ping()
	if err != nil {
		return nil, fmt.Errorf("falha ao pingar o banco de dados: %w", err)
	}

	log.Println("Conexão com o banco de dados via .env realizada com sucesso!")
	return db, nil
}
