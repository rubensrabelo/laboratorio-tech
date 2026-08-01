package task

type Task struct {
	ID        int    `gorm:"primaryKey;autoIncrement"`
	Title     string `gorm:"type:varchar(255);not null"`
	Completed bool   `gorm:"not null;default:false"`
}
