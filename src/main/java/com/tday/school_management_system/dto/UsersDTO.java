package com.tday.school_management_system.dto;

import lombok.Data;

@Data
public class UsersDTO {
	private Long id;
	private String lastName;
	private String firstName;
	private String username;
	private String password;
	private String email;
}
