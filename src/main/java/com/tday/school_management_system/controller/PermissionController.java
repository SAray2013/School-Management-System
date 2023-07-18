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

import com.tday.school_management_system.dto.PageDTO;
import com.tday.school_management_system.model.Permission;
import com.tday.school_management_system.dto.PermissionDTO;
import com.tday.school_management_system.utils.ResponseHandler;
import com.tday.school_management_system.mapper.PermissionMapper;
import com.tday.school_management_system.service.PermissionService;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("hasAnyAuthority('permission:write')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PermissionDTO permissionDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been saved.",
                    PermissionMapper.INSTANCE.toPermissionDTO(permissionService.create(PermissionMapper.INSTANCE.toPermission(permissionDTO)))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    @PreAuthorize("hasAnyAuthority('permission:read')")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    PermissionMapper.INSTANCE.toPermissionDTO(permissionService.getById(id))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    
    @PreAuthorize("hasAnyAuthority('permission:read')")
    @GetMapping
    public ResponseEntity<?> list(@RequestParam Map<String, String> params) {
        try{
            Page<Permission> permissionPage = permissionService.getAll(params);
            PageDTO pageDTO = new PageDTO(permissionPage);
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    pageDTO
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('permission:update')")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody PermissionDTO permissionDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been updated.",
                    PermissionMapper.INSTANCE.toPermissionDTO(permissionService.update(id, PermissionMapper.INSTANCE.toPermission(permissionDTO)))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('permission:delete')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try{
            permissionService.delete(id);
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    null
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}
