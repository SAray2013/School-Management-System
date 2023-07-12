package com.tday.school_management_system.service;

import com.tday.school_management_system.model.Email;
import com.tday.school_management_system.dto.UsersDTO;

public interface EmailService {

    void sendSimpleMail(Email email);
    String templateEmail(UsersDTO usersDTO);
    
}
