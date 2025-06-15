package com.project.ems.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ems.dto.UsersDto;
import com.project.ems.service.UsersService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name="User APIs", description = "User creation and fetch")
@RequestMapping("/api/users")
public class UsersController {

	private UsersService usersService;
	
	@PostMapping("/add-user")
	public ResponseEntity<UsersDto> createUser(@RequestBody UsersDto usersDto)
	{
		UsersDto savedUsersDto = usersService.createEmployee(usersDto);
		return new ResponseEntity<UsersDto>(savedUsersDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-user")
	public ResponseEntity<UsersDto> getUserByUsername()
	{
		//Fetching username that was passed in Authentication header
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		try
		{
			UsersDto usersDto = usersService.getEmployeeByUsername(username);
			return new ResponseEntity<UsersDto>(usersDto, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<UsersDto>(HttpStatus.NOT_FOUND);
		}
	}
	
}
