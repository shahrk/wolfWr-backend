package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Cashier;

@Repository
public interface CashierRepository extends JpaRepository<Cashier, Integer>{

	List<Cashier> findAll();
	
	Optional<Cashier> findById(Integer staffId);
}
