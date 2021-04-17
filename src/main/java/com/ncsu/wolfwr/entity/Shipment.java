package com.ncsu.wolfwr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Shipment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shipmentId;
	
	private Integer recepientStoreId;
	
	private Integer recepientWarehouseOperatorId;
	
	@Temporal(TemporalType.DATE)
	private Date receivedDate;
}
