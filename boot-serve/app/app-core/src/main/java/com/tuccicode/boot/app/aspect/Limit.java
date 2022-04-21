package com.tuccicode.boot.app.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制访问
 *
 * @author tucci.lee
 * @see LimitAspect
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limit {

    /**
     * 限流的key，只支持spel表达式
     *
     * @return String
     */
    String key() default "";

    /**
     * 访问次数
     *
     * @return int
     */
    int rate() default 10;

    /**
     * 访问间隔，单位s
     *
     * @return int
     */
    int rateInterval() default 60;
}
