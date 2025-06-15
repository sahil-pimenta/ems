package com.project.ems.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.ems.dto.UsersDto;
import com.project.ems.entity.Users;
import com.project.ems.mapper.UsersMapper;
import com.project.ems.repository.UsersRepository;
import com.project.ems.service.UsersService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService, UserDetailsService
{

	private UsersRepository usersRepository;
	
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	public UsersDto createEmployee(UsersDto usersDto) {
		Users users = UsersMapper.mapToUsers(usersDto);
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		Users savedUser = usersRepository.save(users);
		return UsersMapper.mapToUsersDto(savedUser);
	}

	@Override
	public UsersDto getEmployeeByUsername(String username) throws UsernameNotFoundException{
		Users users = usersRepository.findByUsername(username);
		
		if(users==null)
		{
			throw new UsernameNotFoundException("User with username: " + username + " not found");
		}
		return UsersMapper.mapToUsersDto(users);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = usersRepository.findByUsername(username);
		if(users==null)
		{
			throw new UsernameNotFoundException("User with username: " + username + " not found");
		}
		
		UserDetails userDetails = User.builder()
				.username(users.getUsername())
				.password(users.getPassword())
				.roles(users.getRole())
				.build();
		
		return userDetails;
	}

}
