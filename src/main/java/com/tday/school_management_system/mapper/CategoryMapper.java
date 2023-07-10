package com.tday.school_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.tday.school_management_system.model.Category;
import com.tday.school_management_system.dto.CategoryDTO;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE  = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryDTO categoryDto);

    CategoryDTO toCategoryDto(Category category);
    
}
