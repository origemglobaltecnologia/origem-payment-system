package tech.origem.payment.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPublisher {

    private final RabbitTemplate rabbitTemplate;
    
    // Nome da fila sincronizado com o Notification Service
    private static final String QUEUE_NAME = "payments.v1.payment-created";

    public void publish(Object paymentData) {
        // Agora o método se chama 'publish', exatamente como o Controller quer
        rabbitTemplate.convertAndSend(QUEUE_NAME, paymentData);
    }
}
