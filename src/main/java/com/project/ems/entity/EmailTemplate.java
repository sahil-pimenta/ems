package com.project.ems.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="email_template")
public class EmailTemplate {
	
	@Id
	private Long id;
	
	@Column(name="template_name", nullable = false, unique = true)
	private String templateName;
	
	@Column(name="subject")
	private String subject;
	
	@Column(name="from_profile")
	private String fromProfile;
	
	@Column(name="has_attachment")
	private String hasAttachment;
	
	@Column(name="is_active")
	private String isActive;
}
