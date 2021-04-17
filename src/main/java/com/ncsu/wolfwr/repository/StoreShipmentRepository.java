package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.StoreShipment;

@Repository
public interface StoreShipmentRepository extends JpaRepository<StoreShipment, Integer>{
	List<StoreShipment> findAll();
	
	Optional<StoreShipment> findById(Integer shipmentId);
}
