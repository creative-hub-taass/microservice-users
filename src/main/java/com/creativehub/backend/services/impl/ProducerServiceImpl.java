package com.creativehub.backend.services.impl;

import com.creativehub.backend.services.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey;


    @Override
    public void sendMessage(UUID id) {
        rabbitTemplate.convertAndSend(exchange,routingKey,id);
        System.out.println("Mandato il messaggio correttamente");
    }
}
