package tech.origem.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.origem.payment.dto.PaymentEventDTO;
import tech.origem.payment.dto.PaymentRequest;
import tech.origem.payment.model.Payment;
import tech.origem.payment.producer.PaymentPublisher;
import tech.origem.payment.repository.PaymentRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentPublisher publisher;

    @Transactional
    public Payment criarPagamento(PaymentRequest request) {
        log.info("Processando novo pagamento: {}", request.getDescricao());
        
        Payment payment = new Payment();
        payment.setValor(request.getValor());
        payment.setDescricao(request.getDescricao());
        payment.setMetodo(request.getMetodo());
        payment.setStatus("PENDENTE");

        Payment salvo = repository.save(payment);
        notificarEvento(salvo);
        
        return salvo;
    }

    public List<Payment> listarTodos() {
        return repository.findAll();
    }

    public Payment buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento " + id + " não encontrado."));
    }

    @Transactional
    public Payment atualizarStatus(Long id, String novoStatus) {
        Payment payment = buscarPorId(id);
        payment.setStatus(novoStatus.toUpperCase());
        
        Payment atualizado = repository.save(payment);
        notificarEvento(atualizado);
        
        log.info("Status do pagamento {} alterado para {}", id, novoStatus);
        return atualizado;
    }

    private void notificarEvento(Payment p) {
        PaymentEventDTO event = PaymentEventDTO.builder()
                .id(p.getId())
                .valor(p.getValor())
                .descricao(p.getDescricao())
                .status(p.getStatus())
                .build();
        publisher.publish(event);
    }
}
