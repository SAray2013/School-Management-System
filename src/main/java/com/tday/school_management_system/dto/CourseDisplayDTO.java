package com.tday.school_management_system.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CourseDisplayDTO {
    private Long courseId;
    private Long categoryId;
    private String categoryName;
    private String courseName;
    private String courseDescription;
    private BigDecimal coursePrice;
}
