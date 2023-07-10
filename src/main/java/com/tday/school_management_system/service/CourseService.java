package com.tday.school_management_system.service;

import java.util.Map;
import java.util.List;
import org.springframework.data.domain.Page;
import com.tday.school_management_system.model.Course;
import com.tday.school_management_system.model.CourseLike;
import com.tday.school_management_system.model.CourseVisit;
import com.tday.school_management_system.model.CourseEnroll;
import com.tday.school_management_system.dto.CourseDisplayDTO;
import com.tday.school_management_system.dto.CourseEnrollDisplayDTO;

public interface CourseService {
    Course create(Course course);
    Course getById(Long id);
    Course update(Long id, Course course);
    void delete(Long id);
    Page<Course> getAll(Map<String, String> params);
    List<CourseDisplayDTO> toCourseDisplayDTOs(List<Course> course);
    void createCourseVisit(CourseVisit courseVisit);
    void createCourseLike(CourseLike courseLike);
    void enrollCourse(CourseEnroll courseEnroll);
    void approveCourse(Long id);
    void rejectCourse(Long id);
    Page<CourseEnroll> getListCourseEnroll(Map<String, String> params);
    List<CourseEnrollDisplayDTO> toCourseEnrollDisplayDTOs(List<CourseEnroll> courseEnroll);
}