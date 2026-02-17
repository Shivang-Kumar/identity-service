package com.practice.microservices.identity_service.services;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisCacheClient {
	
	private StringRedisTemplate redisTemplate;
	
	public void set(String key,String value,int timeout,TimeUnit unit)
	{
		redisTemplate.opsForValue().set(key, value,timeout,unit);
	}

	public String get(String key)
	{
		return redisTemplate.opsForValue().get(key);
	}
	
	
}
