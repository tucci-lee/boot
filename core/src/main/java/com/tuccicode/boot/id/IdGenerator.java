package com.tuccicode.boot.id;

/**
 * id生成器
 *
 * @author tucci.lee
 */
@FunctionalInterface
public interface IdGenerator {
    /**
     * 获取一个id
     *
     * @return id
     */
    long nextId();
}
