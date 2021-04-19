package com.ncsu.wolfwr.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncsu.wolfwr.entity.BillingInfo;
import com.ncsu.wolfwr.service.BillingService;

import models.ShipmentProductDetails;

@RestController
@RequestMapping("/billing")
public class BillingController {
	@Autowired
	BillingService billingService;
	
	@GetMapping("/{id}")
	public BillingInfo getBillById(@PathVariable("id") Integer billId) {
		return billingService.getBillById(billId);
	}
	
	@GetMapping("/supplier/{id}")
	public Float getSupplierPendingPayments(@RequestParam(name = "storeId", required = false) Integer storeId, @PathVariable("id") Integer supplierId) {
		return billingService.getSupplierPendingPayments(supplierId, storeId);
	}
	
	@GetMapping("/store")
	public Float getStorePendingPayments(@RequestParam(name = "receivingStoreId") Integer receivingStoreId, @RequestParam(name = "senderStoreId") Integer senderStoreId) {
		return billingService.getStorePendingPaymentForStore(receivingStoreId, senderStoreId);
	}
	
	@PostMapping("/")
	public void generateBill(@RequestParam(name = "receivingStoreId") Integer receivingStoreId, @RequestParam(name = "shipmentId") Integer shipmentId,@RequestBody List<ShipmentProductDetails> products) {
		billingService.generateBill(receivingStoreId, shipmentId, products);
	}
	
	@PutMapping("/{id}")
	public void updateBill(@PathVariable("id") Integer billId, @RequestBody BillingInfo bill) {
		billingService.updateBill(billId, bill);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBill(@PathVariable("id") Integer billId) {
		billingService.deleteBill(billId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
