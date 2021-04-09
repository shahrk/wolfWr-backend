package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Product;
import com.ncsu.wolfwr.repository.ProductRepository;

@Service
public class ProductService {
	ProductRepository productRepo;
	
	@Autowired
	ProductService(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}
	
	public Product getProductById(Integer productId) {
		return productRepo.findById(productId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createProduct(Product product) {
		if (product.getProductId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		product = this.productRepo.save(product);
		
		return product.getProductId();
	}
	
	public void updateProduct(Integer id, Product product) {
		if (product.getProductId() != null && product.getProductId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.productRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.productRepo.save(product);
	}
	
	public void deleteProduct(Integer id) {
		this.productRepo.deleteById(id);
	}
}
