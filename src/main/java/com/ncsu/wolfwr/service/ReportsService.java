package com.ncsu.wolfwr.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncsu.wolfwr.repository.MerchandiseRepository;
import com.ncsu.wolfwr.repository.SignupRepository;
import com.ncsu.wolfwr.repository.TransactionRepository;

@Service
public class ReportsService {
	
	TransactionRepository transactionRepo;
	MerchandiseRepository merchandiseRepo;
	SignupRepository signupRepo;
	
	@Autowired
	ReportsService(TransactionRepository transactionRepo,MerchandiseRepository merchandiseRepo, SignupRepository signupRepo){
		this.transactionRepo = transactionRepo;
		this.merchandiseRepo = merchandiseRepo;
		this.signupRepo = signupRepo;
	}
	
	public List<Map<Object, Object>> salesReportDay() {
		return transactionRepo.getSalesReportDay();
	}
	
	public List<Map<Object, Object>> salesReportMonth() {
		return transactionRepo.getSalesReportMonth();
	}
	
	public List<Map<Object, Object>> salesReportYear() {
		return transactionRepo.getSalesReportYear();
	}
	
	public List<Map<Object, Object>> salesReportStore(Integer storeId, LocalDate startDate, LocalDate endDate) {
		return transactionRepo.getSalesReportStore( storeId,  startDate,  endDate);
	}
	
	public List<Map<Object, Object>> inventoryStore(Integer storeId) {
		return merchandiseRepo.getInventoryStore(storeId);
	}
	
	public List<Map<Object, Object>> inventoryProduct(Integer productId) {
		return merchandiseRepo.getInventoryProduct(productId);
	}
	
	public List<Map<Object, Object>> customerGrowth(LocalDate startDate, LocalDate endDate) {
		return signupRepo.getCustomerGrowth(startDate, endDate);
	}
	
	public List<Map<Object, Object>> customerActivity(Integer customerId, LocalDate startDate, LocalDate endDate) {
		return transactionRepo.getCustomerActivity(customerId, startDate, endDate);
	}
}
