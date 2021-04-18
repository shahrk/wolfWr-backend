package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Customer;
import com.ncsu.wolfwr.entity.SignupInformation;
import com.ncsu.wolfwr.repository.CustomerRepository;
import com.ncsu.wolfwr.repository.MembershipTierRepository;
import com.ncsu.wolfwr.repository.SignupRepository;

import models.CustomerSignupPOJO;
import utility.BasicUtils;

@Service
public class CustomerService {
	CustomerRepository customerRepo;
	
	SignupRepository signupRepo;
	
	MembershipTierRepository membershipTierRepo;
	
	@Autowired
	CustomerService(CustomerRepository customerRepo, MembershipTierRepository membershipTierRepo, SignupRepository signupRepo) {
		this.customerRepo = customerRepo;
		this.membershipTierRepo = membershipTierRepo;
		this.signupRepo = signupRepo;
	}
	
	
	public Customer getCustomerById(int customerId) {
		return customerRepo.findById(customerId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createCustomer(CustomerSignupPOJO customerPojo) {
		Customer customer = new Customer(customerPojo);
		SignupInformation signupInformation = new SignupInformation(customerPojo);
		if (customer.getCustomerId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if(BasicUtils.isEmpty(customer.getRewardPoints())) {
			customer.setRewardPoints((float) 0);
		}
		
		customer = this.customerRepo.save(customer);
		
		signupInformation.setCustomerId(customer.getCustomerId());
		this.signupRepo.save(signupInformation);
		
		return customer.getCustomerId();
	}
	
	public void updateCustomer(int id, Customer customer) {
		if (customer.getCustomerId() != null && customer.getCustomerId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.customerRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.customerRepo.save(customer);
	}
	
	public void cancelMembershio(int id) {
		Customer customer = this.customerRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
		customer.setMembershipStatus(false);
		this.customerRepo.save(customer);
	}
	
	public void deleteCustomer(int id) {
		this.customerRepo.deleteById(id);
	}
}
