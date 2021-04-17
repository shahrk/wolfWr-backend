package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer>{
	List<Shipment> findAll();
	
	Optional<Shipment> findById(Integer shipmentId);
}
