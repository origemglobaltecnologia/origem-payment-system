package tech.origem.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.origem.payment.dto.PaymentRequest;
import tech.origem.payment.producer.PaymentPublisher;
import tech.origem.payment.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentPublisher publisher;

    @BeforeEach
    void setup() {
        // Limpa o banco antes de cada teste para garantir isolamento
        repository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um pagamento e disparar evento com sucesso")
    void deveCriarPagamentoComSucesso() throws Exception {
        // GIVEN
        PaymentRequest request = new PaymentRequest();
        request.setValor(250.0);
        request.setDescricao("Assinatura Mensal");
        request.setMetodo("PIX");

        // WHEN
        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // THEN
        assertEquals(1, repository.count(), "Deveria haver exatamente 1 pagamento no banco");
        verify(publisher, times(1)).publish(any());
    }
}
