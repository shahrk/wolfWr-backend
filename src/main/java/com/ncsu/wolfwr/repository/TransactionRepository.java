package com.ncsu.wolfwr.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
	
	@Query(value="select purchase_date, sum(total_price) as total_sales from transaction group by purchase_date", nativeQuery=true)
	List<Map<Object, Object>> getSalesReportDay();
	
	@Query(value="select YEAR(purchase_date) as year, MONTH(purchase_date) as month, sum(total_price) as total_sales from transaction group by YEAR(purchase_date), MONTH(purchase_date)", nativeQuery=true)
	List<Map<Object, Object>> getSalesReportMonth();
	
	@Query(value="select YEAR(purchase_date) as year, sum(total_price) as total_sales from transaction group by YEAR(purchase_date)", nativeQuery=true)
	List<Map<Object, Object>> getSalesReportYear();
	
	@Query(value="select coalesce(initial.sales,0) as initial_sales, coalesce(final.sales,0) as final_sales,coalesce((final.sales - initial.sales)*100/initial.sales, 'n/a') as growth from  ( SELECT sum(t.total_price) as sales from transaction t where t.purchase_date <= :startDate and t.store_id = :storeId ) as initial,  (select sum(t.total_price) as sales from transaction t where t.purchase_date <= :endDate and t.store_id = :storeId) as final", nativeQuery=true)
	List<Map<Object, Object>> getSalesReportStore(@Param("storeId") Integer storeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Query(value="select t.customer_id, sum(t.total_price) as total_purchase_amount from transaction t where t.purchase_date between :startDate and :endDate group by t.customer_id having t.customer_id= :customerId", nativeQuery=true)
	List<Map<Object, Object>> getCustomerActivity(@Param("customerId") Integer customerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

