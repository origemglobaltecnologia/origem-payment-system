package tech.origem.payment.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.origem.payment.auth.model.LoginRequest;
import tech.origem.payment.auth.model.AuthResponse;
import tech.origem.payment.auth.model.User;
import tech.origem.payment.auth.service.JwtService;
import tech.origem.payment.auth.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.user());

        // matches(senha_aberta, senha_criptografada)
        if (userOpt.isPresent() && passwordEncoder.matches(request.pass(), userOpt.get().getPassword())) {
            User user = userOpt.get();
            String token = jwtService.generateToken(user.getUsername());
            
            AuthResponse response = new AuthResponse(
                token,
                "Bearer",
                System.currentTimeMillis() + 3600000
            );
            
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(Map.of(
            "erro", "Credenciais Inválidas",
            "status", 401,
            "timestamp", System.currentTimeMillis()
        ));
    }
}
