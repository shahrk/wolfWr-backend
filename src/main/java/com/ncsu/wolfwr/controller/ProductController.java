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

import com.ncsu.wolfwr.entity.Product;
import com.ncsu.wolfwr.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable("id") Integer productId) {
		return productService.getProductById(productId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createProduct(@RequestBody Product product) {
		return new ResponseEntity<Integer>(productService.createProduct(product), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateProduct(@PathVariable("id") Integer productId, @RequestBody Product product) {
		productService.updateProduct(productId, product);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
