package models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ncsu.wolfwr.entity.Shipment;

import lombok.Data;

@Data
public class StoreShipmentPOJO {
	
	private Shipment shipmentDetails;
	private Integer senderStoreId;
	private Integer senderWarehouseCheckerId;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="EST")
	private Date shipmentDate;
	private List<ShipmentMerchDetails> shipmentMerchDetails;
}
