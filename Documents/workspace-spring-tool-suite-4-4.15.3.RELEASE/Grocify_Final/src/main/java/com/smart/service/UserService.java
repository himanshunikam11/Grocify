package com.smart.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.smart.dao.UserRepository;

public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	
	public Optional getUserById(int id) {
		return userRepository.findById(id);
	}

}
