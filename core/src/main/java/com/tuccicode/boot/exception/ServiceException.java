package com.tuccicode.boot.exception;

/**
 * @author tucci.lee
 */
public class ServiceException extends RuntimeException{

    private final int code;

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(ErrorMessage errorMessage){
        this(errorMessage.getCode(), errorMessage.getMessage());
    }

    public int getCode() {
        return code;
    }
}
