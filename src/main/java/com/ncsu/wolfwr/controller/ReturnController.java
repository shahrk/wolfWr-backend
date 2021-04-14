package com.ncsu.wolfwr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.service.ReturnService;

import models.ReturnPOJO;

@RestController
@RequestMapping("/return")
public class ReturnController {
	
	@Autowired
	ReturnService returnService;
	
	@GetMapping("/{id}")
	public ReturnPOJO getReturn(@PathVariable("id") Integer returnId) {
		return returnService.getReturn(returnId);
	}

	@PostMapping("/")
	public ResponseEntity<Integer> createReturn(@RequestBody ReturnPOJO returnPojo) {
		return new ResponseEntity<Integer>(returnService.createReturn(returnPojo), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateReturn(@PathVariable("id") Integer returnId, @RequestBody ReturnPOJO returnPojo) {
		if (returnPojo.getReturns() == null || returnId != returnPojo.getReturns().getReturnId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		returnService.updateReturn(returnPojo);
	}
	
}
