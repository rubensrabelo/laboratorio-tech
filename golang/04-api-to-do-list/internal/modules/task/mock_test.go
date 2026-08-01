package task

type MockTaskRepository struct {
	OnList   func() []Task
	OnCreate func(title string) Task
	OnGet    func(id int) (Task, error)
	OnUpdate func(id int, title string, completed bool) (Task, error)
	OnDelete func(id int) error
}

func (m *MockTaskRepository) List() []Task { return m.OnList() }
func (m *MockTaskRepository) Create(title string) Task { return m.OnCreate(title) }
func (m *MockTaskRepository) Get(id int) (Task, error) { return m.OnGet(id) }
func (m *MockTaskRepository) Update(id int, title string, completed bool) (Task, error) {
	return m.OnUpdate(id, title, completed)
}
func (m *MockTaskRepository) Delete(id int) error { return m.OnDelete(id) }
