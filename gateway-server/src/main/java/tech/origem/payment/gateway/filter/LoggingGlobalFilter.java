package tech.origem.payment.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LoggingGlobalFilter.class);
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        
        // Atribuímos a uma variável final para ser usada com segurança na lambda
        final String correlationId = headers.getFirst(CORRELATION_ID_HEADER) != null 
                ? headers.getFirst(CORRELATION_ID_HEADER) 
                : UUID.randomUUID().toString();

        // Adiciona o ID nos headers da requisição
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(exchange.getRequest().mutate()
                        .header(CORRELATION_ID_HEADER, correlationId)
                        .build())
                .build();

        final String path = exchange.getRequest().getPath().value();
        final String method = exchange.getRequest().getMethod().name();

        log.info("🚀 [GATEWAY] Recebendo {} para {} | ID: {}", method, path, correlationId);

        return chain.filter(mutatedExchange).then(Mono.fromRunnable(() -> {
            log.info("🏁 [GATEWAY] Resposta enviada para {} | ID: {}", path, correlationId);
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
