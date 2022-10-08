package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	List<Product> findAllByCategory_id(int id);
}
