package com.orrin.sca.component.redis.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 缓存管理（注解用）
 * @author orrin.zhang on 2017/8/3.
 * 使用时注解：@EnableCaching(mode = AdviceMode.ASPECTJ),@Configuration
 */

public class RedisCacheConfig extends CachingConfigurerSupport {
	@Autowired
	private RedisTemplate redisTemplate;

	@Bean
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
		redisCacheManager.setDefaultExpiration(300);
		return redisCacheManager;
	}
}
