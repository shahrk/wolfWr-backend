package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Supplier;
import com.ncsu.wolfwr.repository.SupplierRepository;

@Service
public class SupplierService {
	SupplierRepository supplierRepo;
	
	@Autowired
	SupplierService(SupplierRepository supplierRepo) {
		this.supplierRepo = supplierRepo;
	}
	
	public Supplier getSupplierById(Integer supplierId) {
		return supplierRepo.findById(supplierId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createSupplier(Supplier supplier) {
		if (supplier.getSupplierId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		supplier = this.supplierRepo.save(supplier);
		
		return supplier.getSupplierId();
	}
	
	public void updateSupplier(Integer id, Supplier supplier) {
		if (supplier.getSupplierId() != null && supplier.getSupplierId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.supplierRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.supplierRepo.save(supplier);
	}
	
	public void deleteSupplier(Integer id) {
		this.supplierRepo.deleteById(id);
	}
}
