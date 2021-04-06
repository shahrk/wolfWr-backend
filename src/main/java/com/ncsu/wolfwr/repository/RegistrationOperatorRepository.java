package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.RegistrationOperator;

@Repository
public interface RegistrationOperatorRepository extends JpaRepository<RegistrationOperator, Integer>{
	List<RegistrationOperator> findAll();
	
	Optional<RegistrationOperator> findById(Integer staffId);
}
