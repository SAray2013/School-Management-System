package com.tday.school_management_system.controller;

import java.util.Map;

import com.tday.school_management_system.dto.PageDTO;
import com.tday.school_management_system.model.Course;
import com.tday.school_management_system.dto.CourseDTO;
import com.tday.school_management_system.mapper.CourseMapper;
import com.tday.school_management_system.service.CourseService;
import com.tday.school_management_system.utils.ResponseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseMapper courseMapper;

    @PreAuthorize("hasAnyAuthority('course:write')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CourseDTO courseDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been saved.",
                    courseMapper.toCourseDTO(courseService.create(courseMapper.toCourse(courseDTO)) )
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    @PreAuthorize("hasAnyAuthority('course:read')")
    @GetMapping
    public ResponseEntity<?> list(@RequestParam Map<String, String> params){
        try{
            Page<Course> coursePage = courseService.getAll(params);
            PageDTO pageDTO = new PageDTO(coursePage);
            pageDTO.setList(courseService.toCourseDisplayDTOs(coursePage.getContent()));
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    pageDTO
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('course:read')")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    CourseMapper.INSTANCE.toCourseDTO(courseService.getById(id))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('course:update')")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CourseDTO courseDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    courseMapper.toCourseDTO(courseService.update(id, courseMapper.toCourse(courseDTO)))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('course:delete')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try{
            courseService.delete(id);
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    null
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}
