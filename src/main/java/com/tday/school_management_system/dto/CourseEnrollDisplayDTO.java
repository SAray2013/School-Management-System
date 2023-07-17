package com.tday.school_management_system.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CourseEnrollDisplayDTO {
	private Long courseEnrollId;
    private Long courseId;
    private String courseName;
    private BigDecimal coursePrice;
    private Long userId;
    private String userFirstName;
    private String userLastName;
	private Short isApproved;
}
