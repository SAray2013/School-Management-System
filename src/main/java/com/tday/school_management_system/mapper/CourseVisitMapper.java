package com.tday.school_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.tday.school_management_system.dto.CourseVisitDTO;
import com.tday.school_management_system.model.CourseVisit;
import com.tday.school_management_system.service.CourseService;
import com.tday.school_management_system.service.UsersService;

@Mapper(componentModel = "spring", uses = {CourseService.class, UsersService.class})
public interface CourseVisitMapper {
	
	CourseVisitMapper INSTANCE  = Mappers.getMapper(CourseVisitMapper.class);
	
	@Mapping(target = "course", source = "courseId")
    @Mapping(target = "user" , source="userId")
    CourseVisit toCourseVisit(CourseVisitDTO CourseVisitDTO);

    @Mapping(target = "courseId", source = "course.courseId")
    @Mapping(target = "userId", source="user.id")
    CourseVisitDTO toCourseVisitDTO(CourseVisit courseEnroll);

}