package com.ncsu.wolfwr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class BillingInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer billId;
	
	private Integer shipmentId;
	
	private Integer staffId;
	
	private Float amount;
	
	private boolean paymentStatus;
}
