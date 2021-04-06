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

import com.ncsu.wolfwr.entity.Store;
import com.ncsu.wolfwr.service.StoreService;
import com.ncsu.wolfwr.service.StoreService;

@RestController
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
	StoreService staffService;
	
	@GetMapping("/{id}")
	public Store getStoreById(@PathVariable("id") Integer staffId) {
		return staffService.getStoreById(staffId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createStore(@RequestBody Store staff) {
		return new ResponseEntity<Integer>(staffService.createStore(staff), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateStore(@PathVariable("id") Integer staffId, @RequestBody Store staff) {
		staffService.updateStore(staffId, staff);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStore(@PathVariable("id") Integer staffId) {
		staffService.deleteStore(staffId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
