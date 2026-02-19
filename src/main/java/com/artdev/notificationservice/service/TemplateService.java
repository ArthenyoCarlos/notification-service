package com.artdev.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Autowired
    private StringRedisTemplate redisTemplate; //Ferramenta para manipular dados em cache

    public void saveTemplate(String name, String content) {
        // Salva um template (Ex: chave="welcome", valor="Olá {nome}, bem-vindo!")
        redisTemplate.opsForValue().set("template:" + name, content);
    }

    // Recupera um template salvo pelo nome
    public String getTemplate(String name) {
        return redisTemplate.opsForValue().get("template:" + name);
    }
}
