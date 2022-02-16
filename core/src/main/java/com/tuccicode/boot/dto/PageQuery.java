package com.tuccicode.boot.dto;

/**
 * @author tucci.lee
 */
public class PageQuery extends Query {

    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

    private static final int DEFAULT_PAGE_SIZE = 10;

    private int pageNo;

    private int pageSize;

    private String orderBy;

    private String orderDirection = DESC;

    public int getPageNo() {
        return Math.max(pageNo, 1);
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        if (pageSize < 1 || pageSize > 200) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public int getPageStartNo() {
        int pageNo = getPageNo();
        return (pageNo - 1) * getPageSize();
    }
}
