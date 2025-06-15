package com.project.ems.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.ems.dto.ConfigValuesDto;
import com.project.ems.service.ConfigValuesService;

import jakarta.annotation.PostConstruct;

@Component
public class ConfigValuesCache {

	@Autowired
	private ConfigValuesService configValuesService;
	
	public Map<String, String> appCache = new HashMap<>();
	
	@PostConstruct
	public void init()
	{
		List<ConfigValuesDto> configValues = configValuesService.getAllConfigValues();
		for(ConfigValuesDto dto : configValues)
		{
			appCache.put(dto.getParam(), dto.getValue());
		}
	}
	
}
