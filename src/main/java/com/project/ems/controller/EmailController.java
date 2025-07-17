package com.project.ems.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ems.dto.MailDetailsDto;
import com.project.ems.publisher.RabbitMQJsonProducer;
import com.project.ems.response.GenericErrorResponse;
import com.project.ems.utils.EmailValidator;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Tag(name="Email APIs", description = "Send mail to a user")
@RequestMapping("/api/email")
public class EmailController {
	
	@Autowired
	private RabbitMQJsonProducer rabbitMQJsonProducer;
	
	@PostMapping("/send-mail")
	public ResponseEntity<?> sendMail(@RequestBody MailDetailsDto mailDetailsDto)
	{
		if(mailDetailsDto==null
				|| mailDetailsDto.getTo()==null
				|| mailDetailsDto.getTo().size()==0
				|| StringUtils.isBlank(mailDetailsDto.getMailTemplate()))
		{
	        return ResponseEntity
	                .badRequest()
	                .body("Invalid request");
		}
		
		for(String toMail:mailDetailsDto.getTo())
		{
			if(!EmailValidator.isValidEmail(toMail))
			{
		        GenericErrorResponse error = new GenericErrorResponse("Invalid email address provided", "to");
		        return ResponseEntity
		                .badRequest()
		                .body(error);
			}
		}
		
		if(mailDetailsDto.getCc()!=null)
		{
			for(String ccMail:mailDetailsDto.getCc())
			{
				if(!EmailValidator.isValidEmail(ccMail))
				{
			        GenericErrorResponse error = new GenericErrorResponse("Invalid email address provided", "cc");
			        return ResponseEntity
			                .badRequest()
			                .body(error);
				}
			}
		}

		if(mailDetailsDto.getBcc()!=null)
		{
			for(String bccMail:mailDetailsDto.getBcc())
			{
				if(!EmailValidator.isValidEmail(bccMail))
				{
			        GenericErrorResponse error = new GenericErrorResponse("Invalid email address provided", "bcc");
			        return ResponseEntity
			                .badRequest()
			                .body(error);
				}
			}
		}
		
		rabbitMQJsonProducer.sendEmail(mailDetailsDto);
		return ResponseEntity.ok("Mail request in queue");
	}
}
