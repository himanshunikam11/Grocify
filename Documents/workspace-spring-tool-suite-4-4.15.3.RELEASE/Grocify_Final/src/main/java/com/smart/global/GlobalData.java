package com.smart.global;

import java.util.ArrayList;
import java.util.List;

import com.smart.entities.Product;

public class GlobalData {
	public static List<Product> cart;
	static {
		cart = new ArrayList<Product>();
	}
	

}
