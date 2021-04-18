package models;

import java.util.Date;

import lombok.Data;

@Data
public class ShipmentProductDetails {

	private Integer productId;
	private Float buyPrice;
	private Float marketPrice;
	private Date productionDate;
	private Date expirationDate;
	private Integer quantity;
	
}
