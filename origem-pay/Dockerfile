FROM nginx:alpine

# Instala gettext para ter o comando envsubst
RUN apk add --no-cache gettext

# Copia o build do Angular (ajuste o caminho se necessário)
COPY dist/origem-pay/browser /usr/share/nginx/html

# Configura o ponto de entrada executável
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 80

ENTRYPOINT ["/bin/sh", "/entrypoint.sh"]
