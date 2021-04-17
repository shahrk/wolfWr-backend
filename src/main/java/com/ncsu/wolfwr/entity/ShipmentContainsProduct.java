package com.ncsu.wolfwr.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import lombok.NoArgsConstructor;
import models.ProductsDetailsJSON;

@Entity
@Data
@NoArgsConstructor
@IdClass(ShipmentProductId.class)
public class ShipmentContainsProduct {

	@Id
	private Integer shipmentId;
	
	@Id
	private Integer productId;
	
	private Integer quantity;
	
	public ShipmentContainsProduct(Integer shipmentId, ProductsDetailsJSON productDetails) {
		this.shipmentId = shipmentId;
		this.productId = productDetails.getProductId();
		this.quantity = productDetails.getQuantity();
	}
}
