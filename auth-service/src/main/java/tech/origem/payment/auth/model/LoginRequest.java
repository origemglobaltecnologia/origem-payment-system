package tech.origem.payment.auth.model;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para captura de credenciais via JSON com validação Bean Validation
 */
public record LoginRequest(
    @NotBlank(message = "O usuário é obrigatório")
    String user, 
    
    @NotBlank(message = "A senha é obrigatória")
    String pass
) {}
