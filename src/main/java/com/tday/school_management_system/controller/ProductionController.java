package com.tday.school_management_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/production")
@RequiredArgsConstructor
public class ProductionController {
	
	@PreAuthorize("hasAnyAuthority('production:write')")
	@PostMapping
	public ResponseEntity<?> create() {
		return ResponseEntity.ok("Create");
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		return ResponseEntity.ok("Get ID");
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id) {
		return ResponseEntity.ok("Update");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		return ResponseEntity.ok("Delete");
	}
	
	@PreAuthorize("hasAnyAuthority('production:read')")
	@GetMapping
	public ResponseEntity<?> list(){
		return ResponseEntity.ok("list");
	}
	
}