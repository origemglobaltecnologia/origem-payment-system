package tech.origem.payment.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.origem.payment.notification.dto.PaymentEventDTO;

@Slf4j
@Service
public class EmailNotificationService implements NotificationProvider {

    @Override
    public void send(PaymentEventDTO event) {
        log.info("📧 [EMAIL] Preparando envio para o pagamento ID: {}...", event.getId());
        // Simulação de delay de rede
        log.info("✅ [EMAIL] Mensagem enviada com sucesso para o status: {}", event.getStatus());
    }
}
