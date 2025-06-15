package com.project.ems.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
	private Long id;
	private String username;
	private String password;
	private String role;
	private Timestamp createdDate;
	private Timestamp lastUpdatedDate;
}
