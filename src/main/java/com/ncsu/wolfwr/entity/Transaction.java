package com.ncsu.wolfwr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;
	
	private Integer storeId;
	
	private Integer customerId;
	
	private Integer cashierId;
	
	@Temporal(TemporalType.DATE)
	private Date purchaseDate;

	private Float totalPrice;
	
	private Float cashbackReward;
	
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
}
