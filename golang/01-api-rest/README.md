## Criando Rest API em Golang (clean arch + padrões de projeto)

## Descrição

Este projeto é uma implementação prática de uma API REST robusta desenvolvida em Golang. Seguindo os princípios da Clean Architecture, a aplicação foi organizada em camadas para garantir a separação de responsabilidades, facilitando a manutenção, testes e a escalabilidade do código através da injeção de dependência e desacoplamento de componentes.

------------------------------

## Principais pontos de aprendizagem

* **Arquitetura em Camadas:** Organização do projeto seguindo os padrões Go em pastas como cmd para os pontos de entrada e executáveis, e internal para os componentes privados e não exportáveis da aplicação.
* **Desacoplamento e Inversão de Controle:** Divisão clara entre as camadas de handlers (HTTP nativo), usecases (regras de negócio) e repositories (armazenamento em memória), conectadas exclusivamente por interfaces para permitir a troca ágil de tecnologias.
* **Lógica de Negócio Dinâmica:** Implementação de DTOs para tráfego seguro de dados entre as fronteiras da API, geração automática de UUIDs e validação de regras de negócio, como a checagem impedindo o cadastro de e-mails duplicados.
* **Logs Estruturados Nativos:** Adoção do pacote padrão log/slog configurado globalmente em formato JSON para garantir alto desempenho e facilidade na indexação e rastreamento de dados em ambientes de produção.
* **Rotas e Middlewares de Alta Performance:** Uso dos novos recursos nativos do Go para inferência de métodos HTTP no roteamento e criação de um middleware personalizado para monitorar o tempo de execução e auditar o status code de todas as requisições.

------------------------------
## Estrutura do Projeto

.
├── cmd
│   ├── api
│   │   └── main.go
│   └── client
│       └── main.go
├── go.mod
├── go.sum
├── internal
│   ├── handlers
│   │   ├── user_handler.go
│   │   └── user_handler_test.go
│   ├── models
│   │   ├── errors.go
│   │   └── user.go
│   ├── repositories
│   │   ├── memory_user_repository.go
│   │   ├── memory_user_repository_test.go
│   │   └── user_repository.go
│   ├── usecases
│   │   ├── user_usecase.go
│   │   ├── user_usecase_interface.go
│   │   └── user_usecase_test.go
│   └── web
│       ├── middleware
│       │   └── logger.go
│       └── router
│           └── user_router.go
└── README.md

------------------------------
## Implementações Extras e Diferenciais

Além do escopo original apresentado na referência base, foram adicionadas melhorias arquiteturais e de qualidade para aproximar o projeto de um ambiente de produção real:

* **Middleware de Auditoria:** Criação de uma camada interceptadora HTTP nativa (logger.go) que envolve o roteador para calcular o tempo de execução exato de cada requisição e capturar os códigos de status retornados, centralizando os dados de tráfego no slog.
* **Roteamento Desacoplado:** Substituição do bloco de condicionais internas por rotas declarativas nativas (user_router.go) aproveitando os novos recursos do Go, o que eliminou os loops de checagem manuais por método HTTP.
* **Otimização de Performance no Repositório:** Refatoração do mecanismo de busca de e-mails únicos no repositório de memória, substituindo uma varredura linear de complexidade O(n) por uma indexação via mapa de complexidade O(1).
* **Testes Unitários:** Implementação de uma suíte completa de testes automatizados cobrindo os caminhos felizes e tristes:

------------------------------

## Referência
Baseado no tutorial de Leo Miranda Dev: [Criando Rest API em Golang - clean arch + padrões de projeto](https://www.youtube.com/watch?v=EXwqzrcVXKg)

