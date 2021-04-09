package com.ncsu.wolfwr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncsu.wolfwr.entity.SignupInformation;
import com.ncsu.wolfwr.service.SignupService;

@RestController
@RequestMapping("/signup")
public class SignupController {
	
	@Autowired
	SignupService signupService;
	
	@GetMapping("/{id}")
	public SignupInformation getSignupById(@PathVariable("id") Integer signupId) {
		return signupService.getSignupById(signupId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createSignup(@RequestBody SignupInformation signup) {
		return new ResponseEntity<Integer>(signupService.createSignup(signup), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateSignup(@PathVariable("id") Integer signupId, @RequestBody SignupInformation signup) {
		signupService.updateSignup(signupId, signup);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSignup(@PathVariable("id") Integer signupId) {
		signupService.deleteSignup(signupId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
