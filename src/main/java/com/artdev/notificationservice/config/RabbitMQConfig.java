package com.artdev.notificationservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "notification.queue";

    @Bean
    public Queue notificationQueue() {
        // O 'TRUE' significa que essa fila é duravel, ou seja, ela sobreviverá a reinicializações do RabbitMQ
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        // Esse metodo é responsável por converter objetos Java em mensagens JSON e vice-versa,
        // facilitando a comunicação com RabbitMQ
        return new Jackson2JsonMessageConverter();
    }
}
