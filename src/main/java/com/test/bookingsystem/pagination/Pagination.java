package com.test.bookingsystem.pagination;

import java.util.Optional;

public class Pagination {
    private Integer pageNumber;
    private Integer pageSize;

    private Integer DEFAULT_PAGE = 1, DEFAULT_PAGE_SIZE = 20, MAX_PAGE_SIZE = 50;

    public Pagination(Optional<Integer> pageNumber, Optional<Integer> pageSize) {
        this.pageNumber = pageNumber.orElse(DEFAULT_PAGE) - 1;
        this.pageSize = validateAndGetPageSize( pageSize.orElse(DEFAULT_PAGE_SIZE) );
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    private Integer validateAndGetPageSize(Integer pageSize) {
        return pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
    }
}
