package com.tday.school_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.tday.school_management_system.model.User;
import com.tday.school_management_system.config.security.AuthUser;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    AuthUser toAuthUser(User user);
}