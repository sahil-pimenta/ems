package com.project.ems.service;

import java.util.List;

import com.project.ems.dto.EmployeeDto;

public interface EmployeeService 
{
	public EmployeeDto createEmployee(EmployeeDto employeeDto);
	
	public EmployeeDto getEmployeeById(Long employeeId);
	
	public List<EmployeeDto> getAllEmployees();
	
	public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto);
	
	public void deleteEmployee(Long employeeId);
}
