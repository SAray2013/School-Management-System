package com.tday.school_management_system.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
	private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}