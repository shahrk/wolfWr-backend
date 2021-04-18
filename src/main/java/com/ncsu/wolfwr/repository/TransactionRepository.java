package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	List<Transaction> findAll();
	
	Optional<Transaction> findById(Integer transactionId);
	
	@Query("select t.purchaseDate, sum(t.totalPrice) as total_sales from Transaction t group by t.purchaseDate")
	List<Object> getSalesReportDay();
	
	@Query("select YEAR(t.purchaseDate) as year, MONTH(t.purchaseDate) as month, sum(t.totalPrice) as total_sales from Transaction t group by YEAR(t.purchaseDate), MONTH(t.purchaseDate)")
	List<Object> getSalesReportMonth();
	
	@Query("select YEAR(t.purchaseDate) as year, sum(t.totalPrice) as total_sales from Transaction t group by YEAR(t.purchaseDate)")
	List<Object> getSalesReportYear();
}

