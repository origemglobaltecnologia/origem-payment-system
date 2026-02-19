package tech.origem.payment.service;

import tech.origem.payment.model.Payment;
import tech.origem.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository repository;

    @InjectMocks
    private PaymentService service;

    @Test
    void shouldCreatePaymentWithPendingStatus() {
        Payment payment = new Payment(null, new BigDecimal("100.0"), null, "test@tech.com");
        when(repository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment result = service.createPayment(payment);

        assertEquals("PENDING", result.getStatus());
        verify(repository, times(1)).save(payment);
    }
}
