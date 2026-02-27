# 💳 Payment Service (Produção)

Este microsserviço gerencia o ciclo de vida de pagamentos no Ecossistema Origem, utilizando arquitetura em camadas e mensageria orientada a eventos.

## 🚀 Tecnologias
* **Java 21 / Spring Boot 3.5.x**
* **Persistence**: H2 Database (File Mode em `./data/paymentdb`)
* **Messaging**: RabbitMQ (Topic Exchange)
* **Observability**: MDC Correlation ID & SLF4J

## ✅ Funcionalidades Profissionais
* **Correlation ID**: Todas as requisições recebem um `X-Correlation-ID` no header e nos logs para rastreamento ponta-a-ponta.
* **Resiliência**: Declaração automática de filas e exchanges no startup.
* **Global Exception Handling**: Respostas padronizadas para erros de validação e infraestrutura.
* **Service Layer**: Lógica de negócio isolada com suporte a transações ACID.

## 🛠️ Comandos
* **Build**: `./mvnw clean install -DskipTests`
* **Testes**: `./mvnw test`
* **Execução**: `./mvnw spring-boot:run`

## 📊 Observabilidade
Os logs são gerados na raiz do ecossistema e incluem o ID de correlação:
`tail -f ../logs/payment-service.log`

Exemplo de log:
`2026-02-27 09:45:00 [f47ac10b-...] INFO  tech.origem.payment.service.PaymentService - Processando novo pagamento...`

## 📂 Estrutura de Pastas Externa
* **Logs**: `../logs/payment-service.log`
* **Database**: `./data/paymentdb.mv.db`
