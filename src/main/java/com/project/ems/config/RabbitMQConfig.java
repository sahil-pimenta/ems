package com.project.ems.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;
	
	@Value("${rabbitmq.queue.name}")
	private String queueName;
	
	@Value("${rabbitmq.routing.key}")
	private String routingKey;
	
	@Value("${rabbitmq.json.queue.name}")
	private String jsonQueueName;
	
	@Value("${rabbitmq.json.routing.key}")
	private String jsonRoutingKey;
	
	@Value("${rabbitmq.email.queue.name}")
	private String emailQueueName;
	
	@Value("${rabbitmq.email.routing.key}")
	private String emailRoutingKey;
	
	//Spring bean for rabbitmq queue
	@Bean
	public Queue queue()
	{
		return new Queue(queueName);
	}
	
	@Bean
	public Queue jsonQueue()
	{
		return new Queue(jsonQueueName);
	}
	
	@Bean
	public Queue emailQueue()
	{
		return new Queue(emailQueueName);
	}
	
	//Spring bean for rabbitmq exchange
	@Bean
	public TopicExchange exchange()
	{
		return new TopicExchange(exchangeName);
	}
	
	//binding between queue and exchange using routing key
	@Bean
	public Binding binding()
	{
		return BindingBuilder
				.bind(queue())
				.to(exchange())
				.with(routingKey);
	}
	
	@Bean
	public Binding jsonBinding()
	{
		return BindingBuilder
				.bind(jsonQueue())
				.to(exchange())
				.with(jsonRoutingKey);
	}
	
	@Bean
	public Binding emailBinding()
	{
		return BindingBuilder
				.bind(emailQueue())
				.to(exchange())
				.with(emailRoutingKey);
	}
	
	@Bean
	public MessageConverter converter()
	{
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory)
	{
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
	
}
