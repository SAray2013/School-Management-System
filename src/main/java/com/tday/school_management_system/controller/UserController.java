package com.tday.school_management_system.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tday.school_management_system.model.User;
import com.tday.school_management_system.dto.PageDTO;
import com.tday.school_management_system.dto.UsersDTO;
import com.tday.school_management_system.mapper.UsersMapper;
import com.tday.school_management_system.service.UsersService;
import com.tday.school_management_system.utils.ResponseHandler;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsersService userService;

    @PreAuthorize("hasAnyAuthority('user:write')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UsersDTO userDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been saved.",
                    UsersMapper.INSTANCE.toUsersDTO(userService.create(UsersMapper.INSTANCE.toUsers(userDTO),"ADMIN"))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    UsersMapper.INSTANCE.toUsersDTO(userService.getById(id))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping
    public ResponseEntity<?> list(@RequestParam Map<String, String> params) {
        try{
            Page<User> userPage = userService.getAll(params);
            PageDTO pageDTO = new PageDTO(userPage);
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    pageDTO
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('user:update')")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UsersDTO userDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been updated.",
                    UsersMapper.INSTANCE.toUsersDTO(userService.update(id, UsersMapper.INSTANCE.toUsers(userDTO)))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try{
            userService.delete(id);
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    null
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}
