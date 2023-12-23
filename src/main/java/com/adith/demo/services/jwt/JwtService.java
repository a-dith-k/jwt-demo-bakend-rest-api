package com.adith.demo.services.jwt;

import java.security.Key;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

	
	public String generateToken(String username);

	public String createToken(Map<String, Object> claims, String username);

	public Key getSignKey();

    String extractUsername(String token);

	boolean validateToken(String token, UserDetails userDetails);

	boolean isTokenExpired(String token);
} 