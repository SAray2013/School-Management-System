package com.tday.school_management_system.dto;

import lombok.Data;

@Data
public class CourseEnrollDTO {
	private Long courseEnrollId;
    private Long courseId;
    private Long userId;
	private Short isApproved;
}
