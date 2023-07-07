package com.tday.school_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.tday.school_management_system.model.Permission;
import com.tday.school_management_system.dto.PermissionDTO;

@Mapper
public interface PermissionMapper {

    PermissionMapper INSTANCE  = Mappers.getMapper(PermissionMapper.class);

    Permission toPermission(PermissionDTO permissionDTO);

    PermissionDTO toPermissionDTO(Permission permission);
}
