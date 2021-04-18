package com.ncsu.wolfwr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Merchandise;
import com.ncsu.wolfwr.repository.MerchandiseRepository;

@Service
public class MerchandiseService {
	MerchandiseRepository merchandiseRepo;
	
	@Autowired
	MerchandiseService(MerchandiseRepository merchandiseRepo) {
		this.merchandiseRepo = merchandiseRepo;
	}
	
	public Merchandise getMerchandiseById(int merchandiseId) {
		return merchandiseRepo.findById(merchandiseId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createMerchandise(Merchandise merchandise) {
		if (merchandise.getMerchandiseId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		merchandise = this.merchandiseRepo.save(merchandise);
		
		return merchandise.getMerchandiseId();
	}
	
	public void updateMerchandise(int id, Merchandise merchandise) {
		if (merchandise.getMerchandiseId() != null && merchandise.getMerchandiseId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.merchandiseRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.merchandiseRepo.save(merchandise);
	}
	
	public void deleteMerchandise(int id) {
		this.merchandiseRepo.deleteById(id);
	}
	
	
}
