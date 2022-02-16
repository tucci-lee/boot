package com.tuccicode.boot.dto;

/**
 * @author tucci.lee
 */
public class SingletonResponse<T> extends Response {

    private T data;

    public static <T> SingletonResponse<T> success(T data) {
        SingletonResponse<T> response = new SingletonResponse<>();
        response.setStatus(SUCCESS);
        response.setData(data);
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
