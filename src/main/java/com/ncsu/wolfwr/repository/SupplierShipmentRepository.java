package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.SupplierShipment;

@Repository
public interface SupplierShipmentRepository extends JpaRepository<SupplierShipment, Integer>{
	List<SupplierShipment> findAll();
	
	Optional<SupplierShipment> findById(Integer shipmentId);
}
