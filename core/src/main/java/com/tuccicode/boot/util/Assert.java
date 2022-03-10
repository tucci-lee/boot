package com.tuccicode.boot.util;

import com.tuccicode.boot.exception.ErrorCode;
import com.tuccicode.boot.exception.BizException;

import java.util.Collection;
import java.util.Map;

/**
 * 断言
 *
 * @author tucci.lee
 */
public abstract class Assert {

    public static void isNull(Object obj, int code, String msg) {
        if (obj != null) {
            throw new BizException(code, msg);
        }
    }

    public static void isNull(Object obj, ErrorCode errorCode) {
        isNull(obj, errorCode.getCode(), errorCode.getMessage());
    }

    public static void notNull(Object obj, int code, String message) {
        if (obj == null) {
            throw new BizException(code, message);
        }
    }

    public static void notNull(Object obj, ErrorCode errorCode) {
        notNull(obj, errorCode.getCode(), errorCode.getMessage());
    }

    public static void isTrue(boolean expression, int code, String message) {
        if (!expression) {
            throw new BizException(code, message);
        }
    }

    public static void isTrue(boolean expression, ErrorCode errorCode) {
        isTrue(expression, errorCode.getCode(), errorCode.getMessage());
    }

    public static void notEmpty(CharSequence obj, int code, String message) {
        if (obj == null || obj.length() == 0) {
            throw new BizException(code, message);
        }
    }

    public static void notEmpty(CharSequence obj, ErrorCode errorCode) {
        notEmpty(obj, errorCode.getCode(), errorCode.getMessage());
    }

    public static void notEmpty(Collection<?> collection, int code, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new BizException(code, message);
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorCode errorCode) {
        notEmpty(collection, errorCode.getCode(), errorCode.getMessage());
    }

    public static void notEmpty(Map<?, ?> map, int code, String message) {
        if (map == null || map.isEmpty()) {
            throw new BizException(code, message);
        }
    }

    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode) {
        notEmpty(map, errorCode.getCode(), errorCode.getMessage());
    }

    public static void notEmpty(Object[] array, int code, String message) {
        if (array == null || array.length == 0) {
            throw new BizException(code, message);
        }
    }

    public static void notEmpty(Object[] array, ErrorCode errorCode) {
        notEmpty(array, errorCode.getCode(), errorCode.getMessage());
    }
}
