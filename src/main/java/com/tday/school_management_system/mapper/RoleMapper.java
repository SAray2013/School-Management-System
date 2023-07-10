package com.tday.school_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.tday.school_management_system.model.Role;
import com.tday.school_management_system.dto.RoleDTO;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE  = Mappers.getMapper(RoleMapper.class);

    Role toRole(RoleDTO roleDTO);

    RoleDTO toRoleDTO(Role role);
}
