package service

import (
	"errors"
	"time"
	"events-api/internal/auth/dto"
	userSrv "events-api/internal/user/service"
	"github.com/golang-jwt/jwt/v5"
	"golang.org/x/crypto/bcrypt"
)

type service struct {
	userServices userSrv.UserService
	jwtSecret    string
}

func NewAuthService(us userSrv.UserService, secret string) AuthService {
	return &service{userServices: us, jwtSecret: secret}
}

func (s *service) Login(req dto.LoginRequest) (*dto.LoginResponse, error) {
	user, err := s.userServices.GetByEmail(req.Email)
	if err != nil {
		return nil, errors.New("credenciais inválidas")
	}

	err = bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(req.Password))
	if err != nil {
		return nil, errors.New("credenciais inválidas")
	}

	claims := jwt.MapClaims{
		"sub":   user.Id,
		"email": user.Email,
		"exp":   time.Now().Add(time.Hour * 24).Unix(),
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	tokenString, err := token.SignedString([]byte(s.jwtSecret))
	if err != nil {
		return nil, err
	}

	return &dto.LoginResponse{
		Token:  tokenString,
		UserId: user.Id,
	}, nil
}
