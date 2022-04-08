package com.creativehub.backend.services;

import com.creativehub.backend.util.AuthenticationToken;
import com.nimbusds.jose.JOSEException;

import java.time.Duration;
import java.util.List;

public interface JwtUtil {
	String createJWT(String email, List<String> roles, Duration expireToken) throws JOSEException;

	String createAccessToken(String email, List<String> roles);

	String createRefreshToken(String email, List<String> roles);

	AuthenticationToken parseToken(String token);
}
