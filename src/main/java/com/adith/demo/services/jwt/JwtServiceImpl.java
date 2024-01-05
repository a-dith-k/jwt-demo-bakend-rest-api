package com.adith.demo.services.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.adith.demo.entities.UserEntity;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

	Instant now= Instant.now();

	private static final long EXPIRATION_TIME=1000*60*60*24*5L;
	private static final SecretKey key = Jwts.SIG.HS384.key().build();


	@Override
	public String generateToken(UserDetails userDetails) {

		Map<String,Object> claims
								=new HashMap<>();
		boolean isAdmin=false;
		if(userDetails.getAuthorities()!=null&&userDetails.getAuthorities().contains("ADMIN"))
			 isAdmin=true;

		return 
			createToken(claims, userDetails.getUsername(),isAdmin);
	}

	@Override
	public String createToken(Map<String,Object>claims, String username,boolean isAdmin) {
		
	return Jwts
			.builder()
			.claims(claims)
			.subject(username)
			.claim("admin",isAdmin)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
			.signWith(key).compact();
		
	}

	public Key getSignKey(){
		return key;

//		byte[] bytes =Decoders.BASE64.decode(SECRET_KEY);
//		return key;

	}

	@Override
	public String extractUsername(String token) throws JwtException {
			JwtParser parser=Jwts.parser().verifyWith(key).build();
			Jws<Claims> claims=parser.parseSignedClaims(token);
			return claims.getPayload().getSubject();

	}

	@Override
	public boolean validateToken(String token, UserDetails userDetails) {
		boolean isValid=false;
		try{
			isValid=extractUsername(token).equals(userDetails.getUsername())&&!isTokenExpired(token);
		}catch (Exception e){
			log.error(e.getMessage());
		}
		return isValid;
	}

	public boolean isTokenExpired(String token) {
		JwtParser parser=Jwts.parser().verifyWith(key).build();
		Jws<Claims> claims=parser.parseSignedClaims(token);
		return claims.getPayload().getExpiration().before(new Date(System.currentTimeMillis()));
	}


}
