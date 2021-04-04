package com.ncsu.wolfwr.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Store;
import com.ncsu.wolfwr.repository.StoreRepository;

@Service
public class StoreService {
	
	StoreRepository storeRepo;
	
	@Autowired
	StoreService(StoreRepository storeRepo) {
		this.storeRepo = storeRepo;
	}
	
	public Store getStoreById(Integer storeId) {
		Optional<Store> storeOptional = storeRepo.findById(storeId);
		if (storeOptional.isPresent()) {
			Store store = storeOptional.get();
			return store;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
//		return storeRepo.findById(storeId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
