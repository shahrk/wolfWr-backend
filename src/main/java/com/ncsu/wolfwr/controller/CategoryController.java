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

import com.ncsu.wolfwr.entity.Category;
import com.ncsu.wolfwr.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/{id}")
	public Category getCategoryById(@PathVariable("id") Integer categoryId) {
		return categoryService.getCategoryById(categoryId);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> createCategory(@RequestBody Category category) {
		return new ResponseEntity<Integer>(categoryService.createCategory(category), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public void updateCategory(@PathVariable("id") Integer categoryId, @RequestBody Category category) {
		categoryService.updateCategory(categoryId, category);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
