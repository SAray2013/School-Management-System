package com.tday.school_management_system.service.impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.tday.school_management_system.utils.PageUtil;
import com.tday.school_management_system.model.Category;
import com.tday.school_management_system.spec.CategorySpec;
import com.tday.school_management_system.spec.CategoryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import com.tday.school_management_system.service.CategoryService;
import com.tday.school_management_system.repository.CategoryRepository;
import com.tday.school_management_system.exception.ResourceNotFoundException;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category"));
    }

    @Override
    public Category update(Long id, Category category) {
        getById(id);
        category.setCategoryId(id);
        try{
            return categoryRepository.save(category);
        }catch (Exception e){
            throw new RuntimeException("Category name already exists.");
        }
    }

    @Override
    public void delete(Long id) {
        getById(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<Category> getAll(Map<String, String> params) {
        CategoryFilter categoryFilter = new CategoryFilter();

        if (params.containsKey("category_id")) {
            categoryFilter.setCategoryId(Long.valueOf(params.get("category_id")));
        }

        if (params.containsKey("cate_name")) {
            categoryFilter.setCategoryName(params.get("cate_name"));
        }


        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageUtil.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        int pageSize = PageUtil.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageUtil.PAGE_LIMIT)) {
            pageSize = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }

        CategorySpec categorySpec = new CategorySpec(categoryFilter);

        Pageable pageable = PageUtil.getPageable(pageNumber, pageSize);

        Page<Category> categoryPage = categoryRepository.findAll(categorySpec, pageable);

        return categoryPage;
    }
    
}
