package com.tday.school_management_system.spec;

import lombok.Data;
import com.tday.school_management_system.model.Course;

@Data
public class CourseVideoFilter {
	private Course course;
	private String courseVideoTitle;
}
