package com.artdev.notificationservice.controller;

import com.artdev.notificationservice.dto.NotificationRequestDTO;
import com.artdev.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequestDTO request){
        // Chamando o serviço para enfileirar a notificação
        notificationService.enqueueNotification(request);

        return ResponseEntity.accepted().body("Notification queued!");
    }
}
