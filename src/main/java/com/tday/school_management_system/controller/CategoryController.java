package com.tday.school_management_system.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tday.school_management_system.dto.PageDTO;
import com.tday.school_management_system.model.Category;
import com.tday.school_management_system.dto.CategoryDTO;
import com.tday.school_management_system.mapper.CategoryMapper;
import com.tday.school_management_system.utils.ResponseHandler;
import com.tday.school_management_system.service.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('category:write')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been saved.",
                    categoryService.create(CategoryMapper.INSTANCE.toCategory(categoryDTO))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    @PreAuthorize("hasAuthority('category:read')")
    @GetMapping
    public ResponseEntity<?> list(@RequestParam Map<String, String> params) {
        try{
            Page<Category> categoryPage = categoryService.getAll(params);
            PageDTO pageDTO = new PageDTO(categoryPage);
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    pageDTO
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('category:read')")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    CategoryMapper.INSTANCE.toCategoryDto(categoryService.getById(id))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('category:update')")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    categoryService.update(id, CategoryMapper.INSTANCE.toCategory(categoryDTO))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('category:update')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try{
            categoryService.delete(id);
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    null
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}
