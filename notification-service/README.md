# 🔔 Notification Service

Serviço de mensageria assíncrona do sistema **Origem Payment**, responsável por processar notificações de pagamentos.

## 🛠️ Tecnologias e Padrões
- **Java 21 & Spring Boot 3**
- **RabbitMQ**: Integração com CloudAMQP.
- **Resiliência**: Implementação de Retry (3 tentativas) e Dead Letter Queue (DLQ).
- **SOLID**: Inversão de Dependência (DIP) para provedores de notificação.
- **Observabilidade**: Rastreabilidade distribuída via MDC (Correlation ID) e Health Checks via Actuator.

## 🚀 Como Executar
1. Certifique-se de que o RabbitMQ está acessível.
2. Execute o serviço:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📊 Monitoramento
- **Health Check**: [http://localhost:8083/actuator/health](http://localhost:8083/actuator/health)
- **Documentação API (via Gateway)**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 📁 Logs
Os logs são gerados em: `../logs/notification.log` com suporte a Correlation ID para rastreio entre serviços.
