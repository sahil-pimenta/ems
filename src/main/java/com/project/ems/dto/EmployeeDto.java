package com.project.ems.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*DTO class is used to transfer data between client & server (basically JSON request & response fields structure)
 * this class needs to be mapped with it's corresponding Entity class, for doing that we use a Mapper class (e.g. EmployeeMapper)*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Timestamp createdDate;
	private Timestamp lastUpdatedDate;
}
