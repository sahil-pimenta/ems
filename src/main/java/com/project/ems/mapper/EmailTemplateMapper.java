package com.project.ems.mapper;

import com.project.ems.dto.EmailTemplateDto;
import com.project.ems.entity.EmailTemplate;

public class EmailTemplateMapper {
	
	public static EmailTemplateDto mapToEmailTemplateDto(EmailTemplate emailTemplate)
	{
		return new EmailTemplateDto(
				emailTemplate.getId(),
				emailTemplate.getTemplateName(),
				emailTemplate.getSubject(),
				emailTemplate.getFromProfile(),
				emailTemplate.getHasAttachment(),
				emailTemplate.getIsActive());
	}
	
	
	public static EmailTemplate mapToEmailTemplate(EmailTemplateDto emailTemplateDto)
	{
		return new EmailTemplate(
				emailTemplateDto.getId(),
				emailTemplateDto.getTemplateName(),
				emailTemplateDto.getSubject(),
				emailTemplateDto.getFromProfile(),
				emailTemplateDto.getHasAttachment(),
				emailTemplateDto.getIsActive());
	}
}
