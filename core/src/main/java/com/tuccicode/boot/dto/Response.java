package com.tuccicode.boot.dto;


import com.tuccicode.boot.exception.ErrorCode;

/**
 * @author tucci.lee
 */
public class Response extends DTO {

    protected static final boolean SUCCESS = true;
    protected static final boolean FAILURE = false;

    private boolean status;

    private int code;

    private String message;

    protected Response() {
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", code=" + code +
                ", message=" + message +
                '}';
    }

    public static Response success() {
        Response response = new Response();
        response.setStatus(SUCCESS);
        return response;
    }


    public static Response failure(int code, String message) {
        Response response = new Response();
        response.setStatus(FAILURE);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public static Response failure(ErrorCode errorCode) {
        Response response = new Response();
        response.setStatus(FAILURE);
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return response;
    }
}
