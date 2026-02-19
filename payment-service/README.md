# 💳 Payment Service - Origem System

Microserviço responsável pelo processamento e persistência de pagamentos. Parte integrante da arquitetura de microserviços da Origem Tech.

## 🛠️ Tecnologias
* **Java 17**
* **Spring Boot 3.x**
* **Spring Data JPA** (Persistência)
* **H2 Database** (Banco em memória para desenvolvimento)
* **Maven** (Gerenciador de dependências)

## 🏗️ Princípios Aplicados (SOLID)
* **Single Responsibility (S)**: Camadas de serviço e controle bem definidas.
* **Dependency Inversion (D)**: Injeção de dependência via construtor em todos os componentes.

## 🚀 Como Executar
1. Compilar: `./mvnw clean install`
2. Rodar: `./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx256M"`

## 📡 API Endpoints
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| POST | /payments | Cria um novo pagamento (Status Inicial: PENDING) |
