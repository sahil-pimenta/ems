package com.project.ems.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.service.RedisService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class RedisServiceImpl implements RedisService 
{
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public <T> T get(String key, Class<T> entityClass)
	{
		try 
		{
			Object val = redisTemplate.opsForValue().get(key);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(val.toString(), entityClass);
		} 
		catch (Exception e) {
			log.error("Exception in get(key, Class) value from Redis cache>>",e);
			return null;
		}
	}
	
	public Object get(String key)
	{
		try 
		{
			return redisTemplate.opsForValue().get(key);
		} 
		catch (Exception e) {
			log.error("Exception in get(key) value from Redis cache>>",e);
			return null;
		}
	}
	
	public void set(String key, Object val)
	{
		try 
		{
			redisTemplate.opsForValue().set(key, val);
		} 
		catch (Exception e) {
			log.error("Exception in set value from Redis cache>>",e);
		}
	}
	
	public void setWithExp(String key, Object val, Long ttl)
	{
		try 
		{
			if(ttl!=null)
			{
				redisTemplate.opsForValue().set(key, val, ttl, TimeUnit.SECONDS);
			}
			else
			{
				redisTemplate.opsForValue().set(key, val, -1);
			}
		} 
		catch (Exception e) {
			log.error("Exception in setWithExp value from Redis cache>>",e);
		}
	}
	
	public void delete(String key)
	{
		try 
		{
			redisTemplate.delete(key);
		} 
		catch (Exception e) {
			log.error("Exception in delete value from Redis cache>>",e);
		}
	}
}
