package com.example.blogforum.security;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
	private final JwtProperties properties;
	public DecodedJWT decode(String token) {
		return JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
				.build()
				.verify(token);
	}
	public DecodedJWT decodeRefreshToken(String token) {
	    return JWT.require(Algorithm.HMAC256(properties.getSecretKeyRefesh()))
	              .build()
	              .verify(token);
	}
}
	