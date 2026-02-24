#!/bin/bash
echo "🛑 Parando todos os processos Java..."
pkill -9 java
echo "🧹 Liberando portas 8080-8085..."
for port in {8080..8085}; do
    fuser -k $port/tcp > /dev/null 2>&1
done
echo "✅ Ambiente limpo!"
