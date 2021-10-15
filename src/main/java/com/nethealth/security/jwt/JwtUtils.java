package com.nethealth.security.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtils {
	
	
	public String generateToken(User user) {
		List<String> roles = new ArrayList<>();
		user.getAuthorities().forEach(authority->{
			roles.add(authority.getAuthority());
		});
		String jwt = JWT.create().
				withSubject(user.getUsername())
				.withArrayClaim("roles", roles.toArray(new String[roles.size()]))
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtParameters.EXP_TIME))
				.sign(Algorithm.HMAC256(JwtParameters.SECRET));
		
		return jwt;
	}

}
