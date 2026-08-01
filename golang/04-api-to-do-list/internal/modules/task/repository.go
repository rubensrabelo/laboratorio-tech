package task

import (
	"gorm.io/gorm"
)

type TaskRepository struct {
	db *gorm.DB
}

func NewTaskRepository(db *gorm.DB) *TaskRepository {
	return &TaskRepository{db: db}
}

func (r *TaskRepository) List() []Task {
	var list []Task
	r.db.Find(&list)
	return list
}

func (r *TaskRepository) Create(title string) Task {
	task := Task{
		Title:     title,
		Completed: false,
	}
	r.db.Create(&task)
	return task
}

func (r *TaskRepository) Get(id int) (Task, error) {
	var task Task
	if err := r.db.First(&task, id).Error; err != nil {
		return Task{}, err
	}
	return task, nil
}

func (r *TaskRepository) Update(id int, title string, completed bool) (Task, error) {
	task, err := r.Get(id)
	if err != nil {
		return Task{}, err
	}

	task.Title = title
	task.Completed = completed

	if err := r.db.Save(&task).Error; err != nil {
		return Task{}, err
	}
	return task, nil
}

func (r *TaskRepository) Delete(id int) error {
	task, err := r.Get(id)
	if err != nil {
		return err
	}

	if err := r.db.Delete(&task).Error; err != nil {
		return err
	}
	return nil
}
