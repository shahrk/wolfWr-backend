package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Merchandise;

@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Integer>{
	List<Merchandise> findAll();
	
	Optional<Merchandise> findById(Integer merchandiseId);
}
