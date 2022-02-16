package com.tuccicode.boot.util;

import com.tuccicode.boot.exception.ErrorMessage;
import com.tuccicode.boot.exception.ServiceException;

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
            throw new ServiceException(code, msg);
        }
    }

    public static void isNull(Object obj, ErrorMessage errorMessage) {
        isNull(obj, errorMessage.getCode(), errorMessage.getMessage());
    }

    public static void notNull(Object obj, int code, String message) {
        if (obj == null) {
            throw new ServiceException(code, message);
        }
    }

    public static void notNull(Object obj, ErrorMessage errorMessage) {
        notNull(obj, errorMessage.getCode(), errorMessage.getMessage());
    }

    public static void isTrue(boolean expression, int code, String message) {
        if (!expression) {
            throw new ServiceException(code, message);
        }
    }

    public static void isTrue(boolean expression, ErrorMessage errorMessage) {
        isTrue(expression, errorMessage.getCode(), errorMessage.getMessage());
    }

    public static void notEmpty(CharSequence obj, int code, String message) {
        if (obj == null || obj.length() == 0) {
            throw new ServiceException(code, message);
        }
    }

    public static void notEmpty(CharSequence obj, ErrorMessage errorMessage) {
        notEmpty(obj, errorMessage.getCode(), errorMessage.getMessage());
    }

    public static void notEmpty(Collection<?> collection, int code, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new ServiceException(code, message);
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorMessage errorMessage) {
        notEmpty(collection, errorMessage.getCode(), errorMessage.getMessage());
    }

    public static void notEmpty(Map<?, ?> map, int code, String message) {
        if (map == null || map.isEmpty()) {
            throw new ServiceException(code, message);
        }
    }

    public static void notEmpty(Map<?, ?> map, ErrorMessage errorMessage) {
        notEmpty(map, errorMessage.getCode(), errorMessage.getMessage());
    }

    public static void notEmpty(Object[] array, int code, String message) {
        if (array == null || array.length == 0) {
            throw new ServiceException(code, message);
        }
    }

    public static void notEmpty(Object[] array, ErrorMessage errorMessage) {
        notEmpty(array, errorMessage.getCode(), errorMessage.getMessage());
    }
}
