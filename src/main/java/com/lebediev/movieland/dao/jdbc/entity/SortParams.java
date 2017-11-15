package com.lebediev.movieland.dao.jdbc.entity;

public class SortParams {
    private OrderBy orderBy;
    private SortDirection sortDirection;

    public SortParams(){}

    public SortParams(OrderBy orderBy, SortDirection sortDirection){
        this.orderBy = orderBy;
        this.sortDirection = sortDirection;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }


}
