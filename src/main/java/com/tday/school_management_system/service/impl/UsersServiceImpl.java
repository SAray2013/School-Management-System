package com.tday.school_management_system.service.impl;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tday.school_management_system.model.Role;
import com.tday.school_management_system.model.User;
import com.tday.school_management_system.spec.UserSpec;
import com.tday.school_management_system.utils.PageUtil;
import com.tday.school_management_system.spec.UserFilter;
import com.tday.school_management_system.service.RoleService;
import com.tday.school_management_system.service.UsersService;
import com.tday.school_management_system.repository.UsersRepository;
import com.tday.school_management_system.exception.ResourceNotFoundException;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository userRepository;

    @Autowired
    RoleService roleService;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

    @Override
    public User create(User user, String roleName) {
    	try{
    		Role _tmp = roleService.getByName(roleName);
    		Set<Role> roles = Stream.of(_tmp).collect(Collectors.toCollection(HashSet::new));
    		user.setPassword(passwordEncoder.encode(user.getPassword()));
    		user.setIsCredentialsNonExpired(false);
    		user.setIsAccountNonLocked(false);
    		user.setIsAccountNonExpired(false);
    		user.setIsEnabled(false);
    		user.setRoles(roles);
            return userRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException("Username already exists.");
        }
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"));
    }
    
    @Override
	public Page<User> getAll(Map<String, String> params) {
    	UserFilter userFilter = new UserFilter();

        if (params.containsKey("id")) {
            userFilter.setId(Long.valueOf(params.get("id")));
        }

        if (params.containsKey("name")) {
            userFilter.setUserName(null);
        }

        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageUtil.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        int pageSize = PageUtil.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageUtil.PAGE_LIMIT)) {
            pageSize = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }

        UserSpec userSpec = new UserSpec(userFilter);

        Pageable pageable = PageUtil.getPageable(pageNumber, pageSize);

        Page<User> userPage = userRepository.findAll(userSpec, pageable);

        return userPage;
	}
    
    @Override
    public User update(Long id, User user) {
        User tmp = getById(id);
        user.setId(id);
        try{
        	if(!tmp.getPassword().equals(user.getPassword())){
        		user.setPassword(passwordEncoder.encode(user.getPassword()));
        	}
        	user.setRoles(tmp.getRoles());
            return userRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException("User name already exists.");
        }
    }

    @Override
    public void delete(Long id) {
        getById(id);
        userRepository.deleteById(id);
    }

	@Override
	public void verifyEmail(Long id) {
		User user = getById(id);
		user.setIsCredentialsNonExpired(true);
		user.setIsAccountNonLocked(true);
		user.setIsAccountNonExpired(true);
		user.setIsEnabled(true);
		user.setRoles(user.getRoles());
        userRepository.save(user);
	}
    
}
