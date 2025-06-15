package com.project.ems.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.Profiles;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DBConfigInitializer implements BeanPostProcessor, InitializingBean, EnvironmentAware {

	private JdbcTemplate jdbcTemplate;
	private ConfigurableEnvironment environment;

	private final String propertySourceName = "dbProp";

	public DBConfigInitializer(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (environment != null && environment.acceptsProfiles(Profiles.of("ci"))) {
			System.out.println("Skipping DBConfigInitializer in 'ci' profile");
			return;
		}

		if (null != environment) {
			Map<String, Object> systemConfigMap = new HashMap<>();
			String sql = "SELECT param, value from config_values";
			List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : maps) {
				String param = String.valueOf(map.get("param"));
				Object value = map.get("value");
				systemConfigMap.put(param, value);
				System.out.println(String.format("param=%s, value=%s ", param, value));
			}
			environment.getPropertySources().addFirst(new MapPropertySource(propertySourceName, systemConfigMap));
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		if (environment instanceof ConfigurableEnvironment) {
			this.environment = (ConfigurableEnvironment) environment;
		}
	}
}