package tech.origem.payment.gateway.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    public JwtAuthFilter() {
        super(Config.class);
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().value();
            String method = exchange.getRequest().getMethod().name();
            
            log.info("Processando requisicao: {} {}", method, path);

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Tentativa de acesso sem token ou formato invalido no path: {}", path);
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token ausente ou formato invalido");
            }

            try {
                String token = authHeader.substring(7);
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                return chain.filter(exchange);
            } catch (Exception e) {
                log.error("Token invalido ou expirado para o path: {}", path);
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido ou expirado");
            }
        };
    }

    public static class Config {}
}
