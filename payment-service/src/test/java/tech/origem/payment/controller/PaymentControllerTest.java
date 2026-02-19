package tech.origem.payment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.origem.payment.model.Payment;
import tech.origem.payment.repository.PaymentRepository;
import tech.origem.payment.producer.PaymentPublisher;

import java.util.Optional;

class PaymentControllerTest {

    @InjectMocks
    private PaymentController controller;

    @Mock
    private PaymentRepository repository;

    @Mock
    private PaymentPublisher publisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarPagamentoComSucesso() {
        Payment p = new Payment(null, 100.0, "PENDENTE");
        Payment pSalvo = new Payment(1L, 100.0, "PENDENTE");

        when(repository.save(any(Payment.class))).thenReturn(pSalvo);

        String response = controller.postPayment(p);

        assertEquals("Pagamento 1 criado com sucesso!", response);
        verify(repository, times(1)).save(p);
        verify(publisher, times(1)).publish(pSalvo);
    }

    @Test
    void deveAtualizarStatusComSucesso() {
        Payment pExistente = new Payment(1L, 100.0, "PENDENTE");
        when(repository.findById(1L)).thenReturn(Optional.of(pExistente));

        String response = controller.updateStatus(1L, "APROVADO");

        assertEquals("Pagamento 1 atualizado para APROVADO", response);
        assertEquals("APROVADO", pExistente.getStatus());
        verify(repository, times(1)).save(pExistente);
        verify(publisher, times(1)).publish(pExistente);
    }
}
