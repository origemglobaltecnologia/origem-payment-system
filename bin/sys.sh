#!/data/data/com.termux/files/usr/bin/bash
DIR="$(cd "$(dirname "$0")" && pwd)"
source "$DIR/services.conf"

start_service() {
    local name=$1 sub=$2 port=$3 key=$4
    local TARGET_PATH="$ROOT_DIR/$sub"
    
    # JVM_OPTS otimizadas: 80MB de heap max, serial GC para pouca CPU
    local JVM_OPTS="-Xms32m -Xmx80m -XX:+UseSerialGC -XX:TieredStopAtLevel=1 -Xss256k -Xshare:off"

    echo "🌱 Subindo $name (Porta $port)..."
    
    local JAR_FILE=$(find "$TARGET_PATH/target" -name "*.jar" ! -name "*-sources.jar" | head -n 1)

    if [ -z "$JAR_FILE" ]; then
        echo "❌ Erro: JAR não encontrado em $sub. Rode './mvnw clean install' no serviço."
        return
    fi

    # Redireciona stdout e stderr para o log centralizado
    nohup java $JVM_OPTS -jar "$JAR_FILE" > "$LOG_DIR/${key}.log" 2>&1 &
}

case "$1" in
    up)
        echo "🚀 Iniciando Ecossistema (H2 Embedded Mode)..."
        mkdir -p "$LOG_DIR"
        pkill -9 java 2>/dev/null

        for item in "${SERVICES[@]}"; do
            IFS=':' read -r name sub port key <<< "$item"
            start_service "$name" "$sub" "$port" "$key"
            echo "⏳ Aguardando estabilização..."
            sleep 25
        done
        echo "✨ Todos os serviços foram disparados!"
        ;;

    status)
        echo -e "\n📊 Monitor de Recursos (Limite: 80MB/srv):"
        echo "----------------------------------------------------"
        printf "%-20s | %-8s | %-8s\n" "Serviço" "Status" "RAM (RSS)"
        echo "----------------------------------------------------"
        for item in "${SERVICES[@]}"; do
            IFS=':' read -r name sub port key <<< "$item"
            pid=$(pgrep -f "$sub" | head -n 1)
            if [ -n "$pid" ]; then
                mem=$(ps -o rss= -p "$pid" | awk '{print int($1/1024) "MB"}' 2>/dev/null || echo "??")
                printf "%-20s | \e[32mON\e[0m     | %-8s\n" "$name" "$mem"
            else
                printf "%-20s | \e[31mOFF\e[0m    | -\n" "$name"
            fi
        done
        ;;

    down)
        pkill -9 java
        echo "✅ Todos os processos Java foram encerrados."
        ;;
    
    logs)
        tail -f $LOG_DIR/*.log
        ;;
esac
