package com.tday.school_management_system.dto;

import lombok.Data;

@Data
public class CourseVideoDTO {
	private Long courseVideoId;
	private Long courseId;
	private String courseVideoTitle;
	private String courseVideoDescription;
	private String courseVideoLink;
	private String courseVideoThumbnail;
	private Short courseOrdering;
}
