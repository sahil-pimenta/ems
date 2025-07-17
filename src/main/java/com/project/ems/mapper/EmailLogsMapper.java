package com.project.ems.mapper;

import com.project.ems.dto.EmailLogsDto;
import com.project.ems.entity.EmailLogs;

public class EmailLogsMapper {
	
	public static EmailLogsDto mapToEmailLogsDto(EmailLogs emailLogs)
	{
		return new EmailLogsDto(
				emailLogs.getId(),
				emailLogs.getTemplateName(),
				emailLogs.getCreatedTimestamp(),
				emailLogs.getLastUpdatedTimestamp(),
				emailLogs.getFromProfile(),
				emailLogs.getFromId(),
				emailLogs.getToId(),
				emailLogs.getPlaceholders(),
				emailLogs.getHasAttachment(),
				emailLogs.getAttachmentName(),
				emailLogs.getRemarks(),
				emailLogs.getStatus());
	}
	
	
	public static EmailLogs mapToEmailLogs(EmailLogsDto emailLogsDto)
	{
		return new EmailLogs(
				emailLogsDto.getId(),
				emailLogsDto.getTemplateName(),
				emailLogsDto.getCreatedTimestamp(),
				emailLogsDto.getLastUpdatedTimestamp(),
				emailLogsDto.getFromProfile(),
				emailLogsDto.getFromId(),
				emailLogsDto.getToId(),
				emailLogsDto.getPlaceholders(),
				emailLogsDto.getHasAttachment(),
				emailLogsDto.getAttachmentName(),
				emailLogsDto.getRemarks(),
				emailLogsDto.getStatus());
	}
}
