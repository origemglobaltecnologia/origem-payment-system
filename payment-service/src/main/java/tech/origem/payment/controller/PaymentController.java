package tech.origem.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.origem.payment.dto.PaymentRequest;
import tech.origem.payment.model.Payment;
import tech.origem.payment.service.PaymentService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment postPayment(@RequestBody @Valid PaymentRequest request) {
        // Retorna o objeto completo para o Angular receber um JSON válido
        return service.criarPagamento(request);
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
    public Payment updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String novoStatus = payload.get("status");
        if (novoStatus == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo 'status' não informado no JSON.");
        }
        // Retorna o objeto atualizado
        return service.atualizarStatus(id, novoStatus);
    }
}
