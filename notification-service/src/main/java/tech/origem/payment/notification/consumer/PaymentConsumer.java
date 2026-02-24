package tech.origem.payment.notification.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tech.origem.payment.notification.config.RabbitMQConfig;
import tech.origem.payment.notification.dto.PaymentEventDTO;

@Slf4j
@Component
public class PaymentConsumer {

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void consume(PaymentEventDTO event) {
        log.info("🔔 [NOTIFICATION] Recebido: ID {}, Status {}, R$ {}", 
                 event.getId(), event.getStatus(), event.getValor());

        if ("APROVADO".equalsIgnoreCase(event.getStatus())) {
            log.info("📧 Enviando confirmação para o serviço de e-mail...");
        }
    }
}
