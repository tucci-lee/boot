package com.tuccicode.boot.config;

import com.tuccicode.boot.cache.CacheOperate;
import com.tuccicode.boot.cache.RedisTemplateCacheOperate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheManagerConfig {

    private final RedisTemplate redisTemplate;

    public CacheManagerConfig(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public CacheOperate cacheOperate() {
        return new RedisTemplateCacheOperate(redisTemplate);
    }
}
