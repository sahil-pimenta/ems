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
@Table(name="config_values")
public class ConfigValues {
	@Id
	private Long id;
	
	@Column(name="param", nullable = false, unique = true)
	private String param;
	
	@Column(name="value")
	private String value;
}
