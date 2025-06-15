package com.project.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ems.dto.UsersDto;
import com.project.ems.service.UsersService;
import com.project.ems.service.impl.UsersServiceImpl;
import com.project.ems.utils.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Tag(name="Public APIs", description = "Health check, JWT creation(login) & User creation")
@RequestMapping("/public")
public class PublicController {
	
	private UsersService usersService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsersServiceImpl usersServiceImpl;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Value("${weather.api.key}")
	private String api_key;
	
	@GetMapping("/health-check")
	public String checkHealth()
	{
		return "OK"+" "+api_key;
	}
	
	//For JWT, new user will be created
	@PostMapping("/signup")
	public ResponseEntity<UsersDto> signup(@RequestBody UsersDto usersDto)
	{
		UsersDto savedUsersDto = usersService.createEmployee(usersDto);
		return new ResponseEntity<UsersDto>(savedUsersDto, HttpStatus.CREATED);
	}
	
	//For JWT, user will login with valid creds and will be given a JWT token, this token will now be passed in all further requests
	@PostMapping("/login")
	@Operation(summary = "Login a user and create return a JWT, this will now be passed in all other APIs")
	public ResponseEntity<String> login(@RequestBody UsersDto usersDto)
	{
		try
		{
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(usersDto.getUsername(), usersDto.getPassword()));
			UserDetails userDetails = usersServiceImpl.loadUserByUsername(usersDto.getUsername());
			String jwt = jwtUtil.generateToken(userDetails.getUsername()); //JWT Token generated here
			return new ResponseEntity<String>(jwt, HttpStatus.OK);
		}
		catch(Exception e)
		{
			log.error("Exception occured in login while creating auth token>>",e);
			return new ResponseEntity<String>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
		}
	}
}
