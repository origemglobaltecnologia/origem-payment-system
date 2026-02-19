# 💳 Payment Service

O **Payment Service** é o núcleo de transações do sistema. Ele gerencia o estado dos pagamentos, persiste dados em um banco de dados relacional e notifica o ecossistema via mensagens assíncronas.

## 🚀 Tecnologias
* **Spring Boot 3 + Java 17**
* **Spring Data JPA & H2 Database**: Persistência em arquivo local (`paymentdb.mv.db`).
* **RabbitMQ (CloudAMQP)**: Mensageria assíncrona para atualizações de status.
* **JUnit 5 & Mockito**: Testes unitários para garantir a lógica de negócio.

## 🏗️ Arquitetura e Portas
Este serviço opera na porta **8081**. Todas as requisições externas devem preferencialmente passar pelo **API Gateway (8080)**.



## 🛠️ Endpoints Disponíveis (via Gateway)

| Método | Endpoint | Função |
| :--- | :--- | :--- |
| **POST** | `/payments` | Cria um pagamento e envia para a nuvem. |
| **GET** | `/payments` | Lista todos os registros do banco H2. |
| **GET** | `/payments/{id}` | Consulta detalhada de uma transação. |
| **PUT** | `/payments/{id}` | Atualiza o status e notifica o RabbitMQ. |

## 🕹️ Gerenciamento de Processos (Scripts)
Para facilitar o uso no Termux sem múltiplas abas, utilize os scripts na raiz:

* **Iniciar**: `./start_all.sh` (Sobe as APIs em background e gera logs).
* **Status**: `./status.sh` (Verifica se as portas 8080/8081 estão ouvindo).
* **Desligar**: `./stop_all.sh` (Encerra os processos de forma limpa).

## 🧪 Suítes de Teste
1.  **Testes Unitários**:
    ```bash
    ./mvnw test
    ```
2.  **Testes de Integração (API)**:
    ```bash
    ./test_api.sh
    ```

## 📂 Dados e Logs
* **Banco de Dados**: Localizado em `data/paymentdb.mv.db`.
* **Logs**: Gerados em `payment.log` ao utilizar o script de inicialização.

## 🛑 Troubleshooting
Caso encontre o erro "Port already in use", execute:
```bash
fuser -k 8081/tcp
```
