package com.tuccicode.boot.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author tucci.lee
 */
public interface CacheOperate {

    /**
     * 添加缓存，没有过期时间
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    <T> void set(String key, T value);

    /**
     * 添加缓存，有过期时间
     *
     * @param key     缓存key
     * @param value   缓存value
     * @param timeout 过期时间
     * @param unit    过期时间单位
     */
    <T> void set(String key, T value, long timeout, TimeUnit unit);

    /**
     * 获取缓存
     *
     * @param key 缓存key
     * @return 缓存value
     */
    <T> T get(String key);

    /**
     * 删除缓存
     *
     * @param key 缓存key
     */
    void delete(String key);

    /**
     * 清除匹配的缓存
     *
     * @param pattern 匹配格式
     */
    void clean(String pattern);
}
