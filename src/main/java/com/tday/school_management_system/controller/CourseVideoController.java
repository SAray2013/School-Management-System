package com.tday.school_management_system.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tday.school_management_system.dto.PageDTO;
import com.tday.school_management_system.model.CourseVideo;
import com.tday.school_management_system.dto.CourseVideoDTO;
import com.tday.school_management_system.utils.ResponseHandler;
import com.tday.school_management_system.mapper.CourseVideoMapper;
import com.tday.school_management_system.service.CourseVideoService;

@RestController
@RequestMapping("/course-video")
public class CourseVideoController {

    @Autowired
    private CourseVideoService courseVideoService;
    @Autowired
    private CourseVideoMapper courseVideoMapper;

    @PreAuthorize("hasAnyAuthority('course-video:write')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CourseVideoDTO courseVideoDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been saved.",
                    courseVideoMapper.toCourseVideoDTO(courseVideoService.create(courseVideoMapper.toCourseVideo(courseVideoDTO)) )
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    @PreAuthorize("hasAnyAuthority('course-video:read')")
    @GetMapping
    public ResponseEntity<?> list(@RequestParam Map<String, String> params){
        try{
            Page<CourseVideo> coursePage = courseVideoService.getAll(params);
            PageDTO pageDTO = new PageDTO(coursePage);
            pageDTO.setList(courseVideoService.toCourseVideoDisplayDTOs(coursePage.getContent()));
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    pageDTO
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('course-video:read')")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    CourseVideoMapper.INSTANCE.toCourseVideoDTO(courseVideoService.getById(id))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    
    
    @PreAuthorize("hasAnyAuthority('course-video:update')")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CourseVideoDTO courseVideoDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    courseVideoMapper.toCourseVideoDTO(courseVideoService.update(id, courseVideoMapper.toCourseVideo(courseVideoDTO)))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('course-video:delete')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try{
            courseVideoService.delete(id);
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    null
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}

