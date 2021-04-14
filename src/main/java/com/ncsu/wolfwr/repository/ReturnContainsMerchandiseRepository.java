package com.ncsu.wolfwr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.ReturnContainsMerchandise;
import com.ncsu.wolfwr.entity.ReturnMerchandiseId;

@Repository
public interface ReturnContainsMerchandiseRepository extends JpaRepository<ReturnContainsMerchandise, ReturnMerchandiseId> {

	@Query("select quantity from ReturnContainsMerchandise rcm where rcm.returnId = :returnId and rcm.merchandiseId = :merchId")
	public int getQuantityOfReturnedMerch(@Param("returnId") int returnId, @Param("merchId") int merchId);
	
	public List<ReturnContainsMerchandise> findByReturnId(Integer returnId);
}
