package com.smart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id")
	private int id;
	
	private String name;

	private String imageNameCat;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageNameCat() {
		return imageNameCat;
	}

	public void setImageNameCat(String imageNameCat) {
		this.imageNameCat = imageNameCat;
	}
	
	
}
