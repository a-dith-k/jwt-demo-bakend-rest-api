package com.adith.demo.services.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

	
		private static final String SECRET_KEY="5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


	@Override
	public String generateToken(String username) {
		SecretKey key = Jwts.SIG.HS256.key().build();

		String jwt = Jwts.builder()                     // (1)

				.header()
				.add("type","JWT")// (2) optional
				.keyId("aKeyId")
				.and()

				.subject("Bob")                             // (3) JSON Claims, or
				//.content(aByteArray, "text/plain")        //     any byte[] content, with media type

				.signWith(key)                       // (4) if signing, or
				//.encryptWith(key, keyAlg, encryptionAlg)  //     if encrypting

				.compact();




		Map<String,Object> claims
								=new HashMap<>();
		return 
			createToken(claims, username);
	}

	@Override
	public String createToken(Map<String,Object>claims, String username) {
		
	return Jwts
			.builder()
			.claims(claims)
			.subject(username)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
			.signWith(getSignKey()).compact();

		
	}

	public Key getSignKey(){

		byte[] bytes =Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(bytes);

	}

	@Override
	public String extractUsername(String token) throws JwtException {
			JwtParser parser=Jwts.parser().verifyWith((SecretKey) getSignKey()).build();
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
		JwtParser parser=Jwts.parser().verifyWith((SecretKey) getSignKey()).build();
		Jws<Claims> claims=parser.parseSignedClaims(token);
		return claims.getPayload().getExpiration().before(new Date(System.currentTimeMillis()));
	}


}
