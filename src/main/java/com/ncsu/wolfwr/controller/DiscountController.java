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

import com.ncsu.wolfwr.entity.Discount;
import com.ncsu.wolfwr.service.DiscountService;

@RestController
@RequestMapping("/discount")
public class DiscountController {
	@Autowired
	DiscountService discountService;
	
	@GetMapping("/{id}")
	public Discount getDiscountById(@PathVariable("id") Integer discountId) {
		return discountService.getDiscountById(discountId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createDiscount(@RequestBody Discount discount) {
		return new ResponseEntity<Integer>(discountService.createDiscount(discount), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateDiscount(@PathVariable("id") Integer discountId, @RequestBody Discount discount) {
		discountService.updateDiscount(discountId, discount);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDiscount(@PathVariable("id") Integer discountId) {
		discountService.deleteDiscount(discountId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
