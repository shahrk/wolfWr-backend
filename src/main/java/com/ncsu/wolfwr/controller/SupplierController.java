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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncsu.wolfwr.entity.Supplier;
import com.ncsu.wolfwr.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	SupplierService supplierService;
	
	@GetMapping("/{id}")
	public Supplier getSupplierById(@PathVariable("id") Integer supplierId) {
		return supplierService.getSupplierById(supplierId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createSupplier(@RequestBody Supplier supplier) {
		return new ResponseEntity<Integer>(supplierService.createSupplier(supplier), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateSupplier(@PathVariable("id") Integer supplierId, @RequestBody Supplier supplier) {
		supplierService.updateSupplier(supplierId, supplier);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSupplier(@PathVariable("id") Integer supplierId) {
		supplierService.deleteSupplier(supplierId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}/pendingpayment")
	public ResponseEntity<Float> getPendingPayment(@PathVariable("id") Integer supplierId, @RequestParam Integer storeId) {
		Float pendingPayment = supplierService.getPendingPayment(supplierId, storeId);
		return new ResponseEntity<Float>(pendingPayment, HttpStatus.OK);
	}
}
