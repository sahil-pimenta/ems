package com.project.ems.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ems.dto.ConfigValuesDto;
import com.project.ems.entity.ConfigValues;
import com.project.ems.mapper.ConfigValuesMapper;
import com.project.ems.repository.ConfigValuesRepository;
import com.project.ems.service.ConfigValuesService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ConfigValuesServiceImpl implements ConfigValuesService
{
	@Autowired
	private ConfigValuesRepository configValuesRepository;
	
	@Override
	public List<ConfigValuesDto> getAllConfigValues() {
		List<ConfigValues> configList = configValuesRepository.findAll();
		return configList.stream().map((config) -> ConfigValuesMapper.mapToConfigValuesDto(config))
				.collect(Collectors.toList());
	}
	
}
