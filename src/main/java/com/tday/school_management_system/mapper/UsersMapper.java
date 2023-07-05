package com.tday.school_management_system.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.tday.school_management_system.model.User;
import com.tday.school_management_system.dto.UsersDTO;

@Mapper
public interface UsersMapper {

    UsersMapper INSTANCE  = Mappers.getMapper(UsersMapper.class);

    User toUsers(UsersDTO userDTO);

    UsersDTO toUsersDTO(User user);
}
