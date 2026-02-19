package tech.origem.payment.notification.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String PAYMENT_QUEUE = "payments.v1.payment-created";

    @Bean
    public Queue queue() {
        return new Queue(PAYMENT_QUEUE, true);
    }
}
