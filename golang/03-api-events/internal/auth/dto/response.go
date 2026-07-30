package dto

type LoginResponse struct {
	Token  string `json:"token"`
	UserId int    `json:"userId"`
}
