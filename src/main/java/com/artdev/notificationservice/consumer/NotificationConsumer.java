package com.artdev.notificationservice.consumer;

import com.artdev.notificationservice.config.RabbitMQConfig;
import com.artdev.notificationservice.dto.NotificationRequestDTO;
import com.artdev.notificationservice.service.TemplateService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @Autowired
    private TemplateService templateService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(NotificationRequestDTO message){

        String templateContent = templateService.getTemplate(message.getTemplate());

        if(templateContent == null){
            System.out.println("Aviso: Template " + message.getTemplate() + " não encontrado no Redis!");
            templateContent = message.getMessage();
        }
        System.out.println("=== PROCESSANDO NOTIFICAÇÃO ===");
        System.out.println("Usando Template: " + templateContent);
        System.out.println("Enviando para: " + message.getDestination());
    }
}
