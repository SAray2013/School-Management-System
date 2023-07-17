package com.tday.school_management_system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tday.school_management_system.model.CourseVideo;

@Repository
public interface CourseVideoRepository extends JpaRepository<CourseVideo, Long>, JpaSpecificationExecutor<CourseVideo> {}
