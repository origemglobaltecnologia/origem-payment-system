package tech.origem.payment.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.origem.payment.model.Payment;
import tech.origem.payment.repository.PaymentRepository;
import tech.origem.payment.producer.PaymentPublisher;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentRepository repository;

    @MockBean
    private PaymentPublisher publisher;

    @Test
    public void shouldReturnAllPayments() throws Exception {
        // Ajustado para o novo construtor: Long id, Double valor, String descricao, String metodo, String status
        Payment p1 = new Payment(1L, 100.0, "Teste 1", "PIX", "PENDENTE");
        Payment p2 = new Payment(2L, 200.0, "Teste 2", "CARTAO", "APROVADO");

        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void shouldUpdateStatus() throws Exception {
        Payment p1 = new Payment(1L, 100.0, "Teste 1", "PIX", "PENDENTE");
        
        when(repository.findById(1L)).thenReturn(Optional.of(p1));
        when(repository.save(any(Payment.class))).thenReturn(p1);

        // Enviando JSON para o PUT conforme o novo Controller ajustado
        mockMvc.perform(put("/payments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"APROVADO\"}"))
                .andExpect(status().isOk());
    }
}
