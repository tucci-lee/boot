package com.tuccicode.boot.dto;

import java.util.Collection;

/**
 * @author tucci.lee
 */
public class PageResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    protected PageResponse() {
    }

    private Collection<T> data;

    private int total;

    public static <T> PageResponse<T> success(Collection<T> data, int total) {
        PageResponse<T> response = new PageResponse<>();
        response.setStatus(Boolean.TRUE);
        response.setData(data);
        response.setTotal(total);
        return response;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }
}
