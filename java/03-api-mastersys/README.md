# MasterSys

## Descrição

O MasterSys é uma aplicação desenvolvida para gerenciamento de academias, permitindo o controle de alunos, planos, matrículas e demais operações relacionadas ao ambiente de uma academia.

O projeto foi utilizado como estudo prático para revisar conceitos do ecossistema Spring Boot e aplicar boas práticas de desenvolvimento.

## Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Docker Compose
- Maven

## O que eu aprendi

Durante o desenvolvimento deste projeto, pude revisar e aprofundar conhecimentos em diversos recursos do Spring Boot, com destaque para:

- Revisão da arquitetura e dos principais componentes do framework Spring Boot.
- Utilização do padrão **Specification**, permitindo criar consultas dinâmicas, reutilizáveis e de fácil manutenção utilizando Spring Data JPA.
- Organização de uma aplicação seguindo boas práticas de estruturação e separação de responsabilidades.

## Melhorias implementadas

Além do conteúdo apresentado na referência utilizada, implementei algumas melhorias para tornar o projeto mais próximo de um ambiente real:

- Configuração de variáveis de ambiente utilizando arquivo `.env`.
- Banco de dados PostgreSQL executando em container Docker por meio do Docker Compose.
- Scripts `.sh` para automatizar a criação, inicialização e remoção dos containers, além da execução da aplicação.
- Implementação de exceções personalizadas para melhorar o tratamento de erros e padronizar as respostas da API.

## Referências

- Matheus Leandro — [Spring Boot na Prática: Projetos Reais do Zero](https://www.youtube.com/playlist?list=PLCUSYmPGwekepAli6UoI4dyxZ0_AtPK7f)