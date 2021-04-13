package com.ncsu.wolfwr.service;

import java.util.Date;
import java.util.Optional;

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

import models.ProductsDetailsJSON;
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
	
	public Transaction getTransactionById(Integer transactionId) {
		return transactionRepo.findById(transactionId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createTransaction(TransactionPOJO transactionObj) {
		if (transactionObj.getTransactionDetails().getTransactionId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		Date currentDate = new Date();
		
		float sum = 0;
		Transaction transaction = transactionObj.getTransactionDetails();
		transaction.setPaymentMethod(PaymentMethod.Card);
		
		transaction = this.transactionRepo.save(transaction);
		
		for(ProductsDetailsJSON product : transactionObj.getProductsList()) {
			
			//Calculating total price to be saved in transaction table with discount
			Discount discount = discountRepo.findDiscountByProductId(product.getProductId());
			if((!BasicUtils.isEmpty(discount)) && (discount.getDiscountStartDate().getTime() <=  currentDate.getTime()) && (discount.getDiscountExpirationDate().getTime() >= currentDate.getTime())) {
				sum += (product.getTotalPrice() - ((discount.getDiscountPercentage()/100)*product.getTotalPrice()));
			}else {
				sum += product.getTotalPrice();
			}
			
			//update the quantity of the merchandise table of every product
			merchandiseRepo.updateMerchandiseOnTransaction(product.getQuantity(),transaction.getStoreId(),product.getProductId());
			Merchandise merchandise = merchandiseRepo.findMerchandiseByProductStoreId(transaction.getStoreId(),product.getProductId());
			
			TransactionContainsMerchandise obj = new TransactionContainsMerchandise();
			obj.setMerchandiseId(merchandise.getMerchandiseId());
			obj.setTransactionId(transaction.getTransactionId());
			obj.setQuantity(product.getQuantity());
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
	
	public void updateTransaction(Integer id, Transaction transaction) {
		if (transaction.getTransactionId() != null && transaction.getTransactionId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.transactionRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.transactionRepo.save(transaction);
	}
	
	public void deleteTransaction(Integer id) {
		this.transactionRepo.deleteById(id);
	}
}
