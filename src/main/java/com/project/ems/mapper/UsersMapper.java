package com.project.ems.mapper;

import com.project.ems.dto.UsersDto;
import com.project.ems.entity.Users;

public class UsersMapper {
	
	public static UsersDto mapToUsersDto(Users users)
	{
		return new UsersDto(
				users.getId(),
				users.getUsername(),
				users.getPassword(),
				users.getRole(),
				users.getCreatedDate(),
				users.getLastUpdatedDate());
	}
	
	
	public static Users mapToUsers(UsersDto usersDto)
	{
		return new Users(
				usersDto.getId(),
				usersDto.getUsername(),
				usersDto.getPassword(),
				usersDto.getRole(),
				usersDto.getCreatedDate(),
				usersDto.getLastUpdatedDate());
	}
}
