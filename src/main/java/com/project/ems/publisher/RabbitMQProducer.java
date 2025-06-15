package com.project.ems.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMQProducer 
{
	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.key}")
	private String routingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendMsg(String msg)
	{
		rabbitTemplate.convertAndSend(exchange, routingKey, msg);
	}
}
