package tech.origem.payment.notification.consumer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import tech.origem.payment.notification.config.RabbitMQConfig;
import tech.origem.payment.notification.dto.PaymentEventDTO;

import java.util.UUID;

@Slf4j
@Component
public class PaymentConsumer {

    private static final String MDC_KEY = "correlationId";

    // O Spring AMQP chamará este método quando houver uma mensagem na fila
    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void consume(PaymentEventDTO event, @Header(name = "correlationId", required = false) String correlationId) {
        String idParaLog = (correlationId != null) ? correlationId : UUID.randomUUID().toString();
        
        try {
            MDC.put(MDC_KEY, idParaLog);
            processar(event);
        } finally {
            MDC.remove(MDC_KEY);
        }
    }

    // Este método facilita a vida dos seus TESTES unitários e organiza o código
    public void consume(PaymentEventDTO event) {
        processar(event);
    }

    private void processar(PaymentEventDTO event) {
        log.info("🔔 [NOTIFICATION] Evento recebido - ID: {}, Status: {}, Valor: R$ {}",
                 event.getId(), event.getStatus(), event.getValor());

        if ("APROVADO".equalsIgnoreCase(event.getStatus())) {
            log.info("📧 Enviando e-mail de confirmação para o pagamento {}...", event.getId());
        }
    }
}
