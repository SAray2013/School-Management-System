package com.tday.school_management_system.dto;

import lombok.Data;

@Data
public class CourseVisitDTO {
	private Long courseVisitId;
    private Long courseId;
    private Long userId;
}
