package com.tday.school_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.tday.school_management_system.model.CourseEnroll;
import com.tday.school_management_system.dto.CourseEnrollDTO;
import com.tday.school_management_system.service.CourseService;
import com.tday.school_management_system.service.UsersService;

@Mapper(componentModel = "spring", uses = {CourseService.class, UsersService.class})
public interface CourseEnrollMapper {
	
	CourseEnrollMapper INSTANCE  = Mappers.getMapper(CourseEnrollMapper.class);
	
	@Mapping(target = "course", source = "courseId")
    @Mapping(target = "user" , source="userId")
    CourseEnroll toCourseEnroll(CourseEnrollDTO CourseEnrollDTO);

    @Mapping(target = "courseId", source = "course.courseId")
    @Mapping(target = "userId", source="user.id")
    CourseEnrollDTO toCourseEnrollDTO(CourseEnroll courseEnroll);

}