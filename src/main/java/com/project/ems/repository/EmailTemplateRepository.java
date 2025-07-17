package com.project.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ems.entity.EmailTemplate;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long>
{
	EmailTemplate findByTemplateName(String templateName);
}
