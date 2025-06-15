package com.project.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ems.entity.ConfigValues;

public interface ConfigValuesRepository extends JpaRepository<ConfigValues, Long> {

}
