# Criando Rest API em Golang (clean arch + padrões de projeto) 

## Descrição
Este projeto é uma implementação prática de uma API REST robusta desenvolvida em Golang. Seguindo os princípios da **Clean Architecture**, a aplicação foi organizada em camadas para garantir a separação de responsabilidades, facilitando a manutenção, testes e a escalabilidade do código através da injeção de dependência e desacoplamento de componentes.

## Principais pontos de aprendizagem
* **Arquitetura em Camadas:** Organização do projeto em pastas como `cmd` (pontos de entrada), `internal` (lógica interna), `handlers` (camada HTTP), `use cases` (regras de negócio) e `repositories` (camada de dados).
* **Injeção de Dependência:** Utilização de interfaces para conectar camadas, permitindo a substituição de implementações (como bancos de dados ou serviços de terceiros) sem alterar a lógica de negócio.
* **Implementação de Funcionalidades:** Desenvolvimento de modelos, manipulação de JSON, tratamento de erros e criação de endpoints para cadastro e listagem de usuários.
* **Boas Práticas e Ferramentas:** Adoção do pacote `slog` para logs estruturados e uso de padrões nativos do Go para roteamento e tratamento de métodos HTTP.

## Referência
Baseado no tutorial de Leo Miranda Dev: [Criando Rest API em Golang - clean arch + padrões de projeto](https://www.youtube.com/watch?v=EXwqzrcVXKg)