package com.tday.school_management_system.service;

import java.util.List;

import com.tday.school_management_system.model.Role;

public interface RoleService {
	Role create(Role category);
	Role getById(Long id);
	Role getByName(String name);
	List<Role> getRoles();
	Role update(Long id, Role category);
	void delete(Long id);
}
