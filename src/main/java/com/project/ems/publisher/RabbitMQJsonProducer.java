package com.project.ems.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.ems.dto.MailDetailsDto;
import com.project.ems.dto.UsersDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMQJsonProducer {
	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.json.routing.key}")
	private String jsonRoutingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	public void sendJsonMsg(UsersDto usersDto)
	{
		rabbitTemplate.convertAndSend(exchange, jsonRoutingKey, usersDto);
	}
	
	public void sendEmail(MailDetailsDto mailDetailsDto)
	{
		rabbitTemplate.convertAndSend(exchange, jsonRoutingKey, mailDetailsDto);
	}
}
