package com.project.ems.api.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherStack{
	
	@Getter
	@Setter
    private Current current;

	public class Current{
		@JsonProperty("temperature")
	    public int temperature;
		@JsonProperty("weather_descriptions")
	    public List<String> weather_descriptions;
		@JsonProperty("feelslike")
	    public int feelslike;
	}

}