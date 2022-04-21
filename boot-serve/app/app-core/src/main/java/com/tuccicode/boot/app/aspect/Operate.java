package com.tuccicode.boot.app.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记录操作日志
 * @see OperateAspect
 * @author tucci.lee
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operate {

    /** 日志描述 */
    String value() default "";

    /** 是否记录参数 */
    boolean recordParams() default true;
}
