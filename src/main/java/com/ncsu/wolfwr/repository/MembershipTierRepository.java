package com.ncsu.wolfwr.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.MembershipTier;

@Repository
public interface MembershipTierRepository  extends JpaRepository<MembershipTier, Integer>{
	List<MembershipTier> findAll();
	
	Optional<MembershipTier> findById(Integer membershipId);
	
	@Query("SELECT u FROM MembershipTier u WHERE u.tierName = ?1")
	MembershipTier findMembershipByName(String tier_name);
	
}

