package com.tday.school_management_system.service;

import java.util.Map;
import java.util.List;

import org.springframework.data.domain.Page;

import com.tday.school_management_system.model.CourseVideo;
import com.tday.school_management_system.dto.CourseVideoDisplayDTO;

public interface CourseVideoService {
	CourseVideo create(CourseVideo courseVideo);
	CourseVideo getById(Long id);
	Page<CourseVideo> getAll(Map<String, String> params);
    List<CourseVideoDisplayDTO> toCourseVideoDisplayDTOs(List<CourseVideo> courseVideo);
    List<CourseVideoDisplayDTO> toPreviewCourseVideoDisplayDTOs(List<CourseVideo> courseVideo);
	CourseVideo update(Long id, CourseVideo courseVideo);
	void delete(Long id);
}
