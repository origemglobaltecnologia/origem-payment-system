package tech.origem.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequest {
    @NotNull @Positive
    private Double valor;
    @NotBlank
    private String descricao;
    @NotBlank
    private String metodo;
}
