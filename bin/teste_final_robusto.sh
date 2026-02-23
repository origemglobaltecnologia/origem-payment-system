#!/bin/bash

# Cores para facilitar a leitura
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${BLUE}=== INICIANDO TESTE DE INTEGRAÇÃO COMPLETO ===${NC}"

# 1. Reset de Segurança (Exorcizando o ID 33)
echo -e "${YELLOW}[1/6] Limpando processos e bancos antigos...${NC}"
killall -9 java 2>/dev/null
rm -rf ~/origem-payment-system/payment-service/data/*.db
rm -rf ~/origem-payment-system/notification-service/logs/*.log

# 2. Aguardando serviços subirem (Simulação - Certifique-se de que estão rodando)
echo -e "${YELLOW}[2/6] Certifique-se de que o Auth, Payment e Notification estão online.${NC}"

# 3. Autenticação (Pegando o Token JWT)
echo -e "${YELLOW}[3/6] Solicitando Token JWT no Auth Service...${NC}"
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin", "password":"password"}' | jq -r .token)

if [ "$TOKEN" == "null" ] || [ -z "$TOKEN" ]; then
    echo "❌ Erro ao obter token. Verifique o Auth Service."
    exit 1
fi
echo -e "${GREEN}✔ Token obtido com sucesso!${NC}"

# 4. Criando um Pagamento (POST)
echo -e "${YELLOW}[4/6] Criando novo pagamento...${NC}"
CREATE_RES=$(curl -s -X POST http://localhost:8081/payments \
     -H "Authorization: Bearer $TOKEN" \
     -H "Content-Type: application/json" \
     -d '{
           "valor": 1500.0,
           "descricao": "Compra de Equipamentos",
           "metodo": "PIX",
           "status": "PENDENTE"
         }')
echo -e "${GREEN}✔ Resposta: $CREATE_RES${NC}"

# 5. Atualizando o Status (O teste das barras invertidas)
echo -e "${YELLOW}[5/6] Atualizando status para APROVADO...${NC}"
UPDATE_RES=$(curl -s -X PUT http://localhost:8081/payments/1 \
     -H "Authorization: Bearer $TOKEN" \
     -H "Content-Type: application/json" \
     -d '{"status": "APROVADO"}')
echo -e "${GREEN}✔ Resposta: $UPDATE_RES${NC}"

# 6. Verificação Final (Dados Limpos no Banco e RabbitMQ)
echo -e "${YELLOW}[6/6] Verificando integridade dos dados no banco...${NC}"
FINAL_CHECK=$(curl -s -G http://localhost:8081/payments/1 -H "Authorization: Bearer $TOKEN")

echo -e "${BLUE}---------------------------------------------${NC}"
echo -e "${YELLOW}DADOS FINAIS NO BANCO:${NC}"
echo "$FINAL_CHECK" | jq .
echo -e "${BLUE}---------------------------------------------${NC}"

echo -e "${GREEN}=== TESTE CONCLUÍDO ===${NC}"
echo -e "Verifique agora o log do Notification Service com:"
echo -e "${YELLOW}tail -n 20 ~/origem-payment-system/notification-service/logs/application.log${NC}"
