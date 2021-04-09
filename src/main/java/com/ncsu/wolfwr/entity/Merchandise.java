package com.ncsu.wolfwr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Merchandise implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "merchandise_id")
	private Integer merchandiseId;
	
	@Column(name = "product_id")
	private Integer productId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "expiration_date")
	private Date expirationDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "production_date")
	private Date productionDate;
	
	@Column(name = "store_id")
	private Integer storeId;
	
	@Column(name = "supplier_id")
	private Integer supplierId;

	@Column(name = "market_price",columnDefinition = "float4")
	private Float marketPrice;
	
	@Column(name = "buy_price",columnDefinition = "float4")
	private Float buyPrice;
	
	@Column(name = "quantity_in_stock")
	private Integer quantityInStock;
	
}
