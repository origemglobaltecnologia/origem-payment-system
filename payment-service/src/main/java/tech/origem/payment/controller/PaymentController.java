package tech.origem.payment.controller;

import lombok.RequiredArgsConstructor;
import tech.origem.payment.model.Payment;
import tech.origem.payment.repository.PaymentRepository;
import tech.origem.payment.producer.PaymentPublisher;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentRepository repository;
    private final PaymentPublisher publisher;

    @PostMapping
    public String postPayment(@RequestBody Payment payment) {
        Payment salvo = repository.save(payment);
        publisher.publish(salvo);
        return "Pagamento " + salvo.getId() + " criado com sucesso!";
    }

    @GetMapping
    public List<Payment> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Payment getOne(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado!"));
    }

    @PutMapping("/{id}")
    public String updateStatus(@PathVariable Long id, @RequestBody String novoStatus) {
        return repository.findById(id).map(p -> {
            p.setStatus(novoStatus);
            repository.save(p); // Atualiza no banco
            publisher.publish(p); // Notifica a nuvem
            return "Pagamento " + id + " atualizado para " + novoStatus;
        }).orElse("Erro: Pagamento não encontrado.");
    }
}
