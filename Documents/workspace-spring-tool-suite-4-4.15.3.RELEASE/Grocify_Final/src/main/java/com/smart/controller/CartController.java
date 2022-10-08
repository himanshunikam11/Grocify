 package com.smart.controller;

import java.security.Principal;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.AddressRepository;
import com.smart.dao.MyOrderRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Address;
import com.smart.entities.MyOrder;
import com.smart.entities.Product;
import com.smart.entities.User;
import com.smart.global.GlobalData;
import com.smart.service.ProductService;
import com.razorpay.*;

@Controller
public class CartController {
	@Autowired
	ProductService productService;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MyOrderRepository myOrderRepository;
	


	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable int id) {
		GlobalData.cart.add(productService.getProductById(id).get());
		return "index";
	}

	@GetMapping("/cart")
	public String cartGet(Model model) {

		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart", GlobalData.cart);
		return "cart";
	}

	@GetMapping("/cart/removeItem/{index}")
	public String cartItemRemove(@PathVariable int index) {
		GlobalData.cart.remove(index);
		return "redirect:/cart";
	}

	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		return "checkout";
	}

	@PostMapping("/payNow")
	public String processAddForm(@ModelAttribute Address address, Principal principal, Model model) {

		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());

		String name = principal.getName();
		User user = this.userRepository.getUserByName(name);

		address.setUser(user);

		user.getAddress().add(address);

		this.addressRepository.save(address);

		System.out.println("DATA" + address);

		return "payment";
	}

	// creating order payment
	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody Map<String, Object> data, Principal principal) throws Exception {

//		System.out.println("order function executed");
		System.out.println(data);
		
		int amt = Integer.parseInt(data.get("amount").toString());
		
		RazorpayClient client = new RazorpayClient("rzp_test_bqeEqIY5RTum0f", "7kyoLnG5FgR19l35zWXiH8Jk");
		
		JSONObject object = new JSONObject();
		object.put("amount", amt*100);
		object.put("currency", "INR");
	
		
		Order order = client.Orders.create(object);
		System.out.println(order);
		
		//order store in database
	   MyOrder myOrder = new MyOrder();
		myOrder.setAmount(order.get("amount"));
		myOrder.setOrderId(order.get("id"));
		myOrder.setPaymentId(null);
		myOrder.setStatus("created");
		myOrder.setUser(this.userRepository.getUserByName(principal.getName()));
	    
		this.myOrderRepository.save(myOrder);
		
		return order.toString();
	}
	
	
	@PostMapping("update_order")
	public ResponseEntity<?>updateOrder(@RequestBody Map<String, Object>data){
		
	MyOrder myorder=this.myOrderRepository.findByOrderId(data.get("order_id").toString());
	myorder.setPaymentId(data.get("payment_id").toString());
	myorder.setStatus(data.get("status").toString());
	this.myOrderRepository.save(myorder);
		return ResponseEntity.ok("Success");
	}
}
