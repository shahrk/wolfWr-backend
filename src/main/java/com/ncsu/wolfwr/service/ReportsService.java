package com.ncsu.wolfwr.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ncsu.wolfwr.repository.TransactionRepository;


@Service
public class ReportsService {
	
	TransactionRepository transactionRepo;
	
	@Autowired
	ReportsService(TransactionRepository transactionRepo){
		this.transactionRepo = transactionRepo;
	}
	
	public List<Object> salesReportDay() {
		return transactionRepo.getSalesReportDay();
	}
	
	public List<Object> salesReportMonth() {
		return transactionRepo.getSalesReportMonth();
	}
	
	public List<Object> salesReportYear() {
		return transactionRepo.getSalesReportYear();
	}
	
	public List<Object> salesReportStore(Integer storeId, LocalDate startDate, LocalDate endDate) {
		return transactionRepo.getSalesReportStore( storeId,  startDate,  endDate);
	}
	
	
}
