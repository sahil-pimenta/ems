package com.project.ems.consumer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ems.dto.EmailLogsDto;
import com.project.ems.dto.EmailTemplateDto;
import com.project.ems.dto.MailDetailsDto;
import com.project.ems.dto.UsersDto;
import com.project.ems.service.EmailService;
import com.project.ems.utils.MailSenderFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitMQJsonConsumer {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MailSenderFactory mailSenderFactory;
	
	@RabbitListener(queues = {"${rabbitmq.json.queue.name}"})
	public void consumeJsonMessage(UsersDto usersDto)
	{
		System.out.println("Received json msg>>"+usersDto.toString());
		log.debug("Received json msg>>"+usersDto.toString());
	}
	
	@RabbitListener(queues = {"${rabbitmq.email.queue.name}"})
	public void getEmailMessage(MailDetailsDto mailDetailsDto)
	{
		System.out.println("Received email msg>>"+mailDetailsDto.toString());
		log.debug("Received email msg>>"+mailDetailsDto.toString());
		
		EmailLogsDto emailLogsDto = new EmailLogsDto();
		
		emailLogsDto.setTemplateName(mailDetailsDto.getMailTemplate());
		emailLogsDto.setStatus("INITIATED");
		emailLogsDto = emailService.saveEmailLogs(emailLogsDto);
		
		try {
			
			if(mailDetailsDto.getTo()==null
					|| mailDetailsDto.getTo().size()<=0)
			{
				emailLogsDto.setRemarks("Mail recipient not found");
				emailLogsDto.setStatus("FAILED");
				emailLogsDto = emailService.saveEmailLogs(emailLogsDto);
				log.error("Mail recipient not found>>"+mailDetailsDto);
				return;
			}
			
			String[] to = emailService.getToEmails(mailDetailsDto.getTo());
			
			if(to==null || to.length<=0)
			{
				emailLogsDto.setRemarks("Mail recipient not found");
				emailLogsDto.setStatus("FAILED");
				emailLogsDto = emailService.saveEmailLogs(emailLogsDto);
				log.error("Mail recipient not found>>"+mailDetailsDto);
				return;
			}
			
			String[] cc = emailService.getCcEmails(mailDetailsDto.getCc());
			
			String[] bcc = emailService.getBccEmails(mailDetailsDto.getBcc());
			
			EmailTemplateDto emailTemplateDto = emailService.getEmailTemplateDetails(mailDetailsDto.getMailTemplate());
			
			if(emailTemplateDto==null 
					|| emailTemplateDto.getId()==null)
			{
				emailLogsDto.setStatus("FAILED");
				emailLogsDto.setRemarks("Email Template not found for: "+mailDetailsDto.getMailTemplate());
				emailLogsDto = emailService.saveEmailLogs(emailLogsDto);
				log.error("Email Template not found for: "+mailDetailsDto.getMailTemplate());
				return;
			}
			
			StringBuilder sb_to = new StringBuilder();
			for(String toMail:to)
			{
				sb_to.append(toMail).append(",");
			}
			
			if (sb_to.length() > 0 && sb_to.charAt(sb_to.length() - 1) == ',') {
				sb_to.deleteCharAt(sb_to.length() - 1);
			}
			
			emailLogsDto.setToId(sb_to.toString());
			emailLogsDto.setFromProfile(emailTemplateDto.getFromProfile());
			emailLogsDto.setFromId(mailSenderFactory.getFromEmail(emailTemplateDto.getFromProfile()));
			emailLogsDto.setHasAttachment(emailTemplateDto.getHasAttachment());
			
			boolean hasAttachment = false;
			
			if(StringUtils.equalsIgnoreCase(emailTemplateDto.getHasAttachment(), "Y"))
			{
				hasAttachment=true;
				
				//Code to create your attachment file
			}
			
			//Setting placeholders, add your logic here
			Map<String, String> placeholderMap = new HashMap<>();
			placeholderMap.put("customer_name", "Edward D'souza");
			
			if(placeholderMap.size()>0)
			{
				StringBuilder sb = new StringBuilder();
				for(Map.Entry<String,String> entry : placeholderMap.entrySet())
				{
					sb.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
				}
				emailLogsDto.setPlaceholders(sb.toString());
			}
			
			emailLogsDto.setStatus("IN_PROCESS");
			emailLogsDto = emailService.saveEmailLogs(emailLogsDto);
			
			String emailStatus = emailService.sendEmail(emailTemplateDto.getTemplateName(), emailTemplateDto.getFromProfile(), to, cc, bcc, emailTemplateDto.getSubject(), true, placeholderMap , hasAttachment, null);
			
			if(!StringUtils.equalsIgnoreCase(emailStatus, "SUCCESS"))
			{
				emailLogsDto.setStatus("FAILED");
				emailLogsDto.setRemarks(emailStatus);
			}
			
			emailLogsDto.setStatus(emailStatus);
			emailLogsDto = emailService.saveEmailLogs(emailLogsDto);
		}
		catch(Exception e)
		{
			emailLogsDto.setStatus("FAILED");
			emailLogsDto.setRemarks("Exception in sending mail>>"+e.getMessage());
			emailLogsDto = emailService.saveEmailLogs(emailLogsDto);
			log.error("Exception in sending mail>>",e);
		}
	}
}
