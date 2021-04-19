package com.ncsu.wolfwr.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ncsu.wolfwr.entity.SignupInformation;

@Repository
public interface SignupRepository extends JpaRepository<SignupInformation, Integer>{
	List<SignupInformation> findAll();
	
	Optional<SignupInformation> findById(Integer signupId);
	
	
	
	@Query(value="select initial.customer_count as initial_customer_count, final.customer_count as final_customer_count, "
			+ "	(final.customer_count - initial.customer_count)*100/initial.customer_count as growth "
			+ "	from "
			+ "	(select count(*) as customer_count from signup_information s where s.signup_date <= :startDate and coalesce(s.end_date,'2100-01-01') >= :startDate) as initial, "
			+ "	(select count(*) as customer_count from signup_information s where s.signup_date <= :endDate and coalesce(s.end_date,'2100-01-01') >= :startDate) as final", nativeQuery=true)
	List<Map<Object, Object>> getCustomerGrowth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE SignupInformation s SET s.endDate= CURRENT_DATE  WHERE s.customerId = :customerId and s.endDate = null")
	void cancelMembership(@Param(value = "customerId") Integer customerId);
}
