package tech.origem.payment.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.origem.payment.auth.service.JwtService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestParam String user, @RequestParam String pass) {
        if ("admin".equals(user) && "123".equals(pass)) {
            return jwtService.generateToken(user);
        }
        return "Erro: Credenciais Inválidas";
    }
}
