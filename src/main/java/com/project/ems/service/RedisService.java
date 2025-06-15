package com.project.ems.service;

public interface RedisService {
	
	public <T> T get(String key, Class<T> entityClass);
	
	public Object get(String key);
	
	public void set(String key, Object val);
	
	public void setWithExp(String key, Object val, Long ttl);
	
	public void delete(String key);
}
