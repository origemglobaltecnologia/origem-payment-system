#!/bin/sh

# O 'executável' que escreve o env.js baseado no export do cluster
envsubst < /usr/share/nginx/html/assets/env.template.js > /usr/share/nginx/html/assets/env.js

echo "----------------------------------------------------"
echo "Configuração de Runtime aplicada no Origem Pay!"
echo "API_URL definida para: $API_URL"
echo "----------------------------------------------------"

# Inicia o Nginx
nginx -g "daemon off;"
