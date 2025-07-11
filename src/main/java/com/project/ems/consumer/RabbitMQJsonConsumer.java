package com.project.ems.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ems.dto.MailDetailsDto;
import com.project.ems.dto.UsersDto;
import com.project.ems.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitMQJsonConsumer {

	@Autowired
	private EmailService emailService;
	
	@RabbitListener(queues = {"${rabbitmq.json.queue.name}"})
	public void consumeJsonMessage(UsersDto usersDto)
	{
		System.out.println("Received json msg>>"+usersDto.toString());
		log.debug("Received json msg>>"+usersDto.toString());
	}
	
	@RabbitListener(queues = {"${rabbitmq.json.queue.name}"})
	public void getEmailMessage(MailDetailsDto mailDetailsDto)
	{
		try {
			int cc_len=0;
			int bcc_len=0;
			
			if(mailDetailsDto.getCc()!=null)
			{
				cc_len=mailDetailsDto.getCc().size();
			}
			
			if(mailDetailsDto.getBcc()!=null)
			{
				bcc_len=mailDetailsDto.getBcc().size();
			}

			String cc[]=new String[cc_len];
			String bcc[]=new String[bcc_len];
			
			if(cc_len > 0)
			{
				for(int i=0;i<cc_len;i++)
				{
					cc[i]=mailDetailsDto.getCc().get(i);
				}
			}
			
			if(bcc_len > 0)
			{
				for(int i=0;i<bcc_len;i++)
				{
					bcc[i]=mailDetailsDto.getBcc().get(i);
				}
			}
			
			emailService.sendEmail("id1", mailDetailsDto.getTo(), cc, bcc, "Hi this is a test email | SSSR", "Hi, I'm sending this test mail to check my emailservice. How are you?", null);
		}
		catch(Exception e)
		{
			log.error("Exception in sending mail>>",e);
		}
	}
}
