package com.project.ems.mapper;

import com.project.ems.dto.ConfigValuesDto;
import com.project.ems.entity.ConfigValues;

public class ConfigValuesMapper {
	public static ConfigValuesDto mapToConfigValuesDto(ConfigValues configValues)
	{
		return new ConfigValuesDto(
				configValues.getId(),
				configValues.getParam(),
				configValues.getValue());
	}
	
	
	public static ConfigValues mapToConfigValues(ConfigValuesDto configValuesDto)
	{
		return new ConfigValues(
				configValuesDto.getId(),
				configValuesDto.getParam(),
				configValuesDto.getValue());
	}
}
