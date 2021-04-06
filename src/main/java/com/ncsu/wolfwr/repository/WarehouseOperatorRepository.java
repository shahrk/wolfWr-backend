package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.WarehouseOperator;


@Repository
public interface WarehouseOperatorRepository extends JpaRepository<WarehouseOperator, Integer>{
	List<WarehouseOperator> findAll();
	
	Optional<WarehouseOperator> findById(Integer staffId);
}
