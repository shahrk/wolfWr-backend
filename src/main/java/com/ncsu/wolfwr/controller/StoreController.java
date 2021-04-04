package com.ncsu.wolfwr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ncsu.wolfwr.entity.Store;
import com.ncsu.wolfwr.service.StoreService;

@RestController
@RequestMapping("/store")
public class StoreController {
	
	@Autowired
	StoreService storeService;
	
	@GetMapping("/{id}")
	public @ResponseBody Store getStoreById(@PathVariable("id") Integer storeId) {
		Store store = storeService.getStoreById(storeId);
		return store;
	}
}
