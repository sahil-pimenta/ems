package com.project.ems.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.project.ems.dto.EmailLogsDto;
import com.project.ems.dto.EmailTemplateDto;

public interface EmailService {

	public EmailTemplateDto getEmailTemplateDetails(String templateName);
	
	public EmailLogsDto saveEmailLogs(EmailLogsDto emailLogsDto);
	
	public String[] getToEmails(List<String> toEmailList);
	
	public String[] getCcEmails(List<String> ccEmailList);
	
	public String[] getBccEmails(List<String> bccEmailList);
	
	public String sendEmail(String templateName, String fromProfile, String[] to, String[] cc, String[] bcc, String subject, 
			boolean isPlaceholder, Map<String, String> placeholderMap, boolean hasAttachment, File attachment) throws Exception;
}
