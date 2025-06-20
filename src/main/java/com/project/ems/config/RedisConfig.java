package com.project.ems.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;
    
    @Value("${spring.zipkin.base-url}")
    private String zipkinUrl;
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        log.error(">>> Creating LettuceConnectionFactory with host: " + redisHost + ", port: " + redisPort);
        log.error("ZIPKIN URL>>"+zipkinUrl);
        return new LettuceConnectionFactory(redisHost, redisPort);
    }
    
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConFactory)
	{	    
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return redisTemplate;
	}
}
