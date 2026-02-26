#!/bin/bash

# Configurações do seu ambiente que funcionou
GATEWAY="http://localhost:8080"
USER="admin"
PASS="123"

GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}==============================================${NC}"
echo -e "${BLUE}   SISTEMA ORIGEM - TESTE DE INTEGRAÇÃO      ${NC}"
echo -e "${BLUE}==============================================${NC}"

# 1. AUTENTICAÇÃO (Usando o padrão que deu certo no seu log)
echo -e "${YELLOW}[1/4] Autenticando...${NC}"
TOKEN=$(curl -s -X POST "$GATEWAY/auth/login" \
     -H "Content-Type: application/json" \
     -d "{\"user\": \"$USER\", \"pass\": \"$PASS\"}" | sed 's/.*"token":"\([^"]*\)".*/\1/')

if [ -z "$TOKEN" ] || [ "$TOKEN" == "null" ]; then
    echo -e "${RED}❌ Erro ao obter token. Verifique o Auth Service.${NC}"
    exit 1
fi
echo -e "${GREEN}✅ Token obtido com sucesso.${NC}"

# 2. CRIAR PAGAMENTO (POST)
echo -e "\n${YELLOW}[2/4] Criando Pagamento (ID 1)...${NC}"
CREATE_RES=$(curl -s -X POST "$GATEWAY/payments" \
     -H "Authorization: Bearer $TOKEN" \
     -H "Content-Type: application/json" \
     -d '{
       "valor": 500.0,
       "descricao": "Teste de Integracao",
       "metodo": "PIX",
       "status": "PENDENTE"
     }')

if [[ "$CREATE_RES" == *"500"* ]]; then
    echo -e "${RED}❌ Erro 500 na Criação. Verifique logs do RabbitMQ/Jackson no Payment Service.${NC}"
else
    echo -e "${GREEN}✅ Sucesso: $CREATE_RES${NC}"
fi

# 3. ATUALIZAR (PUT)
echo -e "\n${YELLOW}[3/4] Atualizando para APROVADO (Gera Notificação)...${NC}"
UPDATE_RES=$(curl -s -X PUT "$GATEWAY/payments/1" \
     -H "Authorization: Bearer $TOKEN" \
     -H "Content-Type: application/json" \
     -d '{"status": "APROVADO"}')

if [[ "$UPDATE_RES" == *"500"* ]]; then
    echo -e "${RED}❌ Erro 500 na Atualização. O RabbitMQ pode estar recusando a conexão.${NC}"
else
    echo -e "${GREEN}✅ Sucesso: $UPDATE_RES${NC}"
fi

# 4. CONSULTA FINAL
echo -e "\n${YELLOW}[4/4] Resultado Final no Banco:${NC}"
curl -s -X GET "$GATEWAY/payments/1" -H "Authorization: Bearer $TOKEN" | jq .

echo -e "\n${BLUE}==============================================${NC}"
echo -e "${GREEN}🚀 Teste Concluído!${NC}"
echo -e "Verifique se a notificação chegou no log:"
echo -e "${YELLOW}tail -n 10 ~/origem-payment-system/logs/notification.log${NC}"
echo -e "${BLUE}==============================================${NC}"
