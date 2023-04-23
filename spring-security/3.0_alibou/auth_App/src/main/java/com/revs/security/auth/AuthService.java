package com.revs.security.auth;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revs.security.config.JwtService;
import com.revs.security.user.Role;
import com.revs.security.user.User;
import com.revs.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepo;
	
	private final PasswordEncoder encoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authManager;
	
	public String register(RegisterRequest regRequest) {
		// TODO Auto-generated method stub
		User user = User.builder()
				.firstname(regRequest.getFirstname())
				.lastname(regRequest.getLastname())
				.email(regRequest.getEmail())
				.password(encoder.encode(regRequest.getPassword()))
				.role(Role.USER)
				.build();
		
		Optional<User> userFromDB = userRepo.findByEmail(regRequest.getEmail());
		
		if (userFromDB.isPresent()) {
			return null;
		}
		userRepo.save(user);
		String jwt = jwtService.generateToken(user);
		return jwt;
	}

	public AuthReponse authenticate(AuthRequest authRequest) {
		// TODO Auto-generated method stub
		authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		User user = userRepo.findByEmail(authRequest.getEmail())
				.orElseThrow();
		String jwt = jwtService.generateToken(user);
		return AuthReponse.builder().token(jwt).build();
	}

}
