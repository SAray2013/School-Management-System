package com.tday.school_management_system.service.impl;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.tday.school_management_system.model.Course;
import com.tday.school_management_system.model.Category;
import com.tday.school_management_system.utils.PageUtil;
import com.tday.school_management_system.spec.CourseSpec;
import com.tday.school_management_system.spec.CourseFilter;
import com.tday.school_management_system.service.CourseService;
import com.tday.school_management_system.dto.CourseDisplayDTO;
import com.tday.school_management_system.repository.CourseRepository;
import com.tday.school_management_system.repository.CategoryRepository;
import com.tday.school_management_system.exception.ResourceNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Course create(Course course) {
        Category _cate = course.getCategory();
        categoryRepository.findById(_cate.getCategoryId());
        return courseRepository.save(course);
    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Course"));
    }

    @Override
    public Course update(Long id, Course course) {
        try{
            getById(id);
            course.setCourseId(id);
            return courseRepository.save(course);
        }catch (Exception e){
            throw new RuntimeException("Invalid Input");
        }
    }

    @Override
    public void delete(Long id) {
        getById(id);
        courseRepository.deleteById(id);
    }

    @Override
    public Page<Course> getAll(Map<String, String> params) {
        CourseFilter courseFilter = new CourseFilter();
        Category category = new Category();

        if (params.containsKey("category_id")) {
            category.setCategoryId(Long.valueOf(params.get("category_id")));
            courseFilter.setCategory(category);
        }

        if (params.containsKey("cour_name")) {
            courseFilter.setCourseName(params.get("cour_name"));
        }


        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageUtil.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        int pageSize = PageUtil.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageUtil.PAGE_LIMIT)) {
            pageSize = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }

        CourseSpec courseSpec = new CourseSpec(courseFilter);

        Pageable pageable = PageUtil.getPageable(pageNumber, pageSize);

        Page<Course> coursePage = courseRepository.findAll(courseSpec, pageable);

        return coursePage;
    }

    @Override
    public List<CourseDisplayDTO> toCourseDisplayDTOs(List<Course> course) {
        List<CourseDisplayDTO> displayDTOs = new ArrayList<>();
        List<Long> categoryIds = course.stream().map(c -> c.getCategory().getCategoryId()).toList();
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        Map<Long,String> categoryMap = categories.stream().collect(Collectors.toMap(c -> c.getCategoryId(), c -> c.getCategoryName()));
        for(Course c: course){
            CourseDisplayDTO dto = new CourseDisplayDTO();
            dto.setCourseId(c.getCourseId());
            dto.setCategoryId(c.getCategory().getCategoryId());
            dto.setCategoryName(categoryMap.get(c.getCategory().getCategoryId()));
            dto.setCourseName(c.getCourseName());
            dto.setCourseDescription(c.getCourseDescription());
            dto.setCoursePrice(c.getCoursePrice());
            displayDTOs.add(dto);
        }
        return displayDTOs;
    }
}
