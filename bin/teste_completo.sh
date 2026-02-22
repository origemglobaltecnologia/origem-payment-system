#!/bin/bash

GATEWAY="http://localhost:8080"
USER="admin"
PASS="123"

echo "--------------------------------------------------"
echo "🧪 INICIANDO TESTE DE CICLO DE VIDA (CRUD)"
echo "--------------------------------------------------"

# 1. LOGIN E TOKEN
echo -e "\n🔐 [1] Autenticando..."
TOKEN=$(curl -s -X POST "$GATEWAY/auth/login" \
     -H "Content-Type: application/json" \
     -d "{\"user\": \"$USER\", \"pass\": \"$PASS\"}" | sed 's/.*"token":"\([^"]*\)".*/\1/')

if [ -z "$TOKEN" ]; then echo "❌ Erro no Token"; exit 1; fi
echo "✅ Token obtido."

# 2. LISTAR TODOS (INICIAL)
echo -e "\n📋 [2] Listando todos (Deve estar vazio ou limpo):"
curl -s -X GET "$GATEWAY/payments" -H "Authorization: Bearer $TOKEN"

# 3. CRIAR PAGAMENTO
echo -e "\n\n💰 [3] Criando Pagamento (ID 1)..."
curl -s -X POST "$GATEWAY/payments" \
     -H "Authorization: Bearer $TOKEN" \
     -H "Content-Type: application/json" \
     -d '{
       "valor": 500.0,
       "descricao": "Pagamento Original",
       "metodo": "PIX",
       "status": "PENDENTE"
     }'

# 4. EDITAR PAGAMENTO 1 (UPDATE)
# Nota: Assumindo que seu endpoint de edição seja PUT /payments/{id}
echo -e "\n\n📝 [4] Editando Pagamento 1 (Alterando Status e Descrição)..."
curl -s -X PUT "$GATEWAY/payments/1" \
     -H "Authorization: Bearer $TOKEN" \
     -H "Content-Type: application/json" \
     -d '{
       "valor": 500.0,
       "descricao": "Pagamento Alterado e Validado",
       "metodo": "PIX",
       "status": "APROVADO"
     }'

# 5. LISTAR PAGAMENTO 1 (BUSCA POR ID)
echo -e "\n\n🔍 [5] Consultando apenas o Pagamento 1:"
curl -s -X GET "$GATEWAY/payments/1" \
     -H "Authorization: Bearer $TOKEN" | python3 -m json.tool

# 6. LISTAR TODOS (FINAL)
echo -e "\n📋 [6] Listagem Geral Final:"
curl -s -X GET "$GATEWAY/payments" \
     -H "Authorization: Bearer $TOKEN" | python3 -m json.tool

echo -e "\n--------------------------------------------------"
echo "✅ TESTE FINALIZADO"
echo "--------------------------------------------------"
