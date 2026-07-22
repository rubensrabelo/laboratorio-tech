# Microservices na prática com Java Spring

## Descrição
Este projeto demonstra a construção de uma arquitetura de microsserviços baseada em Java e *Spring Boot*. O objetivo principal é implementar dois serviços independentes (**User** e **Email**) que se comunicam de forma assíncrona utilizando o *RabbitMQ* como *broker* de mensageria para processar o envio de e-mails de boas-vindas após o cadastro de usuários.

## Implementação
O fluxo segue um modelo de comunicação por comandos, onde o serviço de usuários atua como *producer* e o serviço de e-mails como *consumer*.

```mermaid
graph LR
%% Título do Diagrama
subgraph Plataforma [Arquitetura de Microserviços]
subgraph MS_User [User Microservice]
B[API Endpoint]
C[(User DB)]
B -->|2. Salva| C
end

subgraph RabbitMQ [RabbitMQ Broker]
D(Exchange Default) --> E[Fila: default-email]
end

subgraph MS_Email [Email Microservice]
F[Consumer/Listener]
G[(Email DB)]
H[SMTP Gmail]
F -->|6. Envia| H
F -->|7. Salva Log| G
end
end

A[Cliente] -->|1. POST /users| B
B -->|3. Publica| D
E -->|5. Consome| F

style RabbitMQ fill:#f9f9f9,stroke:#333,stroke-dasharray: 5 5
style Plataforma fill:#f5f5f5,stroke:#333
```

**Principais tecnologias utilizadas:**
* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Mensageria:** RabbitMQ (via CloudAMQP)
* **Banco de Dados:** PostgreSQL (uma base por microserviço)
* **Integração:** Spring AMQP, Spring Mail (SMTP Gmail)

## Referência
Vídeo original: [Microservices na prática com Java Spring](https://www.youtube.com/watch?v=wlYvA2b1BWI) por *Michelli Brito*.


## O que eu fiz de diferente?

- Implementei o Flyway
- Criei .env para guardar variáveis sensiveis
- Criei arquivos .sh para facilitar rodar os ms user e ms email
- Criei DTOS de criação e de resposta e fiz um mapper