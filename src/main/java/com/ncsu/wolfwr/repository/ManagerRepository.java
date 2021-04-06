package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Cashier;
import com.ncsu.wolfwr.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{

	List<Manager> findAll();
	
	Optional<Manager> findById(Integer staffId);
}
