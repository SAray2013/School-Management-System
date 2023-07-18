package com.tday.school_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tday.school_management_system.model.Email;
import com.tday.school_management_system.dto.UsersDTO;
import com.tday.school_management_system.mapper.UsersMapper;
import com.tday.school_management_system.service.EmailService;
import com.tday.school_management_system.service.UsersService;
import com.tday.school_management_system.utils.ResponseHandler;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
    private UsersService userService;
	@Autowired
	private EmailService emailService;

    @PostMapping("/author")
    public ResponseEntity<?> registeationAuthor(@RequestBody UsersDTO userDTO) {
        try{
        	UsersDTO tmp = UsersMapper.INSTANCE.toUsersDTO(userService.create(UsersMapper.INSTANCE.toUsers(userDTO),"AUTHOR"));
        	Email email = new Email();
        	email.setRecipient(tmp.getEmail());
        	email.setSubject("Complete Account Sign Up");
        	email.setMsgBody(emailService.templateEmail(tmp));
        	emailService.sendSimpleMail(email);
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been registered. Please confirm your email.",
                    tmp
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    
    @PostMapping("/subscriber")
    public ResponseEntity<?> registeationSubscriber(@RequestBody UsersDTO userDTO) {
        try{
        	UsersDTO tmp = UsersMapper.INSTANCE.toUsersDTO(userService.create(UsersMapper.INSTANCE.toUsers(userDTO),"SUBSCRIBER"));
            return ResponseHandler.generateResponseSuccessful(
                    "Your data has been registered. Please confirm your email.",
                    tmp
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
   
    @GetMapping("/verify/{id}")
    public ResponseEntity<?> verifyEmail(@PathVariable("id") Long id) {
    	try{
    		userService.verifyEmail(id);
            return ResponseHandler.generateResponseSuccessful(
                    "You has been activated.",
                    null
            );
        }catch (Exception e){
            return ResponseHandler.generateResponseUnsuccessful(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
