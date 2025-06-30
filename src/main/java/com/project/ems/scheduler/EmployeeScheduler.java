package com.project.ems.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.ems.dto.EmployeeDto;
import com.project.ems.service.EmployeeService;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Scope;
import co.elastic.apm.api.Transaction;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmployeeScheduler {
	@Autowired
	private EmployeeService employeeService;

	@Scheduled(cron="0 * * * * *")
	public void printEmployees()
	{
		Transaction transaction = ElasticApm.startTransaction();
		try (Scope scope = transaction.activate()) 
		{
            transaction.setName("Scheduled - printEmployees");
            transaction.setType("scheduled");
            
    		List<EmployeeDto> list = employeeService.getAllEmployees();
    		
    		for(EmployeeDto dto:list)
    		{
    			System.out.println("Name : "+dto.getFirstName());
    		}
		
        } catch (Exception e) {
            transaction.captureException(e);
            log.error("Exception in printEmployees>>",e);
        } finally {
            transaction.end();
        }
	}
	
}
