package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.TransactionContainsMerchandise;

@Repository
public interface TransactionContainsMerchandiseRepository  extends JpaRepository<TransactionContainsMerchandise, Integer>{
	
	List<TransactionContainsMerchandise> findAll();
	
	Optional<TransactionContainsMerchandise> findById(Integer transactionContainsMerchandiseId);

}
