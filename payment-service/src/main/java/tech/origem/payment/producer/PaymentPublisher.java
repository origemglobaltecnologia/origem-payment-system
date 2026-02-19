package tech.origem.payment.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import tech.origem.payment.model.Payment;

@Component
@RequiredArgsConstructor
public class PaymentPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publish(Payment paymentData) {
        // Envia o objeto inteiro para que outros serviços vejam o novo status
        rabbitTemplate.convertAndSend("payment-exchange", "payment-routing-key", paymentData.toString());
    }
}
