package com.project.ems.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ems.dto.EmployeeDto;
import com.project.ems.dto.MailDetailsDto;
import com.project.ems.dto.UsersDto;
import com.project.ems.publisher.RabbitMQJsonProducer;
import com.project.ems.publisher.RabbitMQProducer;
import com.project.ems.service.EmailService;
import com.project.ems.service.EmployeeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Tag(name="Employee APIs", description = "CRUD operations")
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RabbitMQProducer rabbitMQProducer;
	
	@Autowired
	private RabbitMQJsonProducer rabbitMQJsonProducer;
	
	@Autowired
	private EmailService emailService;
	
	//Add Employee REST API
	@PostMapping("/add-employee")
	public ResponseEntity<EmployeeDto> createEmpolyee(@RequestBody EmployeeDto employeeDto)
	{
		EmployeeDto savedEmployeeDto = employeeService.createEmployee(employeeDto);
		
		rabbitMQJsonProducer.sendEmail(MailDetailsDto.builder()
				.to(savedEmployeeDto.getEmail())
				.cc(Arrays.asList("sahil.pimenta@softwareworkshop.net"))
				.mailType("WELCOME_MAIL")
				.build());
		
		return new ResponseEntity<EmployeeDto>(savedEmployeeDto, HttpStatus.CREATED);
	}
	
	//Get Employee REST API
	@GetMapping("/get-employee/{id}")
	public ResponseEntity<EmployeeDto> getEmpolyeeById(@PathVariable("id") Long employeeId)
	{
		EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
		return new ResponseEntity<EmployeeDto>(employeeDto, HttpStatus.OK);
	}
	
	//Get All Employees REST API
	@GetMapping("/get-employee")
	public ResponseEntity<List<EmployeeDto>> getAllEmpolyees()
	{
		List<EmployeeDto> empList = employeeService.getAllEmployees();
		return new ResponseEntity<List<EmployeeDto>>(empList, HttpStatus.OK);
	}
	
	//Update Employee By ID REST API
	@PutMapping("/update-employee/{id}")
	public ResponseEntity<EmployeeDto> updateEmpolyee(@PathVariable("id") Long empId, @RequestBody EmployeeDto employeeDto)
	{
		EmployeeDto empDto = employeeService.updateEmployee(empId, employeeDto);
		return ResponseEntity.ok(empDto); //another way of returning ResponseEntity
	}
	
	//Delete Employee By ID REST API
	@DeleteMapping("/delete-employee/{id}")
	public ResponseEntity<String> updateEmpolyee(@PathVariable("id") Long empId)
	{
		employeeService.deleteEmployee(empId);
		return ResponseEntity.ok("Employee deleted successfully");
	}
	
	//Send Msg using RabbitMQ
	@PostMapping("/publish-msg")
	public ResponseEntity<String> sendMsg(@RequestParam("msg") String msg)
	{
		rabbitMQProducer.sendMsg(msg);
		return ResponseEntity.ok("Message sent to RabbitMQ successfully");
	}
	
	//Send JSON Msg using RabbitMQ
	@PostMapping("/publish-json-msg")
	public ResponseEntity<String> sendJsonMsg(@RequestBody UsersDto usersDto)
	{
		rabbitMQJsonProducer.sendJsonMsg(usersDto);
		return ResponseEntity.ok("JSON Message sent to RabbitMQ successfully");
	}
}
