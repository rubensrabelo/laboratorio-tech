package services

import (
	"errors"

	"github.com.rrs.simple-api/models"
	"github.com.rrs.simple-api/repository"
)

type TaskService struct {
	repo repository.TaskRepository
}

func NewTaskService(repo repository.TaskRepository) *TaskService {
	return &TaskService{repo: repo}
}

func (s *TaskService) CreateTask(task *models.Task) error {
	if task.Title == "" {
		return errors.New("o título da tarefa não pode ser vazio")
	}
	if task.Status == "" {
		task.Status = "pending"
	}
	return s.repo.Create(task)
}

func (s *TaskService) GetAllTasks() ([]models.Task, error) {
	return s.repo.GetAll()
}
