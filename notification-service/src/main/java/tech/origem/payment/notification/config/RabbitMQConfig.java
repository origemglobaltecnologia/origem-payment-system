package tech.origem.payment.notification.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String PAYMENT_QUEUE = "payments.v1.payment-created";

    @Bean
    public Queue queue() {
        // Fila durável para não perder mensagens se o broker reiniciar
        return new Queue(PAYMENT_QUEUE, true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        // Este bean garante que o JSON vindo do Rabbit seja convertido para Map/Objeto Java
        return new Jackson2JsonMessageConverter();
    }
}
