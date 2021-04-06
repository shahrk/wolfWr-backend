package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.BillingOperator;

@Repository
public interface BillingOperatorRepository extends JpaRepository<BillingOperator, Integer>{
	List<BillingOperator> findAll();
	
	Optional<BillingOperator> findById(Integer staffId);
}
