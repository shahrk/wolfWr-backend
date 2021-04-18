package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.MembershipTier;
import com.ncsu.wolfwr.repository.MembershipTierRepository;

@Service
public class MembershipTierService {
	MembershipTierRepository membershipTierRepo;
	
	@Autowired
	MembershipTierService(MembershipTierRepository membershipTierRepo) {
		this.membershipTierRepo = membershipTierRepo;
	}
	
	public MembershipTier getMembershipTierById(int membershipTierId) {
		return membershipTierRepo.findById(membershipTierId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createMembershipTier(MembershipTier membershipTier) {
		if (membershipTier.getTierId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		membershipTier = this.membershipTierRepo.save(membershipTier);
		
		return membershipTier.getTierId();
	}
	
	public void updateMembershipTier(int id, MembershipTier membershipTier) {
		if (membershipTier.getTierId() != null && membershipTier.getTierId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.membershipTierRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.membershipTierRepo.save(membershipTier);
	}
	
	public void deleteMembershipTier(int id) {
		this.membershipTierRepo.deleteById(id);
	}
	
	
}
