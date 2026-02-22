package tech.origem.payment.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.origem.payment.auth.model.LoginRequest;
import tech.origem.payment.auth.model.AuthResponse;
import tech.origem.payment.auth.service.JwtService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Validação simples (Padrão Profissional usa AuthenticationManager, mas manteremos a lógica funcional)
        if ("admin".equals(request.user()) && "123".equals(request.pass())) {
            String token = jwtService.generateToken(request.user());
            
            // Retorna Objeto JSON estruturado (Status 200)
            return ResponseEntity.ok(new AuthResponse(
                token, 
                "Bearer", 
                System.currentTimeMillis() + 3600000
            ));
        }

        // Retorna Erro em JSON (Status 401 Unauthorized)
        return ResponseEntity.status(401).body(Map.of(
            "erro", "Credenciais Inválidas",
            "status", 401,
            "timestamp", System.currentTimeMillis()
        ));
    }
}
