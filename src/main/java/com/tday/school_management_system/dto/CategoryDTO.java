package com.tday.school_management_system.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CategoryDTO {

    private Long categoryId;
    private String categoryName;
    private Short categoryOrdering;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime modifiedAt;
    private Long modifiedBy;
    
}
