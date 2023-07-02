package com.tday.school_management_system.service.impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

import com.tday.school_management_system.utils.PageUtil;
import com.tday.school_management_system.model.Permission;
import com.tday.school_management_system.spec.PermissionSpec;
import com.tday.school_management_system.spec.PermissionFilter;
import com.tday.school_management_system.service.PermissionService;
import com.tday.school_management_system.repository.PermissionRepository;
import com.tday.school_management_system.exception.ResourceNotFoundException;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Permission create(Permission permission) {
    	try{
            return permissionRepository.save(permission);
        }catch (Exception e){
            throw new RuntimeException("Permission name already exists.");
        }
    }

    @Override
    public Permission getById(Long id) {
        return permissionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Permission"));
    }
    
    @Override
	public Page<Permission> getAll(Map<String, String> params) {
    	PermissionFilter permissionFilter = new PermissionFilter();

        if (params.containsKey("id")) {
            permissionFilter.setId(Long.valueOf(params.get("id")));
        }

        if (params.containsKey("name")) {
            permissionFilter.setName(params.get("name"));
        }

        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageUtil.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        int pageSize = PageUtil.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageUtil.PAGE_LIMIT)) {
            pageSize = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }

        PermissionSpec permissionSpec = new PermissionSpec(permissionFilter);

        Pageable pageable = PageUtil.getPageable(pageNumber, pageSize);

        Page<Permission> permissionPage = permissionRepository.findAll(permissionSpec, pageable);

        return permissionPage;
	}
    
    @Override
    public Permission update(Long id, Permission permission) {
        getById(id);
        permission.setId(id);
        try{
            return permissionRepository.save(permission);
        }catch (Exception e){
            throw new RuntimeException("Permission name already exists.");
        }
    }

    @Override
    public void delete(Long id) {
        getById(id);
        permissionRepository.deleteById(id);
    }
    
}
