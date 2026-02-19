package tech.origem.payment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;

@Slf4j
@Service
public class PaymentService {
    public void process(Map<String, Object> paymentData) {
        log.info("⚙️ Processando lógica de negócio para o pagamento...");
    }
}
