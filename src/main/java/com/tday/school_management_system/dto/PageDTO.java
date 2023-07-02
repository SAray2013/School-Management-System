package com.tday.school_management_system.dto;

import lombok.Data;
import java.util.List;
import org.springframework.data.domain.Page;

@Data
public class PageDTO {
    
    private List<?> list;
    private PaginationDTO pagination;

    public PageDTO(Page<?> page) {
        this.list = page.getContent();
        this.pagination = PaginationDTO.builder()
                .empty(page.isEmpty())
                .first(page.isFirst())
                .last(page.isLast())
                .pageSize(page.getPageable().getPageSize())
                .pageNumber(page.getPageable().getPageNumber()+1)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .build();
    }

}
