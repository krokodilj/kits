package com.timsedam.buildingmanagement.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JsonWebToken {


	@Value("myXAuthSecret")
	private String signingKey;
	
	@Value("18000") //in seconds (5 hours)
	private Long expiration;
	
	public String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(signingKey)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	
	public Date getExpirationDateFromToken(String token) {
		Date expirationDate;
		try {
			Claims claims = getClaimsFromToken(token);
			expirationDate = claims.getExpiration();
		} catch (Exception e) {
			expirationDate = null;
		}
		return expirationDate;
	}

	public List<String> getRolesFromToken(String token){
		List<String> roles;
		try {
			Claims claims = getClaimsFromToken(token);
			roles = (List<String>) claims.get("roles");
		} catch (Exception e) {
			roles = new ArrayList<String>();
		}
		return roles;
	}
	
	private boolean isTokenExpired(String token) {
	    Date expirationDate = getExpirationDateFromToken(token);
	    return expirationDate.before(new Date(System.currentTimeMillis()));
	  }
	
	public boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", userDetails.getUsername());
		claims.put("created", new Date(System.currentTimeMillis()));
		return Jwts.builder().setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, signingKey).compact();
	}

	public String generateToken(String username, List<String> roles){
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", username);
		claims.put("created", new Date(System.currentTimeMillis()));
		claims.put("roles", roles);
		return Jwts.builder().setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, signingKey).compact();
	}

}
