package tech.origem.payment.notification.consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.origem.payment.notification.dto.PaymentEventDTO;
import tech.origem.payment.notification.service.NotificationProvider;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentConsumerTest {

    @Mock
    private NotificationProvider notificationProvider;

    @InjectMocks
    private PaymentConsumer paymentConsumer;

    @Test
    void deveProcessarEventoComSucesso() {
        // GIVEN
        PaymentEventDTO event = new PaymentEventDTO(1L, 100.0, "Teste", "APROVADO");
        String correlationId = "test-uuid-123";

        // WHEN
        paymentConsumer.consume(event, correlationId);

        // THEN
        verify(notificationProvider, times(1)).send(event);
    }

    @Test
    void deveIgnorarEventoSeStatusNaoForAprovado() {
        // GIVEN
        PaymentEventDTO event = new PaymentEventDTO(1L, 100.0, "Teste", "PENDENTE");
        
        // WHEN
        paymentConsumer.consume(event, null);

        // THEN
        verify(notificationProvider, never()).send(any());
    }
}
