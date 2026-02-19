package com.artdev.notificationservice.service;

import com.artdev.notificationservice.config.RabbitMQConfig;
import com.artdev.notificationservice.dto.NotificationRequestDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate; // Ferramenta para enviar mensagens para filas RabbitMQ

    public void enqueueNotification(NotificationRequestDTO request){
        System.out.println("Enviando para a fila: " + request.getDestination());

        // Enviando para a fila
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, request);
    }
}
