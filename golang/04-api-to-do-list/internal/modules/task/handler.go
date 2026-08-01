package task

import (
	"encoding/json"
	"log/slog"
	"github/lab/golang/api/todo/shared/response"
	"net/http"
	"strconv"
)

type Repository interface {
	List() []Task
	Create(title string) Task
	Get(id int) (Task, error)
	Update(id int, title string, completed bool) (Task, error)
	Delete(id int) error
}

type TaskHandler struct {
	repo Repository
}

func NewTaskHandler(repo Repository) *TaskHandler {
	return &TaskHandler{repo: repo}
}

func (h *TaskHandler) list(w http.ResponseWriter, r *http.Request) {
	taskList := h.repo.List()
	response.WriteJSON(w, http.StatusOK, ToTaskResponseList(taskList))
}

func (h *TaskHandler) create(w http.ResponseWriter, r *http.Request) {
	r.Body = http.MaxBytesReader(w, r.Body, 1<<20)

	var req CreateTaskRequest
	dec := json.NewDecoder(r.Body)
	dec.DisallowUnknownFields()
	if err := dec.Decode(&req); err != nil {
		slog.Warn("json invalido ou corpo muito grande", "error", err)
		response.WriteError(w, http.StatusBadRequest, "JSON invalido: "+err.Error())
		return
	}

	if err := req.Validate(); err != nil {
		slog.Warn("falha na validacao de criacao", "error", err)
		response.WriteError(w, http.StatusBadRequest, err.Error())
		return
	}

	task := h.repo.Create(req.Title)

	slog.Info("tarefa criada com sucesso", slog.Int("task_id", task.ID))
	response.WriteJSON(w, http.StatusCreated, ToTaskResponse(task))
}

func (h *TaskHandler) get(w http.ResponseWriter, r *http.Request) {
	idStr := r.PathValue("id")
	id, err := strconv.Atoi(idStr)
	if err != nil {
		slog.Warn("id de tarefa invalido recebido", slog.String("id_str", idStr))
		response.WriteError(w, http.StatusBadRequest, "invalid id")
		return
	}

	task, err := h.repo.Get(id)
	if err != nil {
		slog.Error("tarefa nao encontrada", slog.Int("task_id", id), "error", err)
		response.WriteError(w, http.StatusNotFound, err.Error())
		return
	}

	response.WriteJSON(w, http.StatusOK, ToTaskResponse(task))
}

func (h *TaskHandler) update(w http.ResponseWriter, r *http.Request) {
	idStr := r.PathValue("id")
	id, err := strconv.Atoi(idStr)
	if err != nil {
		slog.Warn("id invalido para atualizacao", slog.String("id_str", idStr))
		response.WriteError(w, http.StatusBadRequest, "invalid id")
		return
	}

	r.Body = http.MaxBytesReader(w, r.Body, 1<<20)

	var req UpdateTaskRequest
	dec := json.NewDecoder(r.Body)
	dec.DisallowUnknownFields()
	if err := dec.Decode(&req); err != nil {
		slog.Warn("json invalido para atualizacao", "error", err)
		response.WriteError(w, http.StatusBadRequest, "JSON invalido: "+err.Error())
		return
	}

	if err := req.Validate(); err != nil {
		slog.Warn("falha na validacao de atualizacao", "error", err)
		response.WriteError(w, http.StatusBadRequest, err.Error())
		return
	}

	task, err := h.repo.Update(id, req.Title, req.Completed)
	if err != nil {
		slog.Error("falha ao atualizar tarefa", slog.Int("task_id", id), "error", err)
		response.WriteError(w, http.StatusNotFound, err.Error())
		return
	}

	slog.Info("tarefa atualizada", slog.Int("task_id", id))
	response.WriteJSON(w, http.StatusOK, ToTaskResponse(task))
}

func (h *TaskHandler) delete(w http.ResponseWriter, r *http.Request) {
	idStr := r.PathValue("id")
	id, err := strconv.Atoi(idStr)
	if err != nil {
		slog.Warn("id invalido para delecao", slog.String("id_str", idStr))
		response.WriteError(w, http.StatusBadRequest, "invalid id")
		return
	}

	if err := h.repo.Delete(id); err != nil {
		slog.Error("falha ao deletar tarefa", slog.Int("task_id", id), "error", err)
		response.WriteError(w, http.StatusNotFound, err.Error())
		return
	}

	slog.Info("tarefa deletada", slog.Int("task_id", id))
	w.WriteHeader(http.StatusNoContent)
}
