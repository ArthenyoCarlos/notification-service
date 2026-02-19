package com.artdev.notificationservice.consumer;

import com.artdev.notificationservice.config.RabbitMQConfig;
import com.artdev.notificationservice.dto.NotificationRequestDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterConsumer {

    @RabbitListener(queues = RabbitMQConfig.DLQ_NAME)
    public void processFailedMessages(NotificationRequestDTO message) {
        // Aqui simulamos um alerta para o administrador
        System.err.println("🚨 ALERTA DE SISTEMA: Notificação falhou permanentemente!");
        System.err.println("Destinatário: " + message.getDestination());
        System.err.println("Motivo: Retries esgotados após 3 tentativas.");

        System.out.println("Aguardando intervenção manual para o template: " + message.getTemplate());
    }
}
