package com.tday.school_management_system.service;

import java.util.Map;
import org.springframework.data.domain.Page;
import com.tday.school_management_system.model.Category;

public interface CategoryService {

    Category create(Category category);
    Category getById(Long id);
    Category update(Long id, Category category);
    void delete(Long id);
    Page<Category> getAll(Map<String, String> params);
    
}