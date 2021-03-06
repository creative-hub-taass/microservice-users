package com.creativehub.backend.services.impl;

import com.creativehub.backend.services.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProducerServiceImpl implements ProducerService {
	/*@Value("${spring.rabbitmq.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.routingkey}")
	private String routingKey;*/
	@Autowired
	private FanoutExchange exchange;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void sendMessage(UUID id) {
		rabbitTemplate.convertAndSend(exchange.getName(), "", id);
		System.out.println("Mandato il messaggio" + id + "correttamente");
	}

}
