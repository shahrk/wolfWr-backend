package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.SignupInformation;

@Repository
public interface SignupRepository extends JpaRepository<SignupInformation, Integer>{
	List<SignupInformation> findAll();
	
	Optional<SignupInformation> findById(Integer signupId);
}
