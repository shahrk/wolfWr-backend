package com.ncsu.wolfwr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Return;
import com.ncsu.wolfwr.entity.ReturnContainsMerchandise;
import com.ncsu.wolfwr.repository.MerchandiseRepository;
import com.ncsu.wolfwr.repository.ReturnContainsMerchandiseRepository;
import com.ncsu.wolfwr.repository.ReturnRepository;
import com.ncsu.wolfwr.repository.TransactionContainsMerchandiseRepository;
import com.ncsu.wolfwr.repository.TransactionRepository;

import models.ReturnPOJO;

@Service
public class ReturnService {

	ReturnRepository returnRepo;
	ReturnContainsMerchandiseRepository returnContainsMerchRepo;
	TransactionRepository transactionRepo;
	TransactionContainsMerchandiseRepository transactionContainsMerchRepo;
	MerchandiseRepository merchRepo;
	
	@Autowired
	ReturnService(ReturnRepository returnRepo, ReturnContainsMerchandiseRepository returnContainsMerchRepo, 
			TransactionRepository transactionRepo, TransactionContainsMerchandiseRepository transactionContainsMerchRepo,
			MerchandiseRepository merchRepo) {
		this.returnRepo = returnRepo;
		this.returnContainsMerchRepo = returnContainsMerchRepo;
		this.transactionRepo = transactionRepo;
		this.transactionContainsMerchRepo = transactionContainsMerchRepo;
		this.merchRepo = merchRepo;
	}
	
	// demo this, show a create for return and show how it gets rejected in certain scenarios
	@Transactional
	public int createReturn(ReturnPOJO returnPojo) {
		Return returns = returnPojo.getReturns();
		if (returns == null || returns.getReturnId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		returns = returnRepo.save(returns);
		Map<Integer, Integer> merchCountMap = returnPojo.getMerchCountMap();
		List<ReturnContainsMerchandise> returnedMerch = new ArrayList<ReturnContainsMerchandise>();
		List<Integer> transactionMerch = transactionContainsMerchRepo.getMerchandiseByTransaction(returns.getTransactionId());
		Set<Integer> merchSet = new HashSet<Integer>(transactionMerch);
		for (Entry<Integer, Integer> merchCount: merchCountMap.entrySet()) {
			if (!merchSet.contains(merchCount.getKey())) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
			returnedMerch.add(new ReturnContainsMerchandise(returns.getReturnId(), 
																	merchCount.getKey(), 
																	merchCount.getValue()));
			merchRepo.updateMerchandiseOnReturn(merchCount.getKey(), merchCount.getValue());
		}
		returnContainsMerchRepo.saveAll(returnedMerch);
		return returns.getReturnId();
	}
	
	@Transactional
	public void updateReturn(ReturnPOJO returnPojo) {
		Return returns = returnPojo.getReturns();
		if (returns.getReturnId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		returnRepo.save(returns);
		Map<Integer, Integer> merchCountMap = returnPojo.getMerchCountMap();
		List<ReturnContainsMerchandise> returnedMerch = new ArrayList<ReturnContainsMerchandise>();
		for (Entry<Integer, Integer> merchCount: merchCountMap.entrySet()) {
			returnedMerch.add(new ReturnContainsMerchandise(returns.getReturnId(), 
																	merchCount.getKey(), 
																	merchCount.getValue()));
			int old_qty = returnContainsMerchRepo.getQuantityOfReturnedMerch(returns.getReturnId(), merchCount.getKey());
			merchRepo.updateMerchandiseOnReturn(merchCount.getKey(), merchCount.getValue() - old_qty);
		}
		returnContainsMerchRepo.saveAll(returnedMerch);
	}
	
	public ReturnPOJO getReturn(int id) {
		ReturnPOJO retPojo = new ReturnPOJO();
		Return ret = returnRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		retPojo.setReturns(ret);
		List<ReturnContainsMerchandise> returnedMerch = returnContainsMerchRepo.findByReturnId(ret.getReturnId());
		Map<Integer,Integer> merchCountMap = new HashMap<Integer,Integer>();
		returnedMerch.forEach(merch -> merchCountMap.put(merch.getMerchandiseId(), merch.getQuantity()));
		retPojo.setMerchCountMap(merchCountMap);
		return retPojo;
	}
}
