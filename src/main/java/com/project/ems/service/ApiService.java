package com.project.ems.service;

import com.project.ems.api.response.WeatherStack;

public interface ApiService {

	public WeatherStack getCityWeather(String city);
	
}
