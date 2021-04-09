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

import com.ncsu.wolfwr.entity.Merchandise;
import com.ncsu.wolfwr.service.MerchandiseService;

@RestController
@RequestMapping("/merchandise")
public class MerchandiseController {
	@Autowired
	MerchandiseService merchandiseService;
	
	@GetMapping("/{id}")
	public Merchandise getMerchandiseById(@PathVariable("id") Integer merchandiseId) {
		return merchandiseService.getMerchandiseById(merchandiseId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createMerchandise(@RequestBody Merchandise merchandise) {
		return new ResponseEntity<Integer>(merchandiseService.createMerchandise(merchandise), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateMerchandise(@PathVariable("id") Integer merchandiseId, @RequestBody Merchandise merchandise) {
		merchandiseService.updateMerchandise(merchandiseId, merchandise);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMerchandise(@PathVariable("id") Integer merchandiseId) {
		merchandiseService.deleteMerchandise(merchandiseId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
