package com.tday.school_management_system.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CourseDTO {
    private Long courseId;
    private Long categoryId;
    private String courseName;
    private String courseDescription;
    private BigDecimal coursePrice;
}
