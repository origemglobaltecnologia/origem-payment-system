package tech.origem.payment.notification.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEventDTO {
    private Long id;
    private Double valor;
    private String descricao;
    private String status;
}
