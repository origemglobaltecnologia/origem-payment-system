package tech.origem.payment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PAYMENTS_EXCHANGE = "payments.v1.events";
    public static final String PAYMENT_CREATED_QUEUE = "payments.v1.payment-created";
    public static final String ROUTING_KEY = "payment.created";

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    // Define a Fila (Durável para não perder mensagens se o Rabbit reiniciar)
    @Bean
    public Queue paymentCreatedQueue() {
        return new Queue(PAYMENT_CREATED_QUEUE, true);
    }

    // Define a Exchange (Tópico é o padrão mais flexível e profissional)
    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(PAYMENTS_EXCHANGE);
    }

    // Cria o elo entre Fila e Exchange usando uma Routing Key
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(paymentCreatedQueue())
                .to(paymentExchange())
                .with(ROUTING_KEY);
    }
}
