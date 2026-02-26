#!/bin/bash
PROJECT_ROOT=~/origem-payment-system
SERVICES=("auth-service" "gateway-server" "payment-service" "notification-service")

echo "🚀 Iniciando compilação em massa..."

for SERVICE in "${SERVICES[@]}"; do
    echo "📦 Compilando $SERVICE..."
    cd $PROJECT_ROOT/$SERVICE
    ./mvnw clean package -DskipTests
    
    if [ $? -eq 0 ]; then
        echo "✅ $SERVICE compilado com sucesso!"
    else
        echo "❌ Falha na compilação de $SERVICE"
        exit 1
    fi
done

echo "🎉 Todos os serviços foram compilados e estão prontos em target/"
