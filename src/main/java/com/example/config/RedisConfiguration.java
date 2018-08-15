package com.example.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/** 
* @ClassName: RedisConfiguration 
* @Description: Redis Config
* @author mengfanzhu
* @date 2017年2月21日 下午2:03:26 
*/

@Configuration
@EnableCaching//开启注解
public class RedisConfiguration {
	
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(connectionFactory);
		//Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new
		//		Jackson2JsonRedisSerializer(Object.class);
		
		//ObjectMapper om = new ObjectMapper();
	//	om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		//om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		// jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setKeySerializer(new StringRedisSerializer());
		//template.setValueSerializer(jackson2JsonRedisSerializer);
		//template.afterPropertiesSet();
		return template;
	}
	@Bean
	public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// cacheManager.setCacheNames(Arrays.asList("users", "emptyUsers"));
		cacheManager.setUsePrefix(true);
		// Number of seconds before expiration. Defaults to unlimited (0)
		cacheManager.setDefaultExpiration(1800L);
		return cacheManager;
	}
	@Bean
	public KeyGenerator accountKeyGenerator() {
		return new KeyGenerator(){
			@Override
			public Object generate(Object target, Method method, Object... params) {
				//first parameter is caching object
				//second paramter is the name of the method, we like the caching key has nothing to do with method name
				//third parameter is the list of parameters in the method being called
				return target.getClass().toString() + "accountId:" + params[0].toString();
			}
		};
	}

}
