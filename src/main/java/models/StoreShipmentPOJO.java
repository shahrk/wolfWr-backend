package models;

import java.util.Date;
import java.util.List;

import com.ncsu.wolfwr.entity.Shipment;

import lombok.Data;

@Data
public class StoreShipmentPOJO {
	
	private Shipment shipmentDetails;
	private Integer senderStoreId;
	private Integer senderWarehouseCheckerId;
	private Date shipmentDate;
	private List<ProductsDetailsJSON> productsList;
}
