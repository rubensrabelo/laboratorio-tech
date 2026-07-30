package dto

import "events-api/internal/user/domain"

type UserResponse struct {
	Id    int    `json:"id"`
	Name  string `json:"name"`
	Email string `json:"email"`
}

func ToUserResponse(u *domain.User) UserResponse {
	return UserResponse{Id: u.Id, Name: u.Name, Email: u.Email}
}
