package tech.origem.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEventDTO {
    private Long id;
    private Double valor;
    private String descricao;
    private String status;
}
