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

import com.ncsu.wolfwr.entity.Store;
import com.ncsu.wolfwr.service.StoreService;

@RestController
@RequestMapping("/store")
public class StoreController {
	
	@Autowired
	StoreService storeService;
	
	@GetMapping("/{id}")
	public Store getStoreById(@PathVariable("id") Integer storeId) {
		return storeService.getStoreById(storeId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createStore(@RequestBody Store store) {
		return new ResponseEntity<Integer>(storeService.createStore(store), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateStore(@PathVariable("id") Integer storeId, @RequestBody Store store) {
		storeService.updateStore(storeId, store);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStore(@PathVariable("id") Integer storeId) {
		storeService.deleteStore(storeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
