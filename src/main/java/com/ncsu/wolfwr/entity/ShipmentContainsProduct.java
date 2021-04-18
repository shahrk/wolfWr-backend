package com.ncsu.wolfwr.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.ShipmentProductDetails;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
@IdClass(ShipmentProductId.class)
public class ShipmentContainsProduct {

	@Id
	private Integer shipmentId;
	
	@Id
	private Integer productId;
	
	private Integer quantity;
	
	public ShipmentContainsProduct(Integer shipmentId, ShipmentProductDetails productDetails) {
		this.shipmentId = shipmentId;
		this.productId = productDetails.getProductId();
		this.quantity = productDetails.getQuantity();
	}
	
}
