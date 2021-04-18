package models;

import java.util.List;

import com.ncsu.wolfwr.entity.Shipment;

import lombok.Data;

@Data
public class SupplierShipmentPOJO {
	
	private Shipment shipmentDetails;
	private Integer supplierId;
	private List<ShipmentProductDetails> productsList;
}
