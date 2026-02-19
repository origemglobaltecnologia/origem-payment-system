package tech.origem.payment.controller;

import tech.origem.payment.model.Payment;
import tech.origem.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        return ResponseEntity.ok(service.createPayment(payment));
    }
}
