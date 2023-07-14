package com.tday.school_management_system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import com.tday.school_management_system.model.Email;
import com.tday.school_management_system.dto.UsersDTO;
import com.tday.school_management_system.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired private JavaMailSender javaMailSender;
	 
    @Value("${spring.mail.username}") private String sender;

	@Override
	public void sendSimpleMail(Email email) {
		try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(email.getRecipient());
            mailMessage.setSubject(email.getSubject());
            mailMessage.setText(email.getMsgBody());
            javaMailSender.send(mailMessage);
        }
        catch (Exception e) {
        	throw new RuntimeException("Cannot send email.");
        }
	}

	@Override
	public String templateEmail(UsersDTO usersDTO) {
		return 	  "Dear " + usersDTO.getLastName() + " " + usersDTO.getFirstName() + "," 
				+ "\n\n" 
				+ "Thanks for your registration to Ray System.\n"
				+ "You need to confirm your email address in order to continue the registration."
				+ "\n\n" 
	            + "To verify your email, please click here: http://localhost:8080/registration/verify/" + usersDTO.getId()
	            + "\n\n"
	            + "If you can't create your username and password, please contact our support 098552515."
	            + "\n\n" 
	            + "Have a Good Day!"
	            + "\n\n" 
	            + "Best regards \n"
	            + "Ray System Co., Ltd.";
	}

}
