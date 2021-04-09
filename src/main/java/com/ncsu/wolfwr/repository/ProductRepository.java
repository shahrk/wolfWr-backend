package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	List<Product> findAll();
	
	Optional<Product> findById(Integer productId);
}
