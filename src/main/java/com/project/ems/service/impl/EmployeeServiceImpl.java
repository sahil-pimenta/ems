package com.project.ems.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.project.ems.dto.EmployeeDto;
import com.project.ems.entity.Employee;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mapper.EmployeeMapper;
import com.project.ems.repository.EmployeeRepository;
import com.project.ems.service.EmployeeService;
import com.project.ems.service.RedisService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService
{
	//private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class); //you can instead just use lombok annotation @Slf4j
	
	private EmployeeRepository employeeRepository;
	
	private RedisService redisService;
	
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) 
	{
		Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeMapper.mapToEmployeeDto(savedEmployee);
	}

	@Override
	public EmployeeDto getEmployeeById(Long employeeId) {
		log.debug("Fetching data for Emp Id: {}", employeeId);
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID: " + employeeId + " not found"));
		
		log.debug("Found data for Emp Id: {} named: {}", employeeId, employee.getFirstName());
		
		return EmployeeMapper.mapToEmployeeDto(employee);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
		log.trace("TRACE logger prints this!!!!!!!!!");
		log.debug("DEBUG logger prints this!!!!!!!!!");
		log.info("INFO logger prints this!!!!!!!!!");
		log.warn("WARN logger prints this!!!!!!!!!");
		log.error("ERROR logger prints this!!!!!!!!!");
		
		if(redisService.get("allEmpsList")!=null)
		{
			//Fetching from Redis cache
			log.debug("getAllEmployees()|| Fetching from Cache");
			List<EmployeeDto> empListOp = (List<EmployeeDto>) redisService.get("allEmpsList");
			
			if(!empListOp.isEmpty())
			{
				return empListOp;
			}
			else
			{
				return new ArrayList<EmployeeDto>();
			}
		}
		else
		{
			//Fetching from DB
			log.debug("getAllEmployees()|| Fetching from DB");
			List<Employee> empList = employeeRepository.findAll();
			
			if(!empList.isEmpty())
			{
				//Setting in Redis cache
				List<EmployeeDto> empListOp = empList.stream()
						.map((employee) -> EmployeeMapper.mapToEmployeeDto(employee)).collect(Collectors.toList());
				log.debug("getAllEmployees()|| Setting in Redis cache");
				redisService.set("allEmpsList", empListOp);
				return empListOp;
			}
			else
			{
				return new ArrayList<EmployeeDto>();
			}
		}
	}

	@Override
	public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updateEmployeeDto) {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID: " + employeeId + " not found"));
		
		employee.setFirstName(updateEmployeeDto.getFirstName());
		employee.setLastName(updateEmployeeDto.getLastName());
		employee.setEmail(updateEmployeeDto.getEmail());
		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeMapper.mapToEmployeeDto(savedEmployee);
	}

	@Override
	public void deleteEmployee(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID: " + employeeId + " not found"));
		
		employeeRepository.deleteById(employeeId);
	}
}
