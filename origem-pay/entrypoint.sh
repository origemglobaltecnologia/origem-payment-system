#!/bin/sh

# Substitui as variáveis no template e gera o env.js na raiz do Nginx
envsubst '${API_URL} ${PRODUCTION}' < /usr/share/nginx/html/env.template.js > /usr/share/nginx/html/env.js

# Inicia o Nginx (comando padrão da imagem)
exec nginx -g 'daemon off;'
