package com.tday.school_management_system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.tday.school_management_system.model.Role;
import com.tday.school_management_system.service.RoleService;
import com.tday.school_management_system.repository.RoleRepository;
import com.tday.school_management_system.exception.ResourceNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role create(Role role) {
    	try{
            return roleRepository.save(role);
        }catch (Exception e){
            throw new RuntimeException("Role name already exists.");
        }
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role"));
    }
    
    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }
    
    @Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

    @Override
    public Role update(Long id, Role role) {
        getById(id);
        role.setId(id);
        try{
            return roleRepository.save(role);
        }catch (Exception e){
            throw new RuntimeException("Role name already exists.");
        }
    }

    @Override
    public void delete(Long id) {
        getById(id);
        roleRepository.deleteById(id);
    }
    
}
