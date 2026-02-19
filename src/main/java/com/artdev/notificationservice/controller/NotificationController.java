package com.artdev.notificationservice.controller;

import com.artdev.notificationservice.dto.NotificationRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequestDTO request){
        System.out.println("Recebemos um pedido para: " + request.getDestination());
        System.out.println("Canal: " + request.getChannel());

        return ResponseEntity.accepted().body("Notification sent to queue successfully!");
    }
}
