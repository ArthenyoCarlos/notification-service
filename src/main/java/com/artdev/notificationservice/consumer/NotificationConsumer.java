package com.artdev.notificationservice.consumer;

import com.artdev.notificationservice.config.RabbitMQConfig;
import com.artdev.notificationservice.dto.NotificationRequestDTO;
import com.artdev.notificationservice.service.TemplateService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(NotificationRequestDTO message){

        String templateContent = templateService.getTemplate(message.getTemplate());

        String finalMessage = (templateContent != null) ? templateContent : message.getMessage();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(message.getDestination());
        email.setSubject("Nova Notificação do Sistema");
        email.setText(finalMessage);

        try {
            mailSender.send(email);
            System.out.println("E-mail enviado com sucesso para: " + message.getDestination());
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
