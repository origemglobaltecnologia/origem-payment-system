package tech.origem.payment.notification.service;

import tech.origem.payment.notification.dto.PaymentEventDTO;

public interface NotificationProvider {
    void send(PaymentEventDTO event);
}
