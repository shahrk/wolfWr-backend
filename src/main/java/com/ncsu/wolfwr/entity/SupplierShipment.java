package com.ncsu.wolfwr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import models.SupplierShipmentPOJO;

@Data
@Entity
@NoArgsConstructor
public class SupplierShipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shipmentId;
	
	private Integer supplierId;
	
	public SupplierShipment (SupplierShipmentPOJO supplierShipmentPOJO) {
		this.shipmentId = supplierShipmentPOJO.getShipmentDetails().getShipmentId();
		this.supplierId = supplierShipmentPOJO.getSupplierId();
	}
}
