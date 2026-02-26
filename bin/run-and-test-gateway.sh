#!/bin/bash

BASE_DIR=$(cd "$(dirname "$0")/.." && pwd)
YELLOW='\033[1;33m'
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${YELLOW}🚀 Iniciando Orquestração Completa (Auth, Notification, Gateway)...${NC}"

# Limpeza inicial
pkill -f 'java'
sleep 2

subir() {
    local servico=$1
    echo -e "${YELLOW}Instanciando $servico...${NC}"
    cd "$BASE_DIR/$servico" || exit
    ./mvnw spring-boot:run > ../logs/$servico.log 2>&1 &
    sleep 20 # Espera o serviço respirar antes de subir o próximo
}

# Cria pasta de logs se não existir
mkdir -p "$BASE_DIR/logs"

# Sobe na ordem de dependência
subir "auth-service"
subir "notification-service"
subir "gateway-server"

echo -e "${YELLOW}⏳ Aguardando mais 30s para estabilização final...${NC}"
sleep 30

echo -e "\n${GREEN}🧪 Iniciando Testes no Gateway (Porta 8080)...${NC}"
echo "----------------------------------------------------"

for i in {1..7}
do
  echo -n "Chamada #$i: "
  # Testando a rota de pagamentos (o payment-service continua OFF para testar o fallback)
  RESPONSE=$(curl -s --connect-timeout 2 -X POST http://localhost:8080/payments/pix \
    -H "Content-Type: application/json" \
    -d '{"valor": 100.0}')
  
  if [ -z "$RESPONSE" ]; then
    echo -e "${RED}Sem resposta do Gateway.${NC}"
  else
    echo -e "${NC}$RESPONSE${NC}"
  fi
  sleep 1
done

echo "----------------------------------------------------"
echo -e "${GREEN}✅ Orquestração e Teste Finalizados.${NC}"
