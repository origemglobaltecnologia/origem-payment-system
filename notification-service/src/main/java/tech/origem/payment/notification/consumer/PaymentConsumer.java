package tech.origem.payment.notification.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tech.origem.payment.notification.config.RabbitMQConfig;
import java.util.Map;

@Slf4j
@Component
public class PaymentConsumer {

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void consume(Map<String, Object> paymentData) {
        log.info("🔔 [NOTIFICATION] Processando atualização de pagamento...");
        
        Object id = paymentData.get("id");
        Object status = paymentData.get("status");
        Object valor = paymentData.get("valor");

        log.info("✅ Pagamento ID: {} | Novo Status: {} | Valor: R$ {}", id, status, valor);
        
        if ("APROVADO".equals(status)) {
            log.info("📧 Enviando e-mail de confirmação para o cliente...");
        }
    }
}
