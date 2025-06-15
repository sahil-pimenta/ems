package com.project.ems.mapper;

import com.project.ems.dto.EmployeeDto;
import com.project.ems.entity.Employee;

/*Mapper class maps every entity class with its corresponding DTO class & vice-versa*/
public class EmployeeMapper {

	public static EmployeeDto mapToEmployeeDto(Employee employee)
	{
		return new EmployeeDto(
				employee.getId(),
				employee.getFirstName(),
				employee.getLastName(),
				employee.getEmail(),
				employee.getCreatedDate(),
				employee.getLastUpdatedDate());
	}
	
	
	public static Employee mapToEmployee(EmployeeDto employeeDto)
	{
		return new Employee(
				employeeDto.getId(),
				employeeDto.getFirstName(),
				employeeDto.getLastName(),
				employeeDto.getEmail(),
				employeeDto.getCreatedDate(),
				employeeDto.getLastUpdatedDate());
	}
}
