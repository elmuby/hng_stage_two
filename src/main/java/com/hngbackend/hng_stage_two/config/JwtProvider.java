package com.hngbackend.hng_stage_two.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secret;
	private SecretKey key;

	private void initKey() {
		if (this.key == null) {
			this.key = Keys.hmacShaKeyFor(secret.getBytes());
		}
	}

	public String generateToken(Authentication auth) {
		initKey();
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		String roles = populateAuthorities(authorities);

		String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 600000))
				.claim("email", auth.getName()).claim("authorities", roles).signWith(key).compact();

		return jwt;
	}

	public String getEmailFromJwtToken(String jwt) {
		jwt = jwt.substring(7);
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email = String.valueOf(claims.get("email"));
		return email;
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> auths = new HashSet<>();

		for (GrantedAuthority authority : authorities) {
			auths.add(authority.getAuthority());
		}
		return String.join(",", auths);
	}

}
