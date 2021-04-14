package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.TransactionContainsMerchandise;
import com.ncsu.wolfwr.entity.TransactionMerchandiseId;

@Repository
public interface TransactionContainsMerchandiseRepository  extends JpaRepository<TransactionContainsMerchandise, TransactionMerchandiseId>{
	
	List<TransactionContainsMerchandise> findAll();
	
	Optional<TransactionContainsMerchandise> findById(TransactionMerchandiseId transactionMerchandiseId);

}
