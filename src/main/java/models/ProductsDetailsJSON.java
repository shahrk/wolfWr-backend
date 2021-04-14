package models;

import lombok.Data;

@Data
public class ProductsDetailsJSON {

	private Integer merchandiseId;
	private Integer productId;
	private Float price;
	private Integer quantity;
	private Float totalPrice;
	
}
