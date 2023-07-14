package com.tday.school_management_system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tday.school_management_system.model.CourseEnroll;

@Repository
public interface CourseEnrollRepository extends JpaRepository<CourseEnroll, Long>, JpaSpecificationExecutor<CourseEnroll> {
	
	@Query(value =  "SELECT" + "\r\n" +
            " *" + "\r\n" +
            "FROM tbl_course_enroll" + "\r\n" +
            "WHERE" + "\r\n" +
            " course_id = :courseId AND" + "\r\n" +
            " user_id = :userId", nativeQuery = true)
	CourseEnroll existsByCourseIdAndUserId(@Param("courseId") Long courseId,@Param("userId") Long userId);
}