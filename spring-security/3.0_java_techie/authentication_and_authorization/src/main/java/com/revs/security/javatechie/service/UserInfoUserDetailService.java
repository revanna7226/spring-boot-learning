package com.revs.security.javatechie.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revs.security.javatechie.entity.User;
import com.revs.security.javatechie.repository.IUserRepository;

@Service
public class UserInfoUserDetailService implements UserDetailsService {
	
	@Autowired
	private IUserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userInfo = this.userRepo.findByName(username);
		
		return userInfo.map(UserInfoUserDetail::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
	}

}
