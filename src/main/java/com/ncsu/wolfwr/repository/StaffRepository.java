package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer>{

	List<Staff> findAll();
	
	Optional<Staff> findById(Integer staffId);
}
