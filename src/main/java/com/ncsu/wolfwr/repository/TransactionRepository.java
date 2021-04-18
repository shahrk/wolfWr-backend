package com.ncsu.wolfwr.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	@Query(value="select initial.sales as initial_sales, final.sales as final_sales,(final.sales - initial.sales)*100/initial.sales as growth from  ( SELECT sum(t.total_price) as sales from transaction t where t.purchase_date <= :startDate and t.store_id = :storeId ) as initial,  (select sum(t.total_price) as sales from transaction t where t.purchase_date <= :endDate and t.store_id = :storeId) as final", nativeQuery=true)
	List<Object> getSalesReportStore(@Param("storeId") Integer storeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

