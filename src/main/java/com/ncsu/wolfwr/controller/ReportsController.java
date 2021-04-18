package com.ncsu.wolfwr.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncsu.wolfwr.service.ReportsService;

@RestController
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	ReportsService reportsService;
	
	@GetMapping("/wolf/sales/day")
	public List<Map<Object, Object>> salesReportDay() {
		return reportsService.salesReportDay();
	}
	
	@GetMapping("/wolf/sales/month")
	public List<Map<Object, Object>> salesReportMonth() {
		return reportsService.salesReportMonth();
	}
	
	@GetMapping("/wolf/sales/year")
	public List<Map<Object, Object>> salesReportYear() {
		return reportsService.salesReportYear();
	}
	
	@GetMapping("/store/{id}/sales")
	public List<Map<Object, Object>> salesReportStore(@PathVariable("id") Integer storeId, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		
		return reportsService.salesReportStore(storeId, startDate, endDate);
	}
	
	@GetMapping("/store/{id}/inventory")
	public List<Map<Object, Object>> inventoryStore(@PathVariable("id") Integer storeId) {
		
		return reportsService.inventoryStore(storeId);
	}
	
	@GetMapping("/product/{id}/inventory")
	public List<Map<Object, Object>> inventoryProduct(@PathVariable("id") Integer productId) {
		
		return reportsService.inventoryProduct(productId);
	}
	
	@GetMapping("/customer/growth")
	public List<Map<Object, Object>> customerGrowth(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		
		return reportsService.customerGrowth(startDate, endDate);
	}
	
	@GetMapping("/customer/{id}/activity")
	public List<Map<Object, Object>> customerActivity(@PathVariable("id") Integer customerId, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		
		return reportsService.customerActivity(customerId, startDate, endDate);
	}
	
}
