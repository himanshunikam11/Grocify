package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.entities.Product;
import com.smart.global.GlobalData;
import com.smart.service.CategoryService;
import com.smart.service.ProductService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping("/index")
	public String dashboard(Model model)
	{
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		
		return "index";
	}
	
	@GetMapping("/categoryVegetable/{id}")
	public String catVeggie(Model model, @PathVariable int id)
	{
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductsByCategory(id));
		return "catagory/Category_Vegetable";
	}
	
	@RequestMapping("/categoryCapsicum/{id}")
	public String catCapsicum(Model model, @PathVariable int id)
	{
		model.addAttribute("product", productService.getProductById(id).get());
		
		return "normal/capsicum";
	}
	
	//creating order payment
	
	
	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder() {
		
		System.out.println("order function executed");
		return "done";
	}
}

