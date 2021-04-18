package com.ncsu.wolfwr.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentProductId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer shipmentId;
	private Integer productId;
	
	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		ShipmentProductId obj = (ShipmentProductId) o;
		return Objects.equals( shipmentId, obj.shipmentId ) &&
				Objects.equals( productId, obj.productId );
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(shipmentId, productId);
	}
}
