package com.ncsu.wolfwr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
