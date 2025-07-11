package com.project.ems.service;

import java.io.File;

import jakarta.mail.MessagingException;

public interface EmailService {

	public void sendEmail(String fromProfile, String to, String[] cc, String[] bcc, String subject, String htmlBody, File attachment) throws MessagingException;
}
