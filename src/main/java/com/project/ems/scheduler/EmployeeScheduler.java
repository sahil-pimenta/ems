package com.project.ems.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.ems.dto.EmployeeDto;
import com.project.ems.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmployeeScheduler {
	@Autowired
	private EmployeeService employeeService;

	@Scheduled(cron="0 * * * * *")
	public void printEmployees()
	{
		List<EmployeeDto> list = employeeService.getAllEmployees();
		
		for(EmployeeDto dto:list)
		{
			System.out.println("Name : "+dto.getFirstName());
		}
	}
	
}
