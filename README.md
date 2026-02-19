# 🚀 Origem Payment System - Microservices

Este repositório contém uma solução completa de pagamentos baseada em arquitetura de microserviços, focada em escalabilidade, resiliência e boas práticas de desenvolvimento (SOLID & Clean Code).

## 🏗️ Arquitetura do Sistema
O sistema é composto pelos seguintes serviços:

* **API Gateway**: Centralizador de requisições e roteamento.
* **Auth Service**: Gerenciamento de autenticação e autorização (Em breve).
* **Payment Service**: Core business para processamento de transações.
* **Notification Service**: Processamento de eventos e envio de notificações (Em breve).


## 🛠️ Tecnologias Globais
* **Linguagem:** Java 17
* **Framework:** Spring Boot 3.x
* **Comunicação:** REST (síncrona) e RabbitMQ (assíncrona - planejado)
* **Banco de Dados:** H2 (Memória) para desenvolvimento

## 🚦 Como Rodar o Ecossistema
Para rodar o fluxo completo, os serviços devem ser iniciados na seguinte ordem:
1.  **Payment Service** (Porta 8081)
2.  **API Gateway** (Porta 8080)

## 📈 Roadmap de Desenvolvimento
- [x] Payment Service Core & Tests
- [x] API Gateway Routing
- [ ] Notification Service (RabbitMQ Integration)
- [ ] Auth Service (JWT)
