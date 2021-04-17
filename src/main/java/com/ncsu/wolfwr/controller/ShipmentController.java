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

import com.ncsu.wolfwr.entity.Shipment;
import com.ncsu.wolfwr.service.ShipmentService;

import models.StoreShipmentPOJO;
import models.SupplierShipmentPOJO;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {
	@Autowired
	ShipmentService shipmentService;
	
	@GetMapping("/{id}")
	public Shipment getShipmentById(@PathVariable("id") Integer shipmentId) {
		return shipmentService.getShipmentById(shipmentId);
	}
	
	@PostMapping("/store")
	public ResponseEntity<Integer> createStoreShipment(@RequestBody StoreShipmentPOJO storeShipment) {
		return new ResponseEntity<Integer>(shipmentService.createStoreShipment(storeShipment), HttpStatus.CREATED);
	}
	
	@PostMapping("/supplier")
	public ResponseEntity<Integer> createSupplierShipment(@RequestBody SupplierShipmentPOJO supplierShipment) {
		return new ResponseEntity<Integer>(shipmentService.createSupplierShipment(supplierShipment), HttpStatus.CREATED);
	}
	
	@PutMapping("/store/{id}")
	public void updateStoreShipment(@PathVariable("id") Integer shipmentId, @RequestBody StoreShipmentPOJO storeShipment) {
		shipmentService.updateStoreShipment(shipmentId, storeShipment);
	}
	
	@PutMapping("/supplier/{id}")
	public void updateSupplierShipment(@PathVariable("id") Integer shipmentId, @RequestBody SupplierShipmentPOJO supplierShipment) {
		shipmentService.updateSupplierShipment(shipmentId, supplierShipment);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer shipmentId) {
		shipmentService.deleteShipment(shipmentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
