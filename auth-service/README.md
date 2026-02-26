# 🛡️ Origem Auth Service

Microsserviço de Identidade (IdP) responsável pela autenticação, autorização e emissão de tokens JWT para o ecossistema **Origem Payment**.

Projeto desenvolvido com foco em arquitetura moderna de microsserviços, segurança stateless e boas práticas de engenharia de software.

---

## 🚀 Tecnologias e Padrões

- Java 17
- Spring Boot 3.5.x
- Spring Security (Arquitetura Stateless)
- JWT (JSON Web Token) com RBAC (Role-Based Access Control)
- BCrypt (Criptografia de senhas - custo 10+)
- Spring Data JPA
- H2 Database
- OpenAPI / Swagger
- Docker
- Maven Wrapper

---

## 🏗️ Arquitetura e Segurança

Seguindo o Blueprint de Modernização, o serviço implementa:

1. Single Responsibility Principle  
   Responsável exclusivamente por autenticação e geração de tokens.

2. Stateless Authentication  
   Não mantém sessão no servidor. Autorização baseada em JWT.

3. Short-Lived Tokens  
   Tokens com expiração de 15 minutos para mitigação de riscos.

4. UUID v4  
   Identificadores únicos para evitar enumeração de usuários.

5. RBAC  
   Controle de acesso baseado em papéis (roles).

6. Global Exception Handling  
   Padronização de erros conforme RFC 7807 (Problem Details for HTTP APIs).

---

## 🧪 Qualidade e Testes

O projeto possui uma suíte automatizada garantindo estabilidade do contrato da API.

### ✔ Testes de Integração
Validação do fluxo completo de login (Controller → Service → Banco de Dados)

### ✔ Testes Unitários
Validação da emissão e regras de geração de tokens JWT.

### Executar testes

```bash
./mvnw test
```

---

# ⚙️ Como Executar

## 🐳 Via Docker (Recomendado)

```bash
docker build -t auth-service .
docker run -p 8081:8081 auth-service
```

O serviço ficará disponível em:

http://localhost:8081

---

## 🛠️ Via Maven (Desenvolvimento)

```bash
./mvnw spring-boot:run
```

---

# 📘 Documentação da API

Após iniciar o serviço, a documentação interativa (Swagger UI) estará disponível em:

http://localhost:8081/swagger-ui/index.html

Com ela é possível:
- Testar endpoints
- Validar contratos
- Visualizar schemas
- Simular autenticação com JWT

---

# 📦 Estrutura do Projeto

```
src/
 ├── controller
 ├── service
 ├── repository
 ├── security
 ├── config
 └── exception
```

Organizado seguindo boas práticas de separação de responsabilidades.

---

# 🔐 Fluxo de Autenticação

1. Usuário envia credenciais (email/senha)
2. Senha validada com BCrypt
3. Token JWT gerado contendo:
   - Subject (UUID)
   - Roles
   - Expiração
4. Cliente envia o token no header:

```
Authorization: Bearer <token>
```

---

# 📌 Status do Projeto

✔ 100% Funcional  
✔ Testado  
✔ Documentado  
✔ Pronto para integração com API Gateway  

---

# 👨‍💻 Autor

Cristiano Origem Camejo  
Origem . Tecnologia  
camejocristiano@gmail.com  
origemoficial.com.br  

---

Projeto voltado para conceitos avançados de microsserviços, segurança e arquitetura moderna Java Back-End.
