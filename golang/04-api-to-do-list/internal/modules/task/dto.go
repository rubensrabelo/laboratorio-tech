package task

import "errors"

type CreateTaskRequest struct {
	Title string `json:"title"`
}

type UpdateTaskRequest struct {
	Title     string `json:"title"`
	Completed bool   `json:"completed"`
}

type TaskResponse struct {
	ID        int    `json:"id"`
	Title     string `json:"title"`
	Completed bool   `json:"completed"`
}

func (r *CreateTaskRequest) Validate() error {
	if r.Title == "" {
		return errors.New("o titulo e obrigatorio")
	}
	if len(r.Title) < 3 {
		return errors.New("o titulo deve ter no minimo 3 caracteres")
	}
	return nil
}

func (r *UpdateTaskRequest) Validate() error {
	if r.Title == "" {
		return errors.New("o titulo e obrigatorio")
	}
	if len(r.Title) < 3 {
		return errors.New("o titulo deve ter no minimo 3 caracteres")
	}
	return nil
}

func ToTaskResponse(t Task) TaskResponse {
	return TaskResponse{
		ID:        t.ID,
		Title:     t.Title,
		Completed: t.Completed,
	}
}

func ToTaskResponseList(taskList []Task) []TaskResponse {
	responses := make([]TaskResponse, len(taskList))
	for i, t := range taskList {
		responses[i] = ToTaskResponse(t)
	}
	return responses
}
