package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer>{

	List<Store> findAll();
	
	Optional<Store> findById(Integer storeId);
}
