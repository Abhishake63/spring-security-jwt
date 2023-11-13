package com.examportal.security;

import java.security.Key;
import java.util.Date;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtGenerator {

	public String generateToken(Authentication authentication, String userType) {

		String username= authentication.getName();
		Date currentDate = new Date();
		Date expiryDate = new Date(currentDate.getTime()+ SecurityConstants.JWT_EXPIRATION);

		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(currentDate)
				.setExpiration(expiryDate)
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.claim("usertype", userType)
				.compact();
		return token;
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parserBuilder()
		        .setSigningKey(getSignKey())
		        .build()
		        .parseClaimsJws(token)
		        .getBody();

		return claims.getSubject();
	}

	public String getUserTypeFromJWT(String token) {
		Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

		return claims.get("usertype").toString();
	}

	public boolean validateToken(String token) {
		try {
    		    Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
            return true;
		}
		catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT token is not valid " + token);
		}
	}

	private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SecurityConstants.JWT_SECERT);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
