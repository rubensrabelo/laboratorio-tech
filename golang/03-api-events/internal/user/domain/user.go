package domain

import "time"

type User struct {
	Id        int    `gorm:"primaryKey;autoIncrement"`
	Email     string `gorm:"type:varchar(255);unique;not null;index"`
	Name      string `gorm:"type:varchar(255);not null"`
	Password  string `gorm:"type:varchar(255);not null"`
	CreatedAt time.Time
	UpdatedAt time.Time
}
