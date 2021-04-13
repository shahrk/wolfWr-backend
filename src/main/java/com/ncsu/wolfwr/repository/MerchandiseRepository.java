package com.ncsu.wolfwr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ncsu.wolfwr.entity.Merchandise;

@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Integer>{
	List<Merchandise> findAll();
	
	Optional<Merchandise> findById(Integer merchandiseId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Merchandise m SET m.quantityInStock= m.quantityInStock - :quantity  WHERE m.storeId= :storeId and m.productId = :productId")
	void updateMerchandiseOnTransaction(@Param(value = "quantity") Integer quantity, @Param(value = "storeId") Integer storeId, @Param(value = "productId") Integer productId);
	
	@Query("SELECT m FROM Merchandise m WHERE m.storeId = ?1 and m.productId = ?2")
	Merchandise findMerchandiseByProductStoreId(Integer storeId, Integer productId);
	
}
