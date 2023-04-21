package com.revs.security.javatechie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.revs.security.javatechie.service.UserInfoUserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	/*
	 * Authentication Feature
	 */
//    @Bean
//    UserDetailsService userDetailService(PasswordEncoder encoder) {
//        UserDetails admin = User.withUsername("revanna")
//        		.password(encoder.encode("revanna") )
//        		.roles("ADMIN").build();
//        UserDetails user = User.withUsername("pushpa")
//        		.password(encoder.encode("pushpa"))
//        		.roles("USER").build();
//        return new InMemoryUserDetailsManager(Arrays.asList(admin, user));
//    }
	
	@Bean
	UserDetailsService userDetailService() {
		return new UserInfoUserDetailService();
	}
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	return http.csrf().disable()
    			.authorizeHttpRequests().requestMatchers("/products/welcome", "/products/new").permitAll()
    			.and()
    			.authorizeHttpRequests().requestMatchers("/products/**").authenticated()
    			.and().formLogin().and().build(); // Form Login
    			//.and().httpBasic().and().build(); // Http Basic Login, Form pops up to login
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
    	daoAuthProvider.setUserDetailsService(this.userDetailService());
    	daoAuthProvider.setPasswordEncoder(this.passwordEncoder());
    	return daoAuthProvider;
    }
	
}
