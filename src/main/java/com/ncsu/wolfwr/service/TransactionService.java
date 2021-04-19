package com.ncsu.wolfwr.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Customer;
import com.ncsu.wolfwr.entity.Discount;
import com.ncsu.wolfwr.entity.MembershipTier;
import com.ncsu.wolfwr.entity.Merchandise;
import com.ncsu.wolfwr.entity.PaymentMethod;
import com.ncsu.wolfwr.entity.Transaction;
import com.ncsu.wolfwr.entity.TransactionContainsMerchandise;
import com.ncsu.wolfwr.repository.CustomerRepository;
import com.ncsu.wolfwr.repository.DiscountRepository;
import com.ncsu.wolfwr.repository.MembershipTierRepository;
import com.ncsu.wolfwr.repository.MerchandiseRepository;
import com.ncsu.wolfwr.repository.TransactionContainsMerchandiseRepository;
import com.ncsu.wolfwr.repository.TransactionRepository;

import models.TransactionMerchandiseDetails;
import models.TransactionPOJO;
import utility.BasicUtils;

@Service
public class TransactionService {
	TransactionRepository transactionRepo;
	
	MerchandiseRepository merchandiseRepo;
	
	CustomerRepository customerRepo;
	
	MembershipTierRepository membershipTierRepo;
	
	DiscountRepository discountRepo;
	
	TransactionContainsMerchandiseRepository transactionContainsMerchandiseRepo;
	
	@Autowired
	TransactionService(TransactionRepository transactionRepo, MerchandiseRepository merchandiseRepo, CustomerRepository customerRepo, MembershipTierRepository membershipTierRepo, 
			DiscountRepository discountRepo, TransactionContainsMerchandiseRepository transactionContainsMerchandiseRepo) {
		this.transactionRepo = transactionRepo;
		this.merchandiseRepo = merchandiseRepo;
		this.customerRepo = customerRepo;
		this.membershipTierRepo = membershipTierRepo;
		this.discountRepo = discountRepo;
		this.transactionContainsMerchandiseRepo = transactionContainsMerchandiseRepo;
	}
	
	public Transaction getTransactionById(int transactionId) {
		return transactionRepo.findById(transactionId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	/**
	 * @param merchList, transactionDetails
	 * @return transaction_id.
	 * 1. Create new entry in transaction and transaction_contains_merchandise entities.
	 * 2. Calculate discount for all the applicable products and sum of all discounts.
	 * 3. Decrease quantity in stock for all the merchandises in the transaction.
	 * 4. Check if the customer is eligible for the reward points. If eligible, update reward points in transaction.
	 */
	@Transactional
	public Integer createTransaction(TransactionPOJO transactionObj) {
		if (transactionObj.getTransactionDetails().getTransactionId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
				
		float sum = 0;
		Transaction transaction = transactionObj.getTransactionDetails();
		transaction.setPaymentMethod(PaymentMethod.Card);
		
		transaction = this.transactionRepo.save(transaction);
		
		for(TransactionMerchandiseDetails merchDetails : transactionObj.getMerchList()) {
			
			Merchandise merch = merchandiseRepo.findById(merchDetails.getMerchandiseId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
			//Calculating total price to be saved in transaction table with discount
			Discount discount = discountRepo.findDiscountByProductId(merch.getProductId());
			float discounted_price = merch.getMarketPrice();
			if((!BasicUtils.isEmpty(discount))) {
				discounted_price = (100 - discount.getDiscountPercentage()) / 100 * merch.getMarketPrice();
			}
			sum += discounted_price * merchDetails.getQuantity();
			
			//update the quantity of the merchandise table of every product
			merchandiseRepo.updateMerchandiseOnTransaction(merchDetails.getQuantity(),merchDetails.getMerchandiseId());
			
			TransactionContainsMerchandise obj = new TransactionContainsMerchandise();
			obj.setMerchandiseId(merchDetails.getMerchandiseId());
			obj.setTransactionId(transaction.getTransactionId());
			obj.setDiscountedPrice(discounted_price);
			obj.setQuantity(merchDetails.getQuantity());
			this.transactionContainsMerchandiseRepo.save(obj);
			
		}
		
		//Get TierId from customer Id
		//Get Tier Name from TierId
		Optional<Customer> customer = customerRepo.findById(transaction.getCustomerId());
		Optional<MembershipTier> memberType = membershipTierRepo.findById(customer.orElseThrow().getTierId());
		
		
		transaction.setTotalPrice(sum);
		if(memberType.orElseThrow().getTierName().equalsIgnoreCase("Platinum")) {
			transaction.setCashbackReward((float) (sum*0.02));
			
			//Update reward point in customer table
			customerRepo.updateRewardPointsOnTransaction((float) (sum*0.02),transaction.getCustomerId());
		}
		
		transaction = this.transactionRepo.save(transaction);
		
		return transaction.getTransactionId();
	}
	
	public void updateTransaction(int id, TransactionPOJO transactionObj) {
		if (transactionObj.getTransactionDetails().getTransactionId() != null && transactionObj.getTransactionDetails().getTransactionId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.transactionRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		float sum = 0;
		Transaction transaction = transactionObj.getTransactionDetails();
		Float prevReward = transaction.getCashbackReward();
		
		transaction = this.transactionRepo.save(transaction);
		
		for(TransactionMerchandiseDetails merchDetails : transactionObj.getMerchList()) {
			
			Merchandise merch = merchandiseRepo.findById(merchDetails.getMerchandiseId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
			//Calculating total price to be saved in transaction table with discount
			Discount discount = discountRepo.findDiscountByProductId(merch.getProductId());
			float discounted_price = merch.getMarketPrice();
			if((!BasicUtils.isEmpty(discount))) {
				discounted_price = (100 - discount.getDiscountPercentage()) / 100 * merch.getMarketPrice();
			}
			sum += discounted_price * merchDetails.getQuantity();
			
			//update the quantity of the merchandise and transactionContainsMerch table of every product
			TransactionContainsMerchandise transactionContainsMerch = transactionContainsMerchandiseRepo.getMerchandiseByIds(transaction.getTransactionId(),merchDetails.getMerchandiseId());
			
			int prevQuantity = transactionContainsMerch.getQuantity();
			merchandiseRepo.updateMerchandiseOnTransaction(merchDetails.getQuantity()-prevQuantity,merchDetails.getMerchandiseId());
			
			
			transactionContainsMerch.setDiscountedPrice(discounted_price);
			transactionContainsMerch.setQuantity(merchDetails.getQuantity());
			this.transactionContainsMerchandiseRepo.save(transactionContainsMerch);
			
		}
		
		//Get TierId from customer Id
		//Get Tier Name from TierId
		Optional<Customer> customer = customerRepo.findById(transaction.getCustomerId());
		Optional<MembershipTier> memberType = membershipTierRepo.findById(customer.orElseThrow().getTierId());
		
		
		transaction.setTotalPrice(sum);
		if(memberType.orElseThrow().getTierName().equalsIgnoreCase("Platinum")) {
			transaction.setCashbackReward((float) (sum*0.02));
			
			//Update reward point in customer table
			customerRepo.updateRewardPointsOnTransaction((float) (sum*0.02) - prevReward,transaction.getCustomerId());
		}
		
		transaction = this.transactionRepo.save(transaction);
		
	}
	
	public void deleteTransaction(int id) {
		this.transactionRepo.deleteById(id);
	}
}
