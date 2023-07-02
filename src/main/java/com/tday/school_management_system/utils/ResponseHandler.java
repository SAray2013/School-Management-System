package com.tday.school_management_system.utils;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

  public static String getCurrentDateTime(){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLLL-yyyy HH:mm:ss");
    return LocalDateTime.now().format(formatter);
  }

  public static ResponseEntity<Object> generateResponseSuccessful(String message, Object data) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("time", getCurrentDateTime());
    response.put("status", HttpStatus.OK.value());
    response.put("isSuccess", true);
    response.put("message", message);
    response.put("data", data);
    System.out.println("Saray!");
    return new ResponseEntity<Object>(response,HttpStatus.OK);
  }

  public static ResponseEntity<Object> generateResponseUnsuccessful(HttpStatus status,String message) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("time", getCurrentDateTime());
    response.put("status", status.value());
    response.put("isSuccess",false);
    response.put("message", message);
    response.put("data", null);
    return new ResponseEntity<Object>(response,status);
  }

}
