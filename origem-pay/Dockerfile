# Estágio 1: Build do Angular
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration production

# Estágio 2: Servidor Nginx
FROM nginx:alpine
# Adiciona envsubst para processar variáveis de ambiente
RUN apk add --no-cache gettext

# COPIA OS ARQUIVOS DO BUILD
# Nota: No Angular 17+, o caminho costuma ser dist/origem-pay/browser
COPY --from=build /app/dist/origem-pay/browser /usr/share/nginx/html

# Copia o script de entrada
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

EXPOSE 80

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
