package tech.origem.payment.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import tech.origem.payment.config.RabbitMQConfig;

@Component
@RequiredArgsConstructor
public class PaymentPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(Object paymentData) {
        // O padrão profissional usa Exchange + Routing Key
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PAYMENTS_EXCHANGE, 
                RabbitMQConfig.ROUTING_KEY, 
                paymentData
        );
    }
}
