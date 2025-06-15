package com.project.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.ems.api.response.WeatherStack;
import com.project.ems.service.ApiService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService{

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${weather.api.url}")
	private String weatherApiURL;
	
	@Value("${weather.api.key}")
	private String weatherApiKey;
	
	@Override
	public WeatherStack getCityWeather(String city) 
	{
		String apiURL = weatherApiURL;
		String apikey = weatherApiKey;
		
		String requestURL = apiURL.replace("{API_KEY}", apikey).replace("{CITY}", city);
		ResponseEntity<WeatherStack> response = restTemplate.exchange(requestURL, HttpMethod.GET, null, WeatherStack.class);
		WeatherStack respBody = response.getBody();
		return respBody;
	}

}
