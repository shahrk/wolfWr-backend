package com.ncsu.wolfwr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
public class Returns {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer returnId;
	
	private Integer transactionId;
	
	private Integer warehouseOperatorId;
}
