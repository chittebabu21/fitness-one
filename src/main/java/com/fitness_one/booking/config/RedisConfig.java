/**
 * 
 */
package com.fitness_one.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * author: chittebabu
 */

@Configuration
public class RedisConfig {
	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}
	
	@Bean
	public RedisTemplate<String, Integer> redisTemplate(LettuceConnectionFactory connectionFactory) {
		RedisTemplate<String, Integer> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		
		return template;
	}
}
