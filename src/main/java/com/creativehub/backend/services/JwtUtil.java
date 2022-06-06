package com.creativehub.backend.services;

import com.creativehub.backend.util.TokenData;
import com.nimbusds.jose.JOSEException;

import java.time.Duration;

public interface JwtUtil {
	String createJWT(TokenData tokenData, Duration expireToken) throws JOSEException;

	String createAccessToken(TokenData tokenData);

	String createRefreshToken(TokenData tokenData);

	TokenData parseToken(String token);
}
