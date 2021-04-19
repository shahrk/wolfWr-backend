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
	
	/**
	 * @return a map of <date,sum of all transactions for a day of all the stores>  
	 */
	public List<Map<Object, Object>> salesReportDay() {
		return transactionRepo.getSalesReportDay();
	}
	
	/**
	 * @return a map of <month,sum of all transactions for a month of all the stores>  
	 */
	public List<Map<Object, Object>> salesReportMonth() {
		return transactionRepo.getSalesReportMonth();
	}
	
	/**
	 * @return a map of <year,sum of all transactions for a year of all the stores>  
	 */
	public List<Map<Object, Object>> salesReportYear() {
		return transactionRepo.getSalesReportYear();
	}
	
	/**
	 * @param storeId
	 * @param startDate
	 * @param endDate
	 * @return for a given store, returns sum of all the transaction before start_date,
	 *  	   sum of all the transactions before end date and growth in sales between the given time period.
	 */
	public List<Map<Object, Object>> salesReportStore(Integer storeId, LocalDate startDate, LocalDate endDate) {
		return transactionRepo.getSalesReportStore( storeId,  startDate,  endDate);
	}
	
	/**
	 * @param storeId
	 * @return  all the details of each merchandise present for a given store.
	 */
	public List<Map<Object, Object>> inventoryStore(Integer storeId) {
		return merchandiseRepo.getInventoryStore(storeId);
	}
	
	/**
	 * @param productId
	 * @return a map of quantity of the given product in each store if the product exists in a store.
	 */

	public List<Map<Object, Object>> inventoryProduct(Integer productId) {
		return merchandiseRepo.getInventoryProduct(productId);
	}
	
	/**
	 * @param productId 
	 * @param storeId
	 * @param startDate
	 * @param endDate
	 * @return a map of quantity of the given product in each store if the product exists in a store.
	 */

	public List<Map<Object, Object>> inventoryStoreProduct(Integer storeId, Integer productId, LocalDate startDate, LocalDate endDate) {
		if (storeId == null && productId == null) {
			return merchandiseRepo.getInventoryDuration(startDate, endDate);
		} else if(storeId != null && productId == null){
			return merchandiseRepo.getInventoryStoreDuration(storeId, startDate, endDate);
		} else if(storeId == null && productId != null){
			return merchandiseRepo.getInventoryProductDuration(productId, startDate, endDate);
		} else {
			return merchandiseRepo.getInventoryStoreProductDuration(storeId,productId, startDate, endDate);
		}
	}
	
	/**
	 * @param startDate
	 * @param endDate
	 * @return sum of all the customers before start_date, sum of all the customers before end date
	 *         and growth in number of customers between the given time period.
	 */
	public List<Map<Object, Object>> customerGrowth(Integer storeId, LocalDate startDate, LocalDate endDate) {
		if (storeId == null) {
			return signupRepo.getCustomerGrowth(startDate, endDate);
			
		} else {
			return signupRepo.getCustomerGrowthStore(storeId, startDate, endDate);
		}
	}
	
	/**
	 * @param customerId
	 * @param startDate
	 * @param endDate
	 * @return total sum of all transactions for a customer across all the stores for a given time period.
	 */
	public List<Map<Object, Object>> customerActivity(Integer customerId, LocalDate startDate, LocalDate endDate) {
		return transactionRepo.getCustomerActivity(customerId, startDate, endDate);
	}
}
