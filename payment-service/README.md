# 💳 Origem Payment System - Cloud
Sistema de pagamentos baseado em microsserviços, projetado para alta escalabilidade e deploy automatizado.

## 🚀 Status do CI/CD (GitHub Actions)
| Serviço | Status do Build | Docker Hub |
| :--- | :--- | :--- |
| **Auth Service** | ![Auth CI](https://github.com/origemglobaltecnologia/origem-payment-system/actions/workflows/auth-ci.yml/badge.svg) | [Imagens](https://hub.docker.com/u/origemglobaltecnologia) |
| **Payment Service** | ![Payment CI](https://github.com/origemglobaltecnologia/origem-payment-system/actions/workflows/payment-ci.yml/badge.svg) | [Imagens](https://hub.docker.com/u/origemglobaltecnologia) |

## 🏗️ Estrutura do Ecossistema
Este repositório (Monorepo) contém os seguintes serviços:
* **Gateway Server**: Porta 8080 (Ponto de entrada único).
* **Auth Service**: Porta 8081 (Segurança e JWT).
* **Payment Service**: Porta 8082 (Núcleo de transações).
* **Notification Service**: Consumidor de eventos RabbitMQ.

## 🛠️ Tecnologias Principais
* **Java 17/21 & Spring Boot 3**: Base das aplicações.
* **RabbitMQ**: Mensageria assíncrona para notificações.
* **Docker & GitHub Actions**: Pipeline automatizada de build e push.

## 🕹️ Como Rodar (Local - Termux)
Utilize os scripts de automação na raiz:
* **Iniciar**: `./start_all.sh`
* **Status**: `./status.sh`
* **Desligar**: `./stop_all.sh`

---
© 2026 Origem Global Tecnologia
