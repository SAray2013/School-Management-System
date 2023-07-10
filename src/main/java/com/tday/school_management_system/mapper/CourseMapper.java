package com.tday.school_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.tday.school_management_system.model.Course;
import com.tday.school_management_system.dto.CourseDTO;
import com.tday.school_management_system.service.CategoryService;

@Mapper(componentModel = "spring", uses = {CategoryService.class})
public interface CourseMapper {

    CourseMapper INSTANCE  = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "category", source = "categoryId")
    Course toCourse(CourseDTO courseDTO);

    @Mapping(target = "categoryId", source = "category.categoryId")
    CourseDTO toCourseDTO(Course course);
}
