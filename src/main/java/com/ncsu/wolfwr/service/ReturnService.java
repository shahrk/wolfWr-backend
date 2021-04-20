package com.ncsu.wolfwr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Customer;
import com.ncsu.wolfwr.entity.MembershipTier;
import com.ncsu.wolfwr.entity.ReturnContainsMerchandise;
import com.ncsu.wolfwr.entity.Returns;
import com.ncsu.wolfwr.entity.Transaction;
import com.ncsu.wolfwr.entity.TransactionContainsMerchandise;
import com.ncsu.wolfwr.entity.TransactionMerchandiseId;
import com.ncsu.wolfwr.repository.CustomerRepository;
import com.ncsu.wolfwr.repository.MembershipTierRepository;
import com.ncsu.wolfwr.repository.MerchandiseRepository;
import com.ncsu.wolfwr.repository.ReturnContainsMerchandiseRepository;
import com.ncsu.wolfwr.repository.ReturnRepository;
import com.ncsu.wolfwr.repository.TransactionContainsMerchandiseRepository;
import com.ncsu.wolfwr.repository.TransactionRepository;

import models.ReturnPOJO;
import models.ReturnResponse;

@Service
public class ReturnService {

	ReturnRepository returnRepo;
	ReturnContainsMerchandiseRepository returnContainsMerchRepo;
	TransactionRepository transactionRepo;
	TransactionContainsMerchandiseRepository transactionContainsMerchRepo;
	MerchandiseRepository merchRepo;
	CustomerRepository customerRepo;
	MembershipTierRepository membershipTierRepo;
	
	@Autowired
	ReturnService(ReturnRepository returnRepo, ReturnContainsMerchandiseRepository returnContainsMerchRepo, 
			TransactionRepository transactionRepo, TransactionContainsMerchandiseRepository transactionContainsMerchRepo,
			MerchandiseRepository merchRepo, CustomerRepository customerRepo, MembershipTierRepository membershipTierRepo) {
		this.returnRepo = returnRepo;
		this.returnContainsMerchRepo = returnContainsMerchRepo;
		this.transactionRepo = transactionRepo;
		this.transactionContainsMerchRepo = transactionContainsMerchRepo;
		this.merchRepo = merchRepo;
		this.customerRepo = customerRepo;
		this.membershipTierRepo = membershipTierRepo;
	}
	
	// demo this, show a create for return and show how it gets rejected in certain scenarios
	/**
	 * @param list of all the merchandise and quantity to be returned for a transaction
	 * @return newly created return id and amount to be returned
	 * 1. Create a new entry in return entity
	 * 2. Create a new entry in return_contains_merchandise entity.
	 * 3. Update the stock in a store for all the products in a return.
	 * 4. Fetch the amount to be returned from the transaction and return total sum. 
	 */
	@Transactional
	public ReturnResponse createReturn(ReturnPOJO returnPojo) {
		ReturnResponse returnResp = new ReturnResponse();
		Returns returns = returnPojo.getReturns();
		if (returns == null || returns.getReturnId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		returns = returnRepo.save(returns);
		Map<Integer, Integer> merchCountMap = returnPojo.getMerchCountMap();
		List<ReturnContainsMerchandise> returnedMerch = new ArrayList<ReturnContainsMerchandise>();
		List<Integer> transactionMerch = transactionContainsMerchRepo.getMerchandiseByTransaction(returns.getTransactionId());
		Set<Integer> merchSet = new HashSet<Integer>(transactionMerch);
		float returnAmount = (float) 0.0;
		for (Entry<Integer, Integer> merchCount: merchCountMap.entrySet()) {
			if (!merchSet.contains(merchCount.getKey())) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
			returnedMerch.add(new ReturnContainsMerchandise(returns.getReturnId(), 
																	merchCount.getKey(), 
																	merchCount.getValue()));
			merchRepo.addMerchandiseStock(merchCount.getKey(), merchCount.getValue());
			TransactionMerchandiseId transactionMerchandiseId = new TransactionMerchandiseId();
			transactionMerchandiseId.setTransactionId(returns.getTransactionId());
			transactionMerchandiseId.setMerchandiseId(merchCount.getKey());
			TransactionContainsMerchandise transactionContainsMerch = transactionContainsMerchRepo.findById(transactionMerchandiseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
			returnAmount += transactionContainsMerch.getDiscountedPrice() * merchCount.getValue();
		}
		returnContainsMerchRepo.saveAll(returnedMerch);
		returnResp.setReturnId(returns.getReturnId());
		returnResp.setReturnAmount(returnAmount);
		
		//Get TierId from customer Id
		//Get Tier Name from TierId
		Transaction transactionObj = transactionRepo.getTransactionById(returns.getTransactionId());
		
		Customer customer = customerRepo.getCustomerById(transactionObj.getCustomerId());
		Optional<MembershipTier> memberType = membershipTierRepo.findById(customer.getTierId());
		
		if(memberType.orElseThrow().getTierName().equalsIgnoreCase("Platinum")) {
			Float prevReward = transactionObj.getCashbackReward();
			transactionObj.setCashbackReward(prevReward - ((float) (returnAmount*0.02)));
			transactionRepo.save(transactionObj);
			
			//Update reward point in customer table
			prevReward = customer.getRewardPoints();
			customer.setRewardPoints(prevReward + ((float) (-returnAmount*0.02)));
			customerRepo.save(customer);
			
		}
				
		return returnResp;
	}
	
	/**
	 * @param list of all the merchandise and quantity to be updated for a return.
	 * Update the stock quantity  in the store for all updated merchandises. 
	 */ 
	@Transactional
	public void updateReturn(ReturnPOJO returnPojo) {
		Returns returns = returnPojo.getReturns();
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
			merchRepo.addMerchandiseStock(merchCount.getKey(), merchCount.getValue() - old_qty);
		}
		returnContainsMerchRepo.saveAll(returnedMerch);
	}
	
	public ReturnPOJO getReturn(int id) {
		ReturnPOJO retPojo = new ReturnPOJO();
		Returns ret = returnRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		retPojo.setReturns(ret);
		List<ReturnContainsMerchandise> returnedMerch = returnContainsMerchRepo.findByReturnId(ret.getReturnId());
		Map<Integer,Integer> merchCountMap = new HashMap<Integer,Integer>();
		returnedMerch.forEach(merch -> merchCountMap.put(merch.getMerchandiseId(), merch.getQuantity()));
		retPojo.setMerchCountMap(merchCountMap);
		return retPojo;
	}
}
