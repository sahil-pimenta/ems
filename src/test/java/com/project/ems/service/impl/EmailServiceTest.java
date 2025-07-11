package com.project.ems.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.ems.service.EmailService;

@SpringBootTest
public class EmailServiceTest {

	@Autowired
	private EmailService emailService;
	
	@Test
	public void testEmail()
	{
//		try {
//			emailService.sendEmail("id1", "vekaf63889@fenexy.com", "Hi this is a test email | SSSR", "Hi, I'm sending this test mail to check my emailservice. How are you?", null);
//		}
//		catch(Exception e)
//		{
//			System.out.println(e);
//		}
	}
}
