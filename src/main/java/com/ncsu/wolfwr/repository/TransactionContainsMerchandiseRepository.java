package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.TransactionContainsMerchandise;
import com.ncsu.wolfwr.entity.TransactionMerchandiseId;

@Repository
public interface TransactionContainsMerchandiseRepository  extends JpaRepository<TransactionContainsMerchandise, TransactionMerchandiseId>{
	
	List<TransactionContainsMerchandise> findAll();
	
	Optional<TransactionContainsMerchandise> findById(TransactionMerchandiseId transactionMerchandiseId);
	
	List<TransactionContainsMerchandise> findByTransactionId(Integer transactionMerchandiseId);

	@Query("select merchandiseId from TransactionContainsMerchandise tcm where tcm.transactionId = :transaction_id")
	List<Integer> getMerchandiseByTransaction(@Param("transaction_id") int transactionId);
	
	@Query("select tcm from TransactionContainsMerchandise tcm where tcm.transactionId = :transaction_id and tcm.merchandiseId = :merchandise_id")
	TransactionContainsMerchandise getMerchandiseByIds(@Param("transaction_id") int transactionId,@Param("merchandise_id") int merchandiseId);
}
