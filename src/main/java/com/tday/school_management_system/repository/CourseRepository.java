package com.tday.school_management_system.repository;

import org.springframework.stereotype.Repository;
import com.tday.school_management_system.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {}
