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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	@SequenceGenerator(
			name = "users_id_seq",
			sequenceName = "users_id_seq_pk",
			allocationSize = 1
	)
	private Long id;
	
	@Column(name="username", nullable = false, unique = true)
	private String username;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="role", nullable = false)
	private String role;
	
	@CreationTimestamp
	@Column(name="created_date")
	private Timestamp createdDate;
	
	@UpdateTimestamp
	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;
}
