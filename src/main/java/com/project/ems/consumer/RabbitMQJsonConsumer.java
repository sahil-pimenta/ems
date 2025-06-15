package com.project.ems.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.project.ems.dto.UsersDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitMQJsonConsumer {

	@RabbitListener(queues = {"${rabbitmq.json.queue.name}"})
	public void consumeJsonMessage(UsersDto usersDto)
	{
		System.out.println("Received json msg>>"+usersDto.toString());
		log.debug("Received json msg>>"+usersDto.toString());
	}
}
