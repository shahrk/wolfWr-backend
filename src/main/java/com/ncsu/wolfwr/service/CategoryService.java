package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Category;
import com.ncsu.wolfwr.repository.CategoryRepository;

@Service
public class CategoryService {
	CategoryRepository categoryRepo;
	
	@Autowired
	CategoryService(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}
	
	public Category getCategoryById(Integer categoryId) {
		return categoryRepo.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createCategory(Category category) {
		if (category.getProductCategoryId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		category = this.categoryRepo.save(category);
		
		return category.getProductCategoryId();
	}
	
	public void updateCategory(Integer id, Category category) {
		if (category.getProductCategoryId() != null && category.getProductCategoryId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.categoryRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.categoryRepo.save(category);
	}
	
	public void deleteCategory(Integer id) {
		this.categoryRepo.deleteById(id);
	}
}
