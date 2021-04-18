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

import com.ncsu.wolfwr.entity.Transaction;
import com.ncsu.wolfwr.service.TransactionService;

import models.TransactionPOJO;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	@Autowired
	TransactionService transactionService;
	
	@GetMapping("/{id}")
	public Transaction getTransactionById(@PathVariable("id") Integer transactionId) {
		return transactionService.getTransactionById(transactionId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createTransaction(@RequestBody TransactionPOJO transaction) {
		return new ResponseEntity<Integer>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateProduct(@PathVariable("id") Integer transactionId, @RequestBody TransactionPOJO transaction) {
		transactionService.updateTransaction(transactionId, transaction);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer transactionId) {
		transactionService.deleteTransaction(transactionId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
