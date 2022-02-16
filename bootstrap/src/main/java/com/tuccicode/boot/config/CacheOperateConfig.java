package com.tuccicode.boot.config;

import com.tuccicode.boot.cache.CacheOperate;
import com.tuccicode.boot.cache.RedisTemplateCacheOperate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author tucci.lee
 */
@Configuration
public class CacheOperateConfig {

    private final RedisTemplate redisTemplate;

    public CacheOperateConfig(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public CacheOperate cacheOperate() {
        return new RedisTemplateCacheOperate(redisTemplate);
    }
}
