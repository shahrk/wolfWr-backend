package com.ncsu.wolfwr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncsu.wolfwr.entity.MembershipTier;
import com.ncsu.wolfwr.service.MembershipTierService;

@RestController
@RequestMapping("/membershipTier")
public class MembershipTierController {

	@Autowired
	MembershipTierService membershipTierService;
	
	@GetMapping("/{id}")
	public MembershipTier getMembershipTierById(@PathVariable("id") Integer membershipTierId) {
		return membershipTierService.getMembershipTierById(membershipTierId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createMembershipTier(@RequestBody MembershipTier membershipTier) {
		return new ResponseEntity<Integer>(membershipTierService.createMembershipTier(membershipTier), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateMembershipTier(@PathVariable("id") Integer membershipTierId, @RequestBody MembershipTier membershipTier) {
		membershipTierService.updateMembershipTier(membershipTierId, membershipTier);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMembershipTier(@PathVariable("id") Integer membershipTierId) {
		membershipTierService.deleteMembershipTier(membershipTierId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
