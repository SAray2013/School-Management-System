package com.tday.school_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.tday.school_management_system.dto.CourseLikeDTO;
import com.tday.school_management_system.model.CourseLike;
import com.tday.school_management_system.service.CourseService;
import com.tday.school_management_system.service.UsersService;

@Mapper(componentModel = "spring", uses = {CourseService.class, UsersService.class})
public interface CourseLikeMapper {
	
	CourseEnrollMapper INSTANCE  = Mappers.getMapper(CourseEnrollMapper.class);
	
	@Mapping(target = "course", source = "courseId")
    @Mapping(target = "user" , source="userId")
    CourseLike toCourseLike(CourseLikeDTO CourseLikeDTO);

    @Mapping(target = "courseId", source = "course.courseId")
    @Mapping(target = "userId", source="user.id")
    CourseLikeDTO toCourseLikeDTO(CourseLike courseLike);

}