package com.ncsu.wolfwr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import models.StoreShipmentPOJO;

@Entity
@Data
@NoArgsConstructor
public class StoreShipment {

	@Id
	private Integer shipmentId;
	
	private Integer senderStoreId;
	
	private Integer senderWarehouseCheckerId;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="EST")
	private Date shipmentDate;
	
	public StoreShipment (StoreShipmentPOJO storeShipment) {
		this.shipmentId = storeShipment.getShipmentDetails().getShipmentId();
		this.senderStoreId = storeShipment.getSenderStoreId();
		this.senderWarehouseCheckerId = storeShipment.getSenderWarehouseCheckerId();
		this.shipmentDate = storeShipment.getShipmentDate();
	}
}
