package tech.origem.payment.notification.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tech.origem.payment.notification.config.RabbitMQConfig;

@Slf4j
@Component
public class PaymentConsumer {

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void consume(String message) {
        log.info("🔔 [NOTIFICATION] Mensagem capturada na nuvem: {}", message);
    }
}
