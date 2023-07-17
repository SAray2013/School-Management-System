package com.tday.school_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tday.school_management_system.dto.RoleDTO;
import com.tday.school_management_system.mapper.RoleMapper;
import com.tday.school_management_system.service.RoleService;
import com.tday.school_management_system.utils.ResponseHandler;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAnyAuthority('role:write')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoleDTO roleDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been saved.",
                    RoleMapper.INSTANCE.toRoleDTO(roleService.create(RoleMapper.INSTANCE.toRole(roleDTO)))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    @PreAuthorize("hasAnyAuthority('role:read')")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    RoleMapper.INSTANCE.toRoleDTO(roleService.getById(id))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    
    @PreAuthorize("hasAnyAuthority('role:read')")
    @GetMapping
    public ResponseEntity<?> list(){
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    roleService.getRoles()
                    .stream()
                    .map(r -> RoleMapper.INSTANCE.toRoleDTO(r))
                    .toList()
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('role:update')")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody RoleDTO roleDTO) {
        try{
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been updated.",
                    RoleMapper.INSTANCE.toRoleDTO(roleService.update(id, RoleMapper.INSTANCE.toRole(roleDTO)))
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('role:delete')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try{
            roleService.delete(id);
            return ResponseHandler.generateResponseSuccessful(
                    "Success",
                    null
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}
