package com.tuccicode.boot.exception;

/**
 * @author tucci.lee
 */
public class BizException extends RuntimeException{

    private final int code;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(ErrorCode errorCode){
        this(errorCode.getCode(), errorCode.getMessage());
    }

    public int getCode() {
        return code;
    }
}
