package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Transaction;
import com.ncsu.wolfwr.repository.TransactionRepository;

@Service
public class TransactionService {
	TransactionRepository transactionRepo;
	
	@Autowired
	TransactionService(TransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}
	
	public Transaction getTransactionById(Integer transactionId) {
		return transactionRepo.findById(transactionId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createTransaction(Transaction transaction) {
		if (transaction.getTransactionId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		transaction = this.transactionRepo.save(transaction);
		
		return transaction.getTransactionId();
	}
	
	public void updateTransaction(Integer id, Transaction transaction) {
		if (transaction.getTransactionId() != null && transaction.getTransactionId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.transactionRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.transactionRepo.save(transaction);
	}
	
	public void deleteTransaction(Integer id) {
		this.transactionRepo.deleteById(id);
	}
}
