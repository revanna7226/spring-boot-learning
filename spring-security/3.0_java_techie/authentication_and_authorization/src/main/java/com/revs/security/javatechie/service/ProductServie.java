package com.revs.security.javatechie.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revs.security.javatechie.dto.Product;
import com.revs.security.javatechie.entity.User;
import com.revs.security.javatechie.repository.IUserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ProductServie {
	
	private List<Product> productList;
	
	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostConstruct
	public void loadProductsFromDB() {
		productList = IntStream.rangeClosed(1, 50)
				.mapToObj(num -> Product.builder()
						.id(num)
						.name("Product " + num)
						.quantity(new Random().nextInt(10))
						.price(new Random().nextDouble(1000))
						.build())
				.collect(Collectors.toList());
	}

	public List<Product> getProducts() {
		return this.productList;
	}

	public Product getProduct(int id) {
		return productList.stream()
				.filter(p -> p.getId() == id)
				.findAny()
				.orElseThrow(() -> new RuntimeException("No Product for ID " + id));
	}

	public String addUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		this.userRepo.save(user);
		return "User Added";
	}

}
