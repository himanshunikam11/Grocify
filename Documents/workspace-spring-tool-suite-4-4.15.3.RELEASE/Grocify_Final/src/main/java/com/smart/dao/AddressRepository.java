package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

}
