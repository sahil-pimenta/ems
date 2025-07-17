package com.project.ems.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.project.ems.service.PlaceholderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlaceholderServiceImpl implements PlaceholderService {

	@Override
	public String setPlaceholders(String orginalText, Map<String, String> placeholderMap) 
	{
		if(placeholderMap==null || placeholderMap.size()==0)return orginalText;
		
		try
		{
			String finalMailBody=orginalText;
			for(Map.Entry<String, String> entryMap : placeholderMap.entrySet())
			{
				finalMailBody=StringUtils.replace(finalMailBody, "[["+entryMap.getKey()+"]]", entryMap.getValue());
			}
			
			return finalMailBody;
		}
		catch(Exception e)
		{
			log.error("Exception in setPlaceholders>>",e);
			return orginalText;
		}
	}

}
