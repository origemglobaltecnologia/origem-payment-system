package tech.origem.payment.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para autenticação")
public record LoginRequest(
    @Schema(example = "admin")
    @NotBlank(message = "O usuário é obrigatório")
    String user, 
    
    @Schema(example = "123")
    @NotBlank(message = "A senha é obrigatória")
    String pass
) {}
