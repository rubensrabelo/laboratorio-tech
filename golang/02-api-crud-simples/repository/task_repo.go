package repository

import (
	"database/sql"
	"fmt"

	"github.com.rrs.simple-api/models"
)


type TaskRepository interface {
	Create(task *models.Task) error
	GetAll() ([]models.Task, error)
}

type sqlTaskRepository struct {
	db *sql.DB
}


func NewTaskRepository(db *sql.DB) TaskRepository {
	return &sqlTaskRepository{db: db}
}

func (r *sqlTaskRepository) Create(task *models.Task) error {
	query := `
		INSERT INTO tasks (title, description, status) 
		VALUES ($1, $2, $3) 
		RETURNING id, created_at`

	err := r.db.QueryRow(query, task.Title, task.Description, task.Status).Scan(&task.ID, &task.CreatedAt)
	if err != nil {
		return fmt.Errorf("erro ao inserir task: %w", err)
	}
	return nil
}

func (r *sqlTaskRepository) GetAll() ([]models.Task, error) {
	query := `SELECT id, title, description, status, created_at FROM tasks`

	rows, err := r.db.Query(query)
	if err != nil {
		return nil, fmt.Errorf("erro ao buscar tasks: %w", err)
	}
	defer rows.Close()

	var tasks []models.Task = []models.Task{}
	
	for rows.Next() {
		var t models.Task
		err := rows.Scan(&t.ID, &t.Title, &t.Description, &t.Status, &t.CreatedAt)
		if err != nil {
			return nil, fmt.Errorf("erro ao escanear linha de task: %w", err)
		}
		tasks = append(tasks, t)
	}

	return tasks, nil
}
