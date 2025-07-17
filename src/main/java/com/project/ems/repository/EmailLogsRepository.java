package com.project.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ems.entity.EmailLogs;

public interface EmailLogsRepository extends JpaRepository<EmailLogs, Long>
{

}
