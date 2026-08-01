# API To-Do List em Go

Este projeto consiste em uma API REST para gerenciamento de tarefas desenvolvida em Go. A arquitetura foca em boas práticas de produção, utilizando o GORM como ORM, PostgreSQL como banco de dados principal e controle de migrações estruturadas. 

A estrutura do projeto segue padrões modernos de isolamento de escopo por módulos, empacotamento via Docker (Multistage Build) utilizando imagens seguras (Distroless), orquestração de ambiente com Docker Compose e testes automatizados isolados através do padrão Table-Driven Tests com SQLite em memória.

## Estrutura de Pastas

```text
.
├── cmd
│   └── api
│       └── main.go
├── config
│   └── env.go
├── docker-compose.yml
├── Dockerfile
├── go.mod
├── go.sum
├── internal
│   └── modules
│       └── task
│           ├── dto.go
│           ├── dto_test.go
│           ├── handler.go
│           ├── handler_test.go
│           ├── mock_test.go
│           ├── module.go
│           ├── repository.go
│           └── task.go
├── migrations
│   ├── 000001_create_tasks_table.down.sql
│   └── 000001_create_tasks_table.up.sql
├── README.md
└── shared
    ├── database
    │   └── postgres.go
    ├── middleware
    │   └── logger.go
    └── response
        └── json.go
```

## Pré-requisitos

* Docker
* Docker Compose

## Configuração e Execução

1. Crie um arquivo `.env` na raiz do projeto utilizando o modelo abaixo:
   ```env
   SERVER_PORT=:8080
   DB_HOST=localhost
   DB_PORT=5432
   DB_USER=postgres
   DB_PASSWORD=suasenha
   DB_NAME=meubanco
   ```

2. Execute o comando para compilar a aplicação, executar as migrações automáticas e subir os containers:
   ```bash
   docker compose up --build
   ```

3. A API estará pronta para receber requisições em: `http://localhost:8080`

## Testes Automatizados

A suíte de testes unitários e de integração HTTP utiliza o ecossistema nativo do Go integrado a um banco de dados SQLite em memória (`:memory:`), garantindo velocidade e isolamento sem afetar o banco de dados de produção.

Para rodar os testes em modo descritivo, execute:
```bash
go test ./internal/modules/task/... -v
```

## Endpoints da API

### Tarefas
* `GET /tasks` - Retorna a listagem completa de tarefas cadastradas.
* `POST /tasks` - Cria uma nova tarefa. Requer corpo JSON com o campo `title` (mínimo de 3 caracteres).
* `GET /tasks/{id}` - Busca os detalhes de uma tarefa específica pelo ID numérico.
* `PUT /tasks/{id}` - Atualiza o título ou o status de conclusão (`completed`) de uma tarefa existente.
* `DELETE /tasks/{id}` - Remove de forma definitiva uma tarefa do banco de dados.

## Referências Técnicas

* [Como Criar uma API REST com Go em 2026](https://golang.com.br/aprenda/api-rest-go/)
* [Migrations em Go: Banco de Dados sem Susto](https://golang.com.br/blog/migrations-go-banco-dados-producao/)
