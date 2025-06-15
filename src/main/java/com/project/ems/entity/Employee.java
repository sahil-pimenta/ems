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

/*Entity classes are used for class and it's fields to a DB table & columns*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employees")
public class Employee {
	/*@Table(name="employees") : maps this entity class with a DB table*/
	/*@Id : marks a field as the primary key*/
	/*@GeneratedValue(strategy=GenerationType.IDENTITY) : enables automatic ID generation*/
	/*@Column : maps a field with table column*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_id_seq")
	@SequenceGenerator(
			name = "employees_id_seq",
			sequenceName = "employees_id_seq_pk",
			allocationSize = 1
	)
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email_id", nullable = false, unique = true)
	private String email;
	
	@CreationTimestamp
	@Column(name="created_date")
	private Timestamp createdDate;
	
	@UpdateTimestamp
	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;
}
