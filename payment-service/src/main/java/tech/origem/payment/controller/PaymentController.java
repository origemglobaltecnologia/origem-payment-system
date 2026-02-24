package tech.origem.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tech.origem.payment.model.Payment;
import tech.origem.payment.dto.PaymentRequest;
import tech.origem.payment.dto.PaymentEventDTO;
import tech.origem.payment.repository.PaymentRepository;
import tech.origem.payment.producer.PaymentPublisher;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentRepository repository;
    private final PaymentPublisher publisher;

    @PostMapping
    public String postPayment(@RequestBody @Valid PaymentRequest request) {
        Payment payment = new Payment();
        payment.setValor(request.getValor());
        payment.setDescricao(request.getDescricao());
        payment.setMetodo(request.getMetodo());
        payment.setStatus("PENDENTE");

        Payment salvo = repository.save(payment);
        publishEvent(salvo);
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
    public String updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        return repository.findById(id).map(p -> {
            // Pegamos o valor da chave "status" do JSON
            String novoStatus = payload.get("status");
            if (novoStatus != null) {
                p.setStatus(novoStatus);
                repository.save(p);
                publishEvent(p);
                return "Pagamento " + id + " atualizado para " + novoStatus;
            }
            return "Erro: Campo 'status' não informado.";
        }).orElse("Erro: Pagamento não encontrado.");
    }

    private void publishEvent(Payment p) {
        PaymentEventDTO event = PaymentEventDTO.builder()
                .id(p.getId())
                .valor(p.getValor())
                .descricao(p.getDescricao())
                .status(p.getStatus())
                .build();
        publisher.publish(event);
    }
}
