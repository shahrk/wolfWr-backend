package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Discount;
import com.ncsu.wolfwr.repository.DiscountRepository;

@Service
public class DiscountService {
	DiscountRepository discountRepo;
	
	@Autowired
	DiscountService(DiscountRepository discountRepo) {
		this.discountRepo = discountRepo;
	}
	
	public Discount getDiscountById(Integer discountId) {
		return discountRepo.findById(discountId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createDiscount(Discount discount) {
		if (discount.getDiscountId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		discount = this.discountRepo.save(discount);
		
		return discount.getDiscountId();
	}
	
	public void updateDiscount(Integer id, Discount discount) {
		if (discount.getDiscountId() != null && discount.getDiscountId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.discountRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.discountRepo.save(discount);
	}
	
	public void deleteDiscount(Integer id) {
		this.discountRepo.deleteById(id);
	}
}
