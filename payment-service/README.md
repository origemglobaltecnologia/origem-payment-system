# 💳 Payment Service (Concluído)

Este microsserviço faz parte do Ecossistema Origem. Ele gerencia o ciclo de vida dos pagamentos.

## ✅ Funcionalidades Implementadas
* **Persistência Local**: Banco H2 em `./data/paymentdb`.
* **Mensageria**: Publicação no RabbitMQ para o tópico de notificações.
* **Logs Centralizados**: Enviados para `../logs/` na raiz do ecossistema.
* **Documentação**: Swagger UI disponível em `/swagger-ui.html`.
* **Testes**: Suíte de integração com MockMVC e RabbitMock.

## 🛠️ Comandos Úteis
* **Rodar Testes**: `./mvnw test`
* **Rodar App**: `./mvnw spring-boot:run`

## 📂 Arquitetura de Arquivos Externa
* **Logs**: `../logs/payment-service.log`
* **Database**: `./data/paymentdb.mv.db`
