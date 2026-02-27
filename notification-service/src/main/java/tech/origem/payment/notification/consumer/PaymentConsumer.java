package tech.origem.payment.notification.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import tech.origem.payment.notification.config.RabbitMQConfig;
import tech.origem.payment.notification.dto.PaymentEventDTO;
import tech.origem.payment.notification.service.NotificationProvider;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final NotificationProvider notificationProvider;
    private static final String MDC_KEY = "correlationId";

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void consume(PaymentEventDTO event, @Header(name = "correlationId", required = false) String correlationId) {
        String idParaLog = (correlationId != null) ? correlationId : UUID.randomUUID().toString();
        
        try {
            MDC.put(MDC_KEY, idParaLog);
            if ("APROVADO".equalsIgnoreCase(event.getStatus())) {
                notificationProvider.send(event);
            } else {
                log.info("ℹ️ [NOTIFICATION] Evento ignorado. Status: {}", event.getStatus());
            }
        } finally {
            MDC.remove(MDC_KEY);
        }
    }
}
