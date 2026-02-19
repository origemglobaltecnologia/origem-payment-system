package tech.origem.payment.service;

import tech.origem.payment.model.Payment;
import tech.origem.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Payment createPayment(Payment payment) {
        payment.setStatus("PENDING");
        return repository.save(payment);
    }
}
