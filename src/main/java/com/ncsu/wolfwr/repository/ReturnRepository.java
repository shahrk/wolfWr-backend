package com.ncsu.wolfwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.Return;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Integer>{

}
