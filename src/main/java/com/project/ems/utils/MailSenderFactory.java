package com.project.ems.utils;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailSenderFactory {
	
	@Value("${id1.mail.senders.host}")
	private String id1_mail_host;
	
	@Value("${id1.mail.senders.port}")
	private String id1_mail_port;
	
	@Value("${id1.mail.senders.username}")
	private String id1_mail_username;
	
	@Value("${id1.mail.senders.password}")
	private String id1_mail_pass;
	
	@Value("${id1.mail.senders.protocol}")
	private String id1_mail_protocol;
	
	@Value("${id1.mail.senders.properties.mail.smtp.auth}")
	private String id1_mail_smtp_auth;
	
	@Value("${id1.mail.senders.properties.mail.smtp.starttls.enable}")
	private String id1_mail_smtp_starttls_enable;

    public JavaMailSender getSender(String fromProfile) {
    	
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
    	if(StringUtils.equalsIgnoreCase(fromProfile, "id1"))
    	{
            mailSender.setHost(id1_mail_host);
            mailSender.setPort(Integer.parseInt(id1_mail_port));
            mailSender.setUsername(id1_mail_username);
            mailSender.setPassword(id1_mail_pass);
            mailSender.setProtocol(id1_mail_protocol);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", id1_mail_smtp_auth);
            props.put("mail.smtp.starttls.enable", id1_mail_smtp_starttls_enable);
    	}
    	else
    	{
            mailSender.setHost(id1_mail_host);
            mailSender.setPort(Integer.parseInt(id1_mail_port));
            mailSender.setUsername(id1_mail_username);
            mailSender.setPassword(id1_mail_pass);
            mailSender.setProtocol(id1_mail_protocol);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", id1_mail_smtp_auth);
            props.put("mail.smtp.starttls.enable", id1_mail_smtp_starttls_enable);
    	}
        return mailSender;
    }
    
}
