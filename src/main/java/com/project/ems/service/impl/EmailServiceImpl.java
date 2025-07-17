package com.project.ems.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.project.ems.dto.EmailLogsDto;
import com.project.ems.dto.EmailTemplateDto;
import com.project.ems.entity.EmailLogs;
import com.project.ems.entity.EmailTemplate;
import com.project.ems.filter.NullPropertyFilter;
import com.project.ems.mapper.EmailLogsMapper;
import com.project.ems.mapper.EmailTemplateMapper;
import com.project.ems.repository.EmailLogsRepository;
import com.project.ems.repository.EmailTemplateRepository;
import com.project.ems.service.EmailService;
import com.project.ems.service.PlaceholderService;
import com.project.ems.utils.MailSenderFactory;


import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class EmailServiceImpl implements EmailService 
{
	@Autowired
	private MailSenderFactory mailSenderFactory;
	
	@Autowired
	private PlaceholderService placeholderService;
	
	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	
	@Autowired
	private EmailLogsRepository emailLogsRepository;
	
	public EmailTemplateDto getEmailTemplateDetails(String templateName)
	{
		EmailTemplate emailTemplate = emailTemplateRepository.findByTemplateName(templateName);
		if(emailTemplate==null)
		{
			return new EmailTemplateDto();
		}
		return EmailTemplateMapper.mapToEmailTemplateDto(emailTemplate);
	}
	
//	public EmailLogsDto saveEmailLogs(EmailLogsDto emailLogsDto)
//	{
//		EmailLogs emailLogs = EmailLogsMapper.mapToEmailLogs(emailLogsDto);
//		EmailLogs savedEmailLogs = emailLogsRepository.save(emailLogs);
//		return EmailLogsMapper.mapToEmailLogsDto(savedEmailLogs);
//	}
	
	public EmailLogsDto saveEmailLogs(EmailLogsDto emailLogsDto) 
	{
	    EmailLogs emailLogs;

	    if (emailLogsDto.getId() != null) {
	        //UPDATE: fetch existing and update only changed (non-null) fields
	        emailLogs = emailLogsRepository.findById(emailLogsDto.getId())
	                .orElseThrow(() -> new RuntimeException("Email log not found"));

	        BeanUtils.copyProperties(emailLogsDto, emailLogs, NullPropertyFilter.getNullPropertyNames(emailLogsDto));
	    } else {
	        //SAVE
	        emailLogs = EmailLogsMapper.mapToEmailLogs(emailLogsDto);
	    }

	    EmailLogs saved = emailLogsRepository.save(emailLogs);
	    return EmailLogsMapper.mapToEmailLogsDto(saved);
	}
	
	public String[] getToEmails(List<String> toEmailList)
	{
		int to_len=0;
		
		if(toEmailList!=null)
		{
			to_len=toEmailList.size();
		}
		
		if(to_len <= 0) {
			log.error("To emails not received>>", toEmailList.toString());
			return null;
		}

		String to[]=new String[to_len];
		
		if(to_len > 0)
		{
			for(int i=0;i<to_len;i++)
			{
				to[i]=toEmailList.get(i);
			}
		}
		return to;
	}
	
	public String[] getCcEmails(List<String> ccEmailList)
	{
		int cc_len=0;
		
		if(ccEmailList!=null)
		{
			cc_len=ccEmailList.size();
		}
		
		if(cc_len <= 0) {
			return null;
		}

		String cc[]=new String[cc_len];
		
		if(cc_len > 0)
		{
			for(int i=0;i<cc_len;i++)
			{
				cc[i]=ccEmailList.get(i);
			}
		}
		return cc;
	}
	
	public String[] getBccEmails(List<String> bccEmailList)
	{
		int bcc_len=0;
		
		if(bccEmailList!=null)
		{
			bcc_len=bccEmailList.size();
		}
		
		if(bcc_len <= 0) {
			return null;
		}

		String bcc[]=new String[bcc_len];
		
		if(bcc_len > 0)
		{
			for(int i=0;i<bcc_len;i++)
			{
				bcc[i]=bccEmailList.get(i);
			}
		}
		return bcc;
	}

	public String sendEmail(String templateName, String fromProfile, String[] to, String[] cc, String[] bcc, String subject, 
			boolean isPlaceholder, Map<String, String> placeholderMap, boolean hasAttachment, File attachment) throws Exception
	{
		if(to==null || to.length<=0)
		{
			log.error("To email not received for template>"+templateName);
			return "RECIPIENT_NOT_FOUND";
		}
		
		JavaMailSender mailSender = mailSenderFactory.getSender(fromProfile);

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true); // true = multipart
		
		String fromEmail = ((JavaMailSenderImpl) mailSender).getUsername();
		
		helper.setFrom(fromEmail);
		
		if((to!=null) && (to.length>0))
		{
			helper.setTo(to);
		}
		
		if((cc!=null) && (cc.length>0))
		{
			helper.setCc(cc);
		}
		
		if((bcc!=null) && (bcc.length>0))
		{
			helper.setBcc(bcc);
		}
		
		helper.setSubject(subject);
		
		String finalMailBody = getMailBody(templateName, isPlaceholder, placeholderMap);
		
		helper.setText(finalMailBody, true); // true = HTML

		if (attachment != null) {
			helper.addAttachment(attachment.getName(), attachment);
		}
		
		mailSender.send(message);
		
		return "SUCCESS";
	}
	
	private String getMailBody(String templateName, boolean isPlaceholder, Map<String, String> placeholderMap) throws Exception
	{
		String templatePath="templates/email/"; //this can be added in your config table if needed
	    ClassPathResource resource = new ClassPathResource(templatePath+templateName+".html");
	    byte[] bytes = resource.getInputStream().readAllBytes();
	    
	    if(isPlaceholder)
	    {
	    	if(placeholderMap==null || placeholderMap.size()==0)return new String(bytes, StandardCharsets.UTF_8);
	    	
	    	String htmlBody = new String(bytes, StandardCharsets.UTF_8);
	    	String finalMailBody = placeholderService.setPlaceholders(htmlBody, placeholderMap);
	    	return finalMailBody;
	    }
	    else
	    {
		    return new String(bytes, StandardCharsets.UTF_8);
	    }
	}
}
