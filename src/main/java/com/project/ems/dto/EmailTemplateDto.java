package com.project.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateDto {
	
	private Long id;
	private String templateName;
	private String subject;
	private String fromProfile;
	private String hasAttachment;
	private String isActive;
}
