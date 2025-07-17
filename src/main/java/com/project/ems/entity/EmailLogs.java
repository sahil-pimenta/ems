package com.project.ems.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_logs")
public class EmailLogs {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_logs_id_seq")
	@SequenceGenerator(
			name = "email_logs_id_seq",
			sequenceName = "email_logs_id_seq",
			allocationSize = 1
	)
	private Long id;
	
	@Column(name="template_name")
	private String templateName;
	
	@CreationTimestamp
	@Column(name="created_timestamp")
	private Timestamp createdTimestamp;
	
	@UpdateTimestamp
	@Column(name="last_updated_timestamp")
	private Timestamp lastUpdatedTimestamp;
	
	@Column(name="from_profile")
	private String fromProfile;
	
	@Column(name="from_id")
	private String fromId;
	
	@Column(name="to_id")
	private String toId;
	
	@Column(name="placeholders")
	private String placeholders;
	
	@Column(name="has_attachment")
	private String hasAttachment;
	
	@Column(name="attachment_name")
	private String attachmentName;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="status")
	private String status;
	
}
