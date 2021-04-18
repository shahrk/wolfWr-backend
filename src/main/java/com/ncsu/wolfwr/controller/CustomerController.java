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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncsu.wolfwr.entity.Customer;
import com.ncsu.wolfwr.service.CustomerService;

import models.CustomerSignupPOJO;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/{id}")
	public Customer getCustomerById(@PathVariable("id") Integer customerId) {
		return customerService.getCustomerById(customerId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> registerCustomer(@RequestBody CustomerSignupPOJO customer) {
		return new ResponseEntity<Integer>(customerService.createCustomer(customer), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateCustomer(@PathVariable("id") Integer customerId, @RequestBody Customer customer) {
		customerService.updateCustomer(customerId, customer);
	}
	
	@PostMapping("/{id}/cancelmembership")
	public void cancelMembership(@PathVariable("id") Integer customerId) {
		customerService.cancelMembershio(customerId);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") Integer customerId) {
		customerService.deleteCustomer(customerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}/rewards")
	public ResponseEntity<Float> getRewardPoints(@RequestParam(name = "year", required = false) Integer year, @PathVariable("id") Integer customerId) {
		return new ResponseEntity<Float>(customerService.getRewardPoints(customerId, year), HttpStatus.OK);
	}

	@PostMapping("/{id}/rewards/reset")
	public ResponseEntity<?>  resetRewardPoints(@PathVariable("id") Integer customerId) {
		customerService.resetRewardPoints(customerId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
