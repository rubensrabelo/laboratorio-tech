package handlers

import (
	"encoding/json"
	"log/slog"
	"net/http"
	"rest-api-2/internal/usecases"
)

type UserHandler struct {
	useCase usecases.UserUseCaseInterface
}

func NewUserHandler(uc usecases.UserUseCaseInterface) *UserHandler {
	return &UserHandler{
		useCase: uc,
	}
}

func (h *UserHandler) Create(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "Metodo nao permitido", http.StatusMethodNotAllowed)
		return
	}

	var input usecases.CreateUserInputDTO
	err := json.NewDecoder(r.Body).Decode(&input)
	if err != nil {
		slog.Error("Falha ao decodificar JSON de entrada", "error", err.Error())
		http.Error(w, "JSON invalido", http.StatusBadRequest)
		return
	}

	user, err := h.useCase.Create(input)
	if err != nil {
		slog.Error("Falha ao criar usuário no usecase", "error", err.Error())
		http.Error(w, err.Error(), http.StatusUnprocessableEntity)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(user)
}

func (h *UserHandler) List(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodGet {
		http.Error(w, "Metodo nao permitido", http.StatusMethodNotAllowed)
		return
	}

	users := h.useCase.List()

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusOK)
	json.NewEncoder(w).Encode(users)
}
