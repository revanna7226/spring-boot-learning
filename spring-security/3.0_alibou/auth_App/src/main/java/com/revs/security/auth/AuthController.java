package com.revs.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revs.security.auth.AuthReponse.AuthReponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest regRequest) {
		String token = this.authService.register(regRequest);
		if (token != null) {
			return ResponseEntity.ok(AuthReponse.builder().token(token).build());
		} else {
			return ResponseEntity.badRequest().body("USer Exists");
		}
		
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
		return ResponseEntity.ok(authService.authenticate(authRequest));
	}
	
}