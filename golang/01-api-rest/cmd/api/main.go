package main

import (
	"log"
	"net/http"
	"rest-api-2/internal/handlers"
	"rest-api-2/internal/repositories"
	"rest-api-2/internal/usecases"
)

func main() {
	repo := repositories.NewMemoryUserRepository()
	useCase := usecases.NewUserUseCase(repo)
	handler := handlers.NewUserHandler(useCase)

	http.HandleFunc("/users", func(w http.ResponseWriter, r *http.Request) {
		switch r.Method {
		case http.MethodPost:
			handler.Create(w, r)
		case http.MethodGet:
			handler.List(w, r)
		default:
			http.Error(w, "Metodo nao permitido", http.StatusMethodNotAllowed)
		}
	})

	log.Println("Servidor rodando na porta :8080...")
	if err := http.ListenAndServe(":8080", nil); err != nil {
		log.Fatal(err)
	}
}
