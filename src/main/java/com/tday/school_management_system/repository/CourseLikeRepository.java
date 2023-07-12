package com.tday.school_management_system.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tday.school_management_system.model.CourseLike;

public interface CourseLikeRepository extends JpaRepository<CourseLike, Long> {
	@Query(value =  "SELECT" + "\r\n" +
            " *" + "\r\n" +
            "FROM tbl_course_like" + "\r\n" +
            "WHERE" + "\r\n" +
            " course_id = :courseId AND" + "\r\n" +
            " user_id = :userId", nativeQuery = true)
	CourseLike existsByCourseIdAndUserId(@Param("courseId") Long courseId,@Param("userId") Long userId);
}