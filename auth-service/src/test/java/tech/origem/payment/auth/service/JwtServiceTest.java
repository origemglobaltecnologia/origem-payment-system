package tech.origem.payment.auth.service;

import org.junit.jupiter.api.Test;
import tech.origem.payment.auth.model.AccountStatus;
import tech.origem.payment.auth.model.User;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void deveGerarTokenComPartsCorretas() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .status(AccountStatus.ACTIVE)
                .build();

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        // O regex "\\." agora será escrito corretamente no arquivo Java
        assertEquals(3, token.split("\\.").length);
    }
}
