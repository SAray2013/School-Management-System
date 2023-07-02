package com.tday.school_management_system.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFoundException extends ApiException{
	
	private String resourceName;
	private Long resourceId;
	
	public ResourceNotFoundException(String resourceName) {
		super(HttpStatus.NOT_FOUND, String.format("%s not found.", resourceName));
	}
	

}