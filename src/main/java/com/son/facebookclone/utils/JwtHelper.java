package com.son.facebookclone.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {
	
	private String key = "1234";
	private String prefix = "Bearer ";
	
	public String generateToken(String email) {
		return Jwts.builder()
					.setIssuer(email)
					.setExpiration(new Date(System.currentTimeMillis() + 3600000))
					.setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, key)
					.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String getToken(HttpServletRequest request) {
		String jwt = request.getHeader("Authorization");
		
		if (jwt == null)
			return jwt;
		
		return jwt.substring(prefix.length(), jwt.length());
	}
	
	public String getUsername(String token) {
		return Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token)
				.getBody()
				.getIssuer();
	}
}
