package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer>{

	List<Staff> findAll();
	
	Optional<Staff> findById(Integer staffId);
	
	@Query("Select s from Staff s where s.storeId = :storeId and s.jobTitle = 'billing_operator'")
	List<Staff> getBillingOperatorByStore(@Param("storeId") Integer storeId);
}
