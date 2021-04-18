package com.ncsu.wolfwr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.BillingInfo;

@Repository
public interface BillingInfoRepository extends JpaRepository<BillingInfo, Integer>{

	Optional<BillingInfo> findByShipmentId(Integer shipmentId);
}
