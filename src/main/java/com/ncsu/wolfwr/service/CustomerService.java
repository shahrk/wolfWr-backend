package com.ncsu.wolfwr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Customer;
import com.ncsu.wolfwr.entity.MembershipTier;
import com.ncsu.wolfwr.repository.CustomerRepository;
import com.ncsu.wolfwr.repository.MembershipTierRepository;

import utility.BasicUtils;

@Service
public class CustomerService {
	CustomerRepository customerRepo;
	
	
	MembershipTierRepository membershipTierRepo;
	
	@Autowired
	CustomerService(CustomerRepository customerRepo, MembershipTierRepository membershipTierRepo) {
		this.customerRepo = customerRepo;
		this.membershipTierRepo = membershipTierRepo;
	}
	
	
	public Customer getCustomerById(Integer customerId) {
		return customerRepo.findById(customerId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createCustomer(Customer customer) {
		if (customer.getCustomerId() != null || !BasicUtils.isEmpty(customer.getMembershipLevel())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if(BasicUtils.isEmpty(customer.getRewardsPoints())) {
			customer.setRewardsPoints((float) 0);
		}
		
		if(!BasicUtils.isEmpty(customer.getMembershipLevel())) {
			
			MembershipTier membershipObj = membershipTierRepo.findMembershipByName(customer.getMembershipLevel());

			if(!BasicUtils.isEmpty(membershipObj)) {
				customer.setTierId(membershipObj.getTierId());
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
		}
		customer = this.customerRepo.save(customer);
		
		return customer.getCustomerId();
	}
	
	public void updateCustomer(Integer id, Customer customer) {
		if (customer.getCustomerId() != null && customer.getCustomerId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.customerRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.customerRepo.save(customer);
	}
	
	public void deleteCustomer(Integer id) {
		this.customerRepo.deleteById(id);
	}
}
