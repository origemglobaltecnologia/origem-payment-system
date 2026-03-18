# Estágio 1: Build (Node.js)
FROM node:20-alpine AS build
WORKDIR /app

# Copia apenas os arquivos de dependências para aproveitar o cache do Docker
COPY package*.json ./
RUN npm install

# Copia o restante do código fonte
COPY . .

# Gera o build de produção (os arquivos irão para /app/dist/origem-pay/browser)
RUN npm run build -- --configuration production

# Estágio 2: Runtime (Nginx)
FROM nginx:alpine

# Copia os arquivos compilados do estágio anterior para a pasta padrão do Nginx
# NOTA: Verifique se o caminho no seu projeto é exatamente 'dist/origem-pay/browser'
COPY --from=build /app/dist/origem-pay/browser /usr/share/nginx/html

# Expõe a porta 80 (padrão do Nginx dentro do container)
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
