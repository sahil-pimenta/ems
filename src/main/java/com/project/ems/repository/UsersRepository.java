package com.project.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ems.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long>
{
	Users findByUsername(String username);
}
