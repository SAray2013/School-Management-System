package com.tday.school_management_system.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.tday.school_management_system.model.User;

public interface UsersService {
	User create(User user);
	User getById(Long id);
	Page<User> getAll(Map<String, String> params);
	User update(Long id, User user);
	void delete(Long id);
}
