## Criando Rest API em Golang (clean arch + padrões de projeto)

## Descrição

Este projeto é uma implementação prática de uma API REST robusta desenvolvida em Golang. Seguindo os princípios da Clean Architecture, a aplicação foi organizada em camadas para garantir a separação de responsabilidades, facilitando a manutenção, testes e a escalabilidade do código através da injeção de dependência e desacoplamento de componentes.

---

## Principais pontos de aprendizagem

* **Arquitetura em Camadas:** Organização do projeto seguindo os padrões Go em pastas como cmd para os pontos de entrada e executáveis, e internal para os componentes privados e não exportáveis da aplicação.
* **Desacoplamento e Inversão de Controle:** Divisão clara entre as camadas de handlers (HTTP nativo), usecases (regras de negócio) e repositories (armazenamento em memória), conectadas exclusivamente por interfaces para permitir a troca ágil de tecnologias.
* **Lógica de Negócio Dinâmica:** Implementação de DTOs para tráfego seguro de dados entre as fronteiras da API, geração automática de UUIDs e validação de regras de negócio, como a checagem impedindo o cadastro de e-mails duplicados.
* **Logs Estruturados Nativos:** Adoção do pacote padrão log/slog configurado globalmente em formato JSON para garantir alto desempenho e facilidade na indexação e rastreamento de dados em ambientes de produção.
* **Rotas e Middlewares de Alta Performance:** Uso dos novos recursos nativos do Go para inferência de métodos HTTP no roteamento e criação de um middleware personalizado para monitorar o tempo de execução e auditar o status code de todas as requisições.

---

## Referência

Baseado no tutorial de Leo Miranda Dev: [Criando Rest API em Golang - clean arch + padrões de projeto](https://www.youtube.com/watch?v=EXwqzrcVXKg)
