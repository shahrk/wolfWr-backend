package com.ncsu.wolfwr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
@IdClass(TransactionMerchandiseId.class)
public class TransactionContainsMerchandise{
	
	@Id
	@Column(name = "transaction_id")
	private Integer transactionId;
	
	@Id
	@Column(name = "merchandise_id")
	private Integer merchandiseId;
	
	private Integer quantity;
	
}
