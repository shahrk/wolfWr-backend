package com.ncsu.wolfwr.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncsu.wolfwr.service.ReportsService;

@RestController
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	ReportsService reportsService;
	
	@GetMapping("/day")
	public List<Object> salesReportDay() {
		return reportsService.salesReportDay();
	}
	
	@GetMapping("/month")
	public List<Object> salesReportMonth() {
		return reportsService.salesReportMonth();
	}
	
	@GetMapping("/year")
	public List<Object> salesReportYear() {
		return reportsService.salesReportYear();
	}
	
	@GetMapping("/store/{id}/{startDate}/{endDate}")
	public List<Object> salesReportStore(@PathVariable("id") Integer storeId, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		
		return reportsService.salesReportStore(storeId, startDate, endDate);
	}
	
	@GetMapping("/store/inventory/{id}")
	public List<Object> inventoryStore(@PathVariable("id") Integer storeId) {
		
		return reportsService.inventoryStore(storeId);
	}
	
	@GetMapping("/product/inventory/{id}")
	public List<Object> inventoryProduct(@PathVariable("id") Integer productId) {
		
		return reportsService.inventoryProduct(productId);
	}
	
	@GetMapping("/customer/growth/{startDate}/{endDate}")
	public List<Object> customerGrowth(@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		
		return reportsService.customerGrowth(startDate, endDate);
	}
	
	@GetMapping("/customer/activity/{id}/{startDate}/{endDate}")
	public List<Object> customerActivity(@PathVariable("id") Integer customerId, @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		
		return reportsService.customerActivity(customerId, startDate, endDate);
	}
	
}
