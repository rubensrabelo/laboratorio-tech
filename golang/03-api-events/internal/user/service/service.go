package service

import (
	"errors"
	"events-api/internal/user/domain"
	"events-api/internal/user/dto"
	"events-api/internal/user/repository"
	"golang.org/x/crypto/bcrypt"
)

type service struct {
	repo repository.UserRepository
}

func NewUserService(repo repository.UserRepository) UserService {
	return &service{repo: repo}
}

func (s *service) Register(req dto.RegisterRequest) (*domain.User, error) {
	existing, _ := s.repo.FindByEmail(req.Email)
	if existing != nil {
		return nil, errors.New("este e-mail já está cadastrado")
	}

	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(req.Password), bcrypt.DefaultCost)
	if err != nil {
		return nil, err
	}

	user := &domain.User{
		Name:     req.Name,
		Email:    req.Email,
		Password: string(hashedPassword),
	}

	if err := s.repo.Create(user); err != nil {
		return nil, err
	}

	return user, nil
}

func (s *service) GetByEmail(email string) (*domain.User, error) {
	return s.repo.FindByEmail(email)
}

func (s *service) GetById(id int) (*domain.User, error) {
	return s.repo.FindById(id)
}

func (s *service) Update(id int, req dto.UpdateUserRequest) (*domain.User, error) {
	user, err := s.repo.FindById(id)
	if err != nil {
		return nil, errors.New("usuário não encontrado")
	}

	existing, _ := s.repo.FindByEmail(req.Email)
	if existing != nil && existing.Id != id {
		return nil, errors.New("este e-mail já está em uso por outro usuário")
	}

	user.Name = req.Name
	user.Email = req.Email

	if err := s.repo.Update(user); err != nil {
		return nil, err
	}

	return user, nil
}

func (s *service) Delete(id int) error {
	_, err := s.repo.FindById(id)
	if err != nil {
		return errors.New("usuário não encontrado")
	}
	return s.repo.Delete(id)
}

func (s *service) ListAll() ([]domain.User, error) {
	return s.repo.FindAll()
}
