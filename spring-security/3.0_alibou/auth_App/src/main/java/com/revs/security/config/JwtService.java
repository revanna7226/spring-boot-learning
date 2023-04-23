package com.revs.security.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final String SECRET_KEY = "3F4428472B4B6250655367566B5970337336763979244226452948404D635166";

	public String extractUsername(String jwt) {
		return this.extractClaim(jwt, Claims::getSubject);
	}
	
	public String generateToken(UserDetails userDetails) {
		return this.generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(
			Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*5))
				.signWith(this.getSigninKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
		final Claims claims = this.extractAllClaims(jwt);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String jwt) {
		return Jwts
				.parserBuilder()
				.setSigningKey(this.getSigninKey())
				.build()
				.parseClaimsJws(jwt)
				.getBody();
	}
	
	private Key getSigninKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public boolean isTokenValid(String jwt, UserDetails userDetails) {
		final String username = extractUsername(jwt);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
	}

	private boolean isTokenExpired(String jwt) {
		return extractExpiration(jwt).before(new Date());
	}
	
	private Date extractExpiration(String jwt) {
		return extractClaim(jwt, Claims::getExpiration);
	}
}
