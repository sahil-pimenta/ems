package com.project.ems.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.project.ems.service.EmailService;
import com.project.ems.utils.MailSenderFactory;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;

@Service
@Data
public class EmailServiceImpl implements EmailService 
{
	@Autowired
	private MailSenderFactory mailSenderFactory;

	public void sendEmail(String fromProfile, String to, String[] cc, String[] bcc, String subject, String htmlBody, File attachment) throws MessagingException
	{
		JavaMailSender mailSender = mailSenderFactory.getSender(fromProfile);

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true); // true = multipart
		
		String fromEmail = ((JavaMailSenderImpl) mailSender).getUsername();
		
		helper.setFrom(fromEmail);
		helper.setTo(to);
		
		if((cc!=null) && (cc.length>0))
		{
			helper.setCc(cc);
		}
		
		if((bcc!=null) && (bcc.length>0))
		{
			helper.setBcc(bcc);
		}
		
		helper.setSubject(subject);
		helper.setText(htmlBody, true); // true = HTML

		if (attachment != null) {
			helper.addAttachment(attachment.getName(), attachment);
		}

		mailSender.send(message);
	}
}
