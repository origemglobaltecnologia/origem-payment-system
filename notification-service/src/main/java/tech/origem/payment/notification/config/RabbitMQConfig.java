package tech.origem.payment.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String PAYMENT_QUEUE = "payments.v1.payment-created";
    public static final String PAYMENT_DLQ = "payments.v1.payment-created.dlq";
    public static final String PAYMENT_DLX = "payments.v1.payment-created.dlx";

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(PAYMENT_QUEUE)
                .withArgument("x-dead-letter-exchange", PAYMENT_DLX)
                .build();
    }

    @Bean
    public Queue dlq() {
        return QueueBuilder.durable(PAYMENT_DLQ).build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange(PAYMENT_DLX);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(dlq()).to(deadLetterExchange());
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
