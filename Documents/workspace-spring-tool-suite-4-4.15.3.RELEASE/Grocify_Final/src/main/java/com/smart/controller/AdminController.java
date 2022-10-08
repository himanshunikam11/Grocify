package com.smart.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dto.ProductDTO;
import com.smart.entities.Category;
import com.smart.entities.Product;
import com.smart.service.CategoryService;
import com.smart.service.ProductService;

@Controller
public class AdminController {

	public String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/index-images";
	
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	@GetMapping("/admin/index")
	public String adminHome() {
		return "admin/Admin_home";
	}

	@GetMapping("/admin/categories")
	public String getCat(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "admin/categories";
	}

	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model model) {
		model.addAttribute("category", new Category());
		return "admin/categoriesAdd";
	}

	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute Category category, @RequestParam("profileImage") MultipartFile file)
			throws IOException {

		if (file.isEmpty()) {

			System.out.println("file not found");

		} else {
			category.setImageNameCat(file.getOriginalFilename());

			File saveFile = new ClassPathResource("static/index-images").getFile();

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			System.out.println("Image uploaded");
		}

		categoryService.addCategory(category);

		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model) {

		Optional<Category> category = categoryService.getCategoryById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "admin/categoriesAdd";
		} else {
			return "404";
		}
	}

	// product section
	@GetMapping("/admin/products")
	public String getPro(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "admin/products";
	}

	@GetMapping("/admin/products/add")
	public String addPro(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "admin/productsAdd";
	}

	@PostMapping("/admin/products/add")
	public String postProAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName)
			throws IOException {
		
		Product product= this.modelMapper.map(productDTO,Product.class);
		/*Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory((Category) categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());*/
		String imageUUID;
		if (!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		} else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		productService.addProduct(product);

		return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/delete/{id}")
	public String deletePro(@PathVariable long id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/update/{id}")
	public String updatePro(@PathVariable long id, Model model) {
		Product product = productService.getProductById(id).get();
		ProductDTO dto=ProductToDto(product);
		
		/*ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());*/

		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", dto);

		return "admin/productsAdd";
	}
	
	
	//Methods added to reduce the code of  Mapping of objects
	public Product DtoToProduct(ProductDTO dto) {
		Product product= this.modelMapper.map(dto, Product.class);
		
		return product;
		
	}
	
	public ProductDTO ProductToDto(Product product) {
		ProductDTO dto= this.modelMapper.map(product, ProductDTO.class);
		
		return dto;
		
	}
}
