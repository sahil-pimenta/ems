package com.project.ems.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailLogsDto {
	
	private Long id;
	private String templateName;
	private Timestamp createdTimestamp;
	private Timestamp lastUpdatedTimestamp;
	private String fromProfile;
	private String fromId;
	private String toId;
	private String placeholders;
	private String hasAttachment;
	private String attachmentName;
	private String remarks;
	private String status;
	
}
