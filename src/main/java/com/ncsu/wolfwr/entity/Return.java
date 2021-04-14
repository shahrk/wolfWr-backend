package com.ncsu.wolfwr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Return {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer returnId;
	
	private Integer transactionId;
	
	private Integer warehouseOperatorId;
}
