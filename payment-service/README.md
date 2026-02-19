# 💳 Payment Service

O **Payment Service** é o núcleo de processamento de transações do sistema. Ele é responsável por receber requisições de pagamento via API Gateway, persistir os dados localmente e notificar outros serviços através de mensageria.

## 🚀 Tecnologias
* **Java 17 & Spring Boot**
* **Spring Data JPA**: Para persistência de dados
* **H2 Database**: Banco de dados relacional em arquivo para persistência local
* **RabbitMQ (CloudAMQP)**: Mensageria assíncrona na nuvem
* **Lombok**: Para redução de código boilerplate

## 🏗️ Arquitetura
O serviço opera na porta **8081** para evitar conflitos com o API Gateway (8080).



## 🛠️ Endpoints
### 1. Criar Pagamento
**POST** `/payments`
- **Payload**: `{"valor": 150.0, "status": "GRAVADO"}`
- **Ação**: Salva no banco H2 e envia para a exchange `payment-exchange` no RabbitMQ.

### 2. Listar Pagamentos
**GET** `/payments`
- **Retorno**: Lista JSON de todos os pagamentos persistidos no arquivo `paymentdb.mv.db`.

## 📦 Como Rodar
1. Certifique-se de que o **Gateway** está ativo na porta 8080.
2. Execute o comando:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📂 Estrutura de Dados
Os dados são persistidos na pasta `data/` na raiz do projeto:
- **Arquivo**: `data/paymentdb.mv.db`
