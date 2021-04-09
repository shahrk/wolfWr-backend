package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	List<Category> findAll();
	
	Optional<Category> findById(Integer categoryId);

}
