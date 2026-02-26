package tech.origem.payment.notification.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.origem.payment.notification.dto.PaymentEventDTO;

@SpringBootTest
class PaymentConsumerTest {

    @Autowired
    private PaymentConsumer paymentConsumer;

    @Test
    void testConsume() {
        // Criando o DTO em vez de um Map para o teste passar
        PaymentEventDTO event = new PaymentEventDTO();
        event.setId(1L);
        event.setStatus("APROVADO");
        event.setValor(100.0);
        event.setDescricao("Teste de DTO");

        paymentConsumer.consume(event);
    }
}
