package com.tuccicode.boot.config;

import com.tuccicode.raccoon.cache.CacheOperate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    class RedisTemplateCacheOperate implements CacheOperate {

        private final RedisTemplate redisTemplate;

        public RedisTemplateCacheOperate(RedisTemplate redisTemplate) {
            this.redisTemplate = redisTemplate;
        }

        @Override
        public <T> void set(String key, T value) {
            redisTemplate.opsForValue().set(key, value);
        }

        @Override
        public <T> void set(String key, T value, long timeout, TimeUnit unit) {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        }

        @Override
        public <T> T get(String key) {
            return (T) redisTemplate.opsForValue().get(key);
        }

        @Override
        public void delete(String key) {
            redisTemplate.delete(key);
        }

        @Override
        public void clean(String pattern) {
            Set<String> keys = redisTemplate.keys(pattern);
            if (!CollectionUtils.isEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        }
    }
}
