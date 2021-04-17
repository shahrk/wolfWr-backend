package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.ShipmentContainsProduct;
import com.ncsu.wolfwr.entity.ShipmentProductId;

@Repository
public interface ShipmentContainsProductRepository  extends JpaRepository<ShipmentContainsProduct, ShipmentProductId>{
	
	List<ShipmentContainsProduct> findAll();
	
	Optional<ShipmentContainsProduct> findById(ShipmentProductId shipmentProductId);
	
	List<ShipmentContainsProduct> findByShipmentId(Integer shipmentId);
}
