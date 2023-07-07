package com.tday.school_management_system.service;

import java.util.Map;
import java.util.List;
import org.springframework.data.domain.Page;
import com.tday.school_management_system.model.Course;
import com.tday.school_management_system.dto.CourseDisplayDTO;


public interface CourseService {
    Course create(Course course);
    Course getById(Long id);
    Course update(Long id, Course course);
    void delete(Long id);
    Page<Course> getAll(Map<String, String> params);
    List<CourseDisplayDTO> toCourseDisplayDTOs(List<Course> course);
}