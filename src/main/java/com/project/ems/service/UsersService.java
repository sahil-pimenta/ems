package com.project.ems.service;

import com.project.ems.dto.UsersDto;

public interface UsersService {
	
	public UsersDto createEmployee(UsersDto usersDto);
	
	public UsersDto getEmployeeByUsername(String username);
}
