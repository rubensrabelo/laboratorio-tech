package main

import (
	"encoding/json"
	"log"
	"net/http"

	"github.com/gorilla/mux"

	"github.com.rrs.simple-api/config"
	"github.com.rrs.simple-api/controllers"
	"github.com.rrs.simple-api/models"
	"github.com.rrs.simple-api/repository"
	"github.com.rrs.simple-api/services"
)

func main() {
	db, err := config.SetupDB()
	if err != nil {
		log.Fatalf("Erro crítico ao iniciar a aplicação: %v", err)
	}

	err = models.CreateTaskTable(db)
	if err != nil {
		log.Fatalf("Erro ao criar tabelas: %v", err)
	}

	defer func() {
		if err := db.Close(); err != nil {
			log.Printf("Erro ao fechar a conexão com o banco: %v", err)
		} else {
			log.Println("Conexão com o banco de dados fechada com segurança.")
		}
	}()

	taskRepo := repository.NewTaskRepository(db)
	taskService := services.NewTaskService(taskRepo)
	taskController := controllers.NewTaskController(taskService)

	r := mux.NewRouter()

	r.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusOK)
		json.NewEncoder(w).SetEscapeHTML(true)
		_ = json.NewEncoder(w).Encode(map[string]string{"status": "UP", "database": "CONNECTED"})
	}).Methods("GET")

	r.HandleFunc("/tasks", taskController.CreateTaskHandler).Methods("POST")
	r.HandleFunc("/tasks", taskController.GetAllTasksHandler).Methods("GET")

	log.Println("Servidor API rodando na porta :8080...")
	if err := http.ListenAndServe(":8080", r); err != nil {
		log.Fatalf("Erro ao iniciar o servidor: %v", err)
	}
}
