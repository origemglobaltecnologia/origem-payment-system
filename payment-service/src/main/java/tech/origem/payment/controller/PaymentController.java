package tech.origem.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.origem.payment.dto.PaymentRequest;
import tech.origem.payment.model.Payment;
import tech.origem.payment.service.PaymentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String postPayment(@RequestBody @Valid PaymentRequest request) {
        Payment salvo = service.criarPagamento(request);
        return "Pagamento " + salvo.getId() + " criado com sucesso!";
    }

    @GetMapping
    public List<Payment> getAll() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Payment getOne(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public String updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String novoStatus = payload.get("status");
        if (novoStatus == null) {
            return "Erro: Campo 'status' não informado no JSON.";
        }
        Payment atualizado = service.atualizarStatus(id, novoStatus);
        return "Pagamento " + atualizado.getId() + " atualizado para " + atualizado.getStatus();
    }
}
