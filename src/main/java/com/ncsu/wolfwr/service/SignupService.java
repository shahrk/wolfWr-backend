package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Customer;
import com.ncsu.wolfwr.entity.SignupInformation;
import com.ncsu.wolfwr.repository.CustomerRepository;
import com.ncsu.wolfwr.repository.SignupRepository;

import utility.BasicUtils;

@Service
public class SignupService {
	
	SignupRepository signupRepo;
	
	CustomerRepository customerRepo;
	
	@Autowired
	SignupService(SignupRepository signupRepo, CustomerRepository customerRepo) {
		this.signupRepo = signupRepo;
		this.customerRepo = customerRepo;
	}
	
	public SignupInformation getSignupById(Integer signupId) {
		return signupRepo.findById(signupId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createSignup(SignupInformation signup) {
		if (signup.getSignupId() != null || BasicUtils.isEmpty(signup.getCustomerId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		Customer customer = customerRepo.findById(signup.getCustomerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
		signup.setTierId(customer.getTierId());
		signup = this.signupRepo.save(signup);
		
		return signup.getSignupId();
	}
	
	public void updateSignup(Integer id, SignupInformation signup) {
		if (signup.getSignupId() != null && signup.getSignupId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.signupRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.signupRepo.save(signup);
	}
	
	public void deleteSignup(Integer id) {
		this.signupRepo.deleteById(id);
	}

}
