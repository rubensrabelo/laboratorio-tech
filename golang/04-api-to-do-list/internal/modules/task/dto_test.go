package task

import "testing"

func TestCreateTaskRequest_Validate(t *testing.T) {
	tests := []struct {
		name        string
		request     CreateTaskRequest
		expectError bool
		errorMsg    string
	}{
		{
			name:        "Sucesso com título válido",
			request:     CreateTaskRequest{Title: "Aprender Go"},
			expectError: false,
		},
		{
			name:        "Erro com título vazio",
			request:     CreateTaskRequest{Title: ""},
			expectError: true,
			errorMsg:    "o titulo e obrigatorio",
		},
		{
			name:        "Erro com título menor que 3 caracteres",
			request:     CreateTaskRequest{Title: "Oi"},
			expectError: true,
			errorMsg:    "o titulo deve ter no minimo 3 caracteres",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			err := tt.request.Validate()
			if (err != nil) != tt.expectError {
				t.Fatalf("esperava erro: %v, obtido: %v", tt.expectError, err)
			}
			if err != nil && err.Error() != tt.errorMsg {
				t.Errorf("esperava mensagem '%s', obtida '%s'", tt.errorMsg, err.Error())
			}
		})
	}
}
