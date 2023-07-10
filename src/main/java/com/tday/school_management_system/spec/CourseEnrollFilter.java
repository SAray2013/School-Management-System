package com.tday.school_management_system.spec;

import lombok.Data;

import com.tday.school_management_system.model.User;
import com.tday.school_management_system.model.Course;

@Data
public class CourseEnrollFilter {
	private Course course;
	private User user;
}
