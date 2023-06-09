package com.revs.security.javatechie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revs.security.javatechie.dto.AuthRequest;
import com.revs.security.javatechie.dto.Product;
import com.revs.security.javatechie.entity.User;
import com.revs.security.javatechie.service.JwtService;
import com.revs.security.javatechie.service.ProductServie;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductServie productService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to Security App! But, This is not secure!";
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Product> getAllProducts() {
		return this.productService.getProducts();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
	public Product getProductById(@PathVariable int id) {
		return this.productService.getProduct(id);
	}
	
	@PostMapping("/new")
	public String addNewUser(@RequestBody User user) {
		return this.productService.addUser(user);
	}
	
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		if(!auth.isAuthenticated()) {
			throw new UsernameNotFoundException("No User with Username" + authRequest.getUsername());
		}
		
		return this.jwtService.generateToken(authRequest.getUsername());
	}
	
}
