package com.project.ems.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailDetailsDto {

	public String to;
	public List<String> cc;
	public List<String> bcc;
	public String mailType;
}
