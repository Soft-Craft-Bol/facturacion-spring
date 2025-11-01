package com.gaspar.facturador.application.response;

import lombok.Data;
import java.util.List;

@Data
public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public PagedResponse(List<T> content, int number, int size, long totalElements, int totalPages, boolean last) {
        this.content = content;
        this.page = number;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }
}