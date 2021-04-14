package com.ncsu.wolfwr.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReturnMerchandiseId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer returnId;
	private Integer merchandiseId;
	
	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		ReturnMerchandiseId obj = (ReturnMerchandiseId) o;
		return Objects.equals( returnId, obj.returnId ) &&
				Objects.equals( merchandiseId, obj.merchandiseId );
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(returnId, merchandiseId);
	}
}
