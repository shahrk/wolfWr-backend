package com.ncsu.wolfwr.service;

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
		return storeRepo.findById(storeId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createStore(Store store) {
		if (store.getStoreId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		store = this.storeRepo.save(store);
		
		return store.getStoreId();
	}
	
	public void updateStore(Integer id, Store store) {
		if (store.getStoreId() != null && store.getStoreId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.storeRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.storeRepo.save(store);
	}
	
	public void deleteStore(Integer id) {
		this.storeRepo.deleteById(id);
	}
}
