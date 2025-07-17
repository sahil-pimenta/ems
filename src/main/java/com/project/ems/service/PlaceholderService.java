package com.project.ems.service;

import java.util.Map;

public interface PlaceholderService {

	public String setPlaceholders(String orginalText, Map<String, String> placeholderMap);
}
