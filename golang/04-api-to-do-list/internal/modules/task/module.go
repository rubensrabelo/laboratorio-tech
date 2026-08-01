package task

import (
	"net/http"

	"gorm.io/gorm"
)

type Module struct {
	handler *TaskHandler
}

func NewModule(db *gorm.DB) *Module {
	repo := NewTaskRepository(db)
	handler := NewTaskHandler(repo)
	return &Module{handler: handler}
}

func (m *Module) RegisterRoutes(mux *http.ServeMux) {
	mux.HandleFunc("GET /tasks", m.handler.list)
	mux.HandleFunc("POST /tasks", m.handler.create)
	mux.HandleFunc("GET /tasks/{id}", m.handler.get)
	mux.HandleFunc("PUT /tasks/{id}", m.handler.update)
	mux.HandleFunc("DELETE /tasks/{id}", m.handler.delete)
}
