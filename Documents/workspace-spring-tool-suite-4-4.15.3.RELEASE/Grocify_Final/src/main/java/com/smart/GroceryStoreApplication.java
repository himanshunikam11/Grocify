package com.smart;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GroceryStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryStoreApplication.class, args);
	}
	
	
	@Bean
	public ModelMapper mapping() {
		return new ModelMapper();
	}

}
