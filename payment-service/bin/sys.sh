#!/data/data/com.termux/files/usr/bin/bash
DIR="$(cd "$(dirname "$0")" && pwd)"
source "$DIR/services.conf"

start_service() {
    local name=$1 sub=$2 port=$3 key=$4
    local TARGET_PATH="$ROOT_DIR/$sub"

    # JVM_OPTS otimizadas para Termux: Foco em estabilidade e baixo consumo
    local JVM_OPTS="-Xms32m -Xmx96m -XX:+UseSerialGC -XX:TieredStopAtLevel=1 -Xss256k -Xshare:off"

    echo "🌱 [STARTING] $name na porta $port..."

    local JAR_FILE=$(find "$TARGET_PATH/target" -name "*.jar" ! -name "*-sources.jar" | head -n 1)

    if [ -z "$JAR_FILE" ]; then
        echo "❌ [ERRO] JAR não encontrado em $sub/target. Compile com 'mvn clean install'."
        return
    fi

    # Inicia o Java e redireciona logs
    nohup java $JVM_OPTS -jar "$JAR_FILE" > "$LOG_DIR/${key}.log" 2>&1 &
}

case "$1" in
    up)
        echo "🚀 Iniciando Ecossistema Origem (Modo Robusto)..."
        mkdir -p "$LOG_DIR"
        
        echo "🧹 [CLEAN] Derrubando processos antigos e limpando bancos de dados..."
        pkill -9 java 2>/dev/null
        
        # Limpeza vital para evitar IDs duplicados (como o ID 33)
        rm -rf $ROOT_DIR/payment-service/data/*.db
        rm -rf $ROOT_DIR/auth-service/data/*.db
        rm -f $LOG_DIR/*.log
        
        sleep 2

        for item in "${SERVICES[@]}"; do
            IFS=':' read -r name sub port key <<< "$item"
            start_service "$name" "$sub" "$port" "$key"
            
            # O Auth precisa de tempo extra antes do Payment tentar conectar
            if [ "$key" == "auth" ]; then
                echo "⏳ Aguardando Auth-Service estabilizar..."
                sleep 20
            else
                echo "⏳ Aguardando $name..."
                sleep 15
            fi
        done
        echo -e "\n✨ [SUCESSO] Todos os serviços foram disparados!"
        echo "📌 Use './sys.sh status' para monitorar."
        ;;

    status)
        echo -e "\n📊 Monitor de Recursos (Limite: ~96MB/srv):"
        echo "----------------------------------------------------"
        printf "%-20s | %-8s | %-8s\n" "Serviço" "Status" "RAM (RSS)"
        echo "----------------------------------------------------"
        for item in "${SERVICES[@]}"; do
            IFS=':' read -r name sub port key <<< "$item"
            # Busca o PID exato pelo nome do JAR ou pasta
            pid=$(pgrep -f "$sub" | head -n 1)
            if [ -n "$pid" ]; then
                mem=$(ps -o rss= -p "$pid" | awk '{print int($1/1024) "MB"}' 2>/dev/null || echo "??")
                printf "%-20s | \e[32mON\e[0m     | %-8s\n" "$name" "$mem"
            else
                printf "%-20s | \e[31mOFF\e[0m    | -\n" "$name"
            fi
        done
        echo "----------------------------------------------------"
        ;;

    down)
        echo "🛑 Encerrando todos os serviços..."
        pkill -9 java
        echo "✅ Ecossistema desligado."
        ;;

    logs)
        # Segue todos os logs ao mesmo tempo com prefixos
        tail -f $LOG_DIR/*.log
        ;;
    
    *)
        echo "Uso: $0 {up|status|down|logs}"
        exit 1
        ;;
esac
