# 🔔 Notification Service
Serviço responsável pelo processamento de alertas e comunicações assíncronas do sistema Origem Payment.

## 🚀 Propósito
Este microsserviço atua como um **Consumer** de eventos. Ele escuta a fila de pagamentos e executa ações de saída, como logs no terminal e simulação de envio de e-mails..

## 🛠️ Tecnologias
- **Java 21 & Spring Boot 3**
- **Spring AMQP**: Protocolo para comunicação com RabbitMQ..
- **CloudAMQP**: Broker gerenciado na nuvem para garantir resiliência..
- **Lombok**: Para redução de código boilerplate.

## 🏗️ Arquitetura (SOLID)
- **SRP (Single Responsibility)**: O serviço tem a única função de processar notificações.
- **DIP (Dependency Inversion)**: A lógica de notificação depende de uma interface de e-mail, permitindo trocar o provedor (SMTP, AWS SES, Twilio) sem alterar o consumidor.

## 📡 Fluxo de Dados
1. O `payment-service` envia um JSON para a Exchange no RabbitMQ.
2. O RabbitMQ roteia a mensagem para a fila `payments.v1.payment-created`..
3. Este serviço consome a mensagem e dispara o `EmailService`.

## 🔧 Configuração no Termux
Certifique-se de que as credenciais no `src/main/resources/application.yaml` estão apontando para o Host: `jaragua.lmq.cloudamqp.com`..

Execução:
```bash
./mvnw spring-boot:run
```

## 🧪 Testes
Para garantir a qualidade, execute:
```bash
./mvnw test
```
Os testes unitários utilizam **Mockito** para simular o Broker e o serviço de e-mail, permitindo validação em ambiente offline (como no Termux).
