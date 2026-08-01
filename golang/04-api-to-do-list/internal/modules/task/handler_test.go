package task

import (
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"

	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestRouter() *http.ServeMux {
	db, _ := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	db.AutoMigrate(&Task{})

	db.Create(&Task{ID: 1, Title: "Tarefa Existente", Completed: false})
	db.Create(&Task{ID: 2, Title: "Outra Tarefa", Completed: true})

	mux := http.NewServeMux()
	module := NewModule(db)
	module.RegisterRoutes(mux)

	return mux
}

func TestGetTaskHandler(t *testing.T) {
	router := setupTestRouter()

	cases := []struct {
		name       string
		url        string
		wantStatus int
	}{
		{"ID valido e existente", "/tasks/1", http.StatusOK},
		{"ID valido mas inexistente", "/tasks/999", http.StatusNotFound},
		{"ID invalido (texto)", "/tasks/abc", http.StatusBadRequest},
	}

	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			req := httptest.NewRequest(http.MethodGet, tc.url, nil)
			rec := httptest.NewRecorder()

			router.ServeHTTP(rec, req)

			if rec.Code != tc.wantStatus {
				t.Fatalf("status %d, esperado %d", rec.Code, tc.wantStatus)
			}
		})
	}
}

func TestUpdateTaskHandler(t *testing.T) {
	router := setupTestRouter()

	cases := []struct {
		name       string
		url        string
		body       string
		wantStatus int
	}{
		{
			name:       "Atualizacao valida",
			url:        "/tasks/1",
			body:       `{"title": "Titulo Atualizado", "completed": true}`,
			wantStatus: http.StatusOK,
		},
		{
			name:       "Erro de validacao (titulo curto)",
			url:        "/tasks/1",
			body:       `{"title": "Go", "completed": true}`,
			wantStatus: http.StatusBadRequest,
		},
		{
			name:       "ID inexistente",
			url:        "/tasks/999",
			body:       `{"title": "Nova tentativa", "completed": true}`,
			wantStatus: http.StatusNotFound,
		},
		{
			name:       "Campo desconhecido no JSON",
			url:        "/tasks/1",
			body:       `{"title": "Titulo OK", "unknown_field": 123}`,
			wantStatus: http.StatusBadRequest,
		},
	}

	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			req := httptest.NewRequest(http.MethodPut, tc.url, strings.NewReader(tc.body))
			rec := httptest.NewRecorder()

			router.ServeHTTP(rec, req)

			if rec.Code != tc.wantStatus {
				t.Fatalf("status %d, esperado %d", rec.Code, tc.wantStatus)
			}
		})
	}
}

func TestDeleteTaskHandler(t *testing.T) {
	router := setupTestRouter()

	cases := []struct {
		name       string
		url        string
		wantStatus int
	}{
		{"Remover com sucesso", "/tasks/2", http.StatusNoContent},
		{"Remover ID inexistente", "/tasks/999", http.StatusNotFound},
		{"Remover ID invalido", "/tasks/xyz", http.StatusBadRequest},
	}

	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			req := httptest.NewRequest(http.MethodDelete, tc.url, nil)
			rec := httptest.NewRecorder()

			router.ServeHTTP(rec, req)

			if rec.Code != tc.wantStatus {
				t.Fatalf("status %d, esperado %d", rec.Code, tc.wantStatus)
			}
		})
	}
}
