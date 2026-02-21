#!/data/data/com.termux/files/usr/bin/bash

GATEWAY_URL="http://localhost:8080"
AUTH_ENDPOINT="$GATEWAY_URL/auth/login"
PAYMENT_ENDPOINT="$GATEWAY_URL/payments"

echo "🔍 Iniciando Testes de Funcionamento..."

# 1. Teste de Login
echo -e "\n[Passo 1] Solicitando Token JWT..."
RESPONSE=$(curl -s -X POST $AUTH_ENDPOINT \
     -H "Content-Type: application/json" \
     -d '{"username":"admin", "password":"123"}')

# Tenta extrair 'token' ou 'jwt'
TOKEN=$(echo $RESPONSE | jq -r '.token // .jwt' 2>/dev/null)

if [ "$TOKEN" == "null" ] || [ -z "$TOKEN" ]; then
    echo "❌ Erro ao obter token."
    echo "Resposta do servidor: $RESPONSE"
    exit 1
fi
echo "✅ Token obtido com sucesso!"

# 2. Teste de Pagamento Válido
echo -e "\n[Passo 2] Testando Pagamento VÁLIDO (R$ 100.00)..."
curl -i -X POST $PAYMENT_ENDPOINT \
     -H "Authorization: Bearer $TOKEN" \
     -H "Content-Type: application/json" \
     -d '{"descricao":"Teste Sucesso", "valor": 100.00}'

# 3. Teste de Pagamento Inválido
echo -e "\n[Passo 3] Testando Pagamento INVÁLIDO (R$ -50.00)..."
curl -i -X POST $PAYMENT_ENDPOINT \
     -H "Authorization: Bearer $TOKEN" \
     -H "Content-Type: application/json" \
     -d '{"descricao":"Teste Erro", "valor": -50.00}'

echo -e "\n\n✨ Testes finalizados."
