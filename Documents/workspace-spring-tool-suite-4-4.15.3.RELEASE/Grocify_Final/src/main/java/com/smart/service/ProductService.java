package com.smart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.ProductRepo;
import com.smart.entities.Product;

@Service
public class ProductService {
	@Autowired
	private ProductRepo productRepo;
	
	public List<Product> getAllProduct(){
		return productRepo.findAll();
	}

	public void addProduct(Product product) {
		productRepo.save(product);
	}
	
	public void removeProductById(long id) {
		productRepo.deleteById(id);
	}
	
	public Optional<Product> getProductById(long id){
		return productRepo.findById(id);
	}
	
	public List<Product> getAllProductsByCategory(int id){
		return productRepo.findAllByCategory_id(id);
	}
}
