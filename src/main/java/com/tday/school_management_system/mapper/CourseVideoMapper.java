package com.tday.school_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.tday.school_management_system.model.CourseVideo;
import com.tday.school_management_system.dto.CourseVideoDTO;
import com.tday.school_management_system.service.CourseService;

@Mapper(componentModel = "spring", uses = {CourseService.class})
public interface CourseVideoMapper {
	
	CourseVideoMapper INSTANCE  = Mappers.getMapper(CourseVideoMapper.class);
	
	@Mapping(target = "course", source = "courseId")
    CourseVideo toCourseVideo(CourseVideoDTO courseVideoDTO);

    @Mapping(target = "courseId", source = "course.courseId")
    CourseVideoDTO toCourseVideoDTO(CourseVideo courseVideo);

}
