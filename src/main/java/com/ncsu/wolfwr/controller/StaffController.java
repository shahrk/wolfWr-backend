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

import com.ncsu.wolfwr.entity.Staff;
import com.ncsu.wolfwr.service.StaffService;
import com.ncsu.wolfwr.service.StaffService;

@RestController
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
	StaffService staffService;
	
	@GetMapping("/{id}")
	public Staff getStaffById(@PathVariable("id") Integer staffId) {
		return staffService.getStaffById(staffId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createStaff(@RequestBody Staff staff) {
		return new ResponseEntity<Integer>(staffService.createStaff(staff), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateStaff(@PathVariable("id") Integer staffId, @RequestBody Staff staff) {
		staffService.updateStaff(staffId, staff);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStaff(@PathVariable("id") Integer staffId) {
		staffService.deleteStaff(staffId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
