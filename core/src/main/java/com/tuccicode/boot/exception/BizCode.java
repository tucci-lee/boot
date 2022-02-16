package com.tuccicode.boot.exception;

/**
 * @author tucci.lee
 */
public enum BizCode implements ErrorMessage {
    /**
     * <p>
     * -1 服务器错误
     */
    SERVER_ERROR(-1, "服务器错误"),

    /**
     * 102xx 请求错误
     * <p>
     * 10200 参数错误
     * 10201 请求方法不支持
     * 10202 不支持的媒体类型
     * 10203 参数类型错误
     * 10204 json解析错误
     * 10205 未找到
     * 10206 请求频繁
     */
    PARAMETER_ERROR(10200, "参数错误"),
    METHOD_NOT_ALLOWED(10201, "请求方法不支持"),
    UNSUPPORTED_MEDIA_TYPE(10202, "不支持的媒体类型"),
    PARAMETER_TYPE_ERROR(10203, "参数类型错误"),
    JSON_PARSE_ERROR(10204, "json解析错误"),
    NOT_FOUND(10205, "未找到"),
    FREQUENT_REQUESTS(10206, "请求频繁"),
    ;

    int code;
    String message;

    BizCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
