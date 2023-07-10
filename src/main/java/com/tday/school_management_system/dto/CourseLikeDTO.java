package com.tday.school_management_system.dto;

import lombok.Data;

@Data
public class CourseLikeDTO {
	private Long courseLikeId;
    private Long courseId;
    private Long userId;
}
