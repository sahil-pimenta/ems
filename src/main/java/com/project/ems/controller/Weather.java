package com.project.ems.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ems.api.response.WeatherStack;
import com.project.ems.service.ApiService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Tag(name="Weather APIs", description = "Live weather stack APi")
@RequestMapping("/api/weather")
public class Weather {
	
	private ApiService apiService;
	
	@GetMapping("/{city}")
	public ResponseEntity<WeatherStack> getWeather(@PathVariable("city") String city)
	{
		return ResponseEntity.ok(apiService.getCityWeather(city));
	}
}
