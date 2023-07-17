package com.tday.school_management_system.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.tday.school_management_system.model.Permission;

public interface PermissionService {
	Permission create(Permission permission);
    Permission getById(Long id);
    Page<Permission> getAll(Map<String, String> params);
    Permission update(Long id, Permission permission);
    void delete(Long id);
}
