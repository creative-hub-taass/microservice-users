package com.creativehub.backend.services.impl;

import com.creativehub.backend.services.JwtUtil;
import com.creativehub.backend.util.TokenData;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JwtUtilImpl implements JwtUtil {
	private static final Duration expireToken = Duration.ofMinutes(10);
	private static final Duration expireRefreshToken = Duration.ofDays(7);
	@Value("${security.jwt.secret}")
	private String secret;

	@Override
	public String createJWT(TokenData tokenData, Duration expireToken) throws JOSEException {
		JWTClaimsSet claims = new JWTClaimsSet.Builder()
				.subject(tokenData.getSubject())
				.claim("roles", tokenData.getRoles())
				.expirationTime(Date.from(Instant.now().plus(expireToken)))
				.issueTime(new Date())
				.build();
		Payload payload = new Payload(claims.toJSONObject());
		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), payload);
		jwsObject.sign(new MACSigner(secret));
		return jwsObject.serialize();
	}

	@Override
	public String createAccessToken(TokenData tokenData) {
		try {
			return createJWT(tokenData, expireToken);
		} catch (JOSEException e) {
			log.debug("Error creating JWT", e);
			return null;
		}
	}

	@Override
	public String createRefreshToken(TokenData tokenData) {
		try {
			return createJWT(tokenData, expireRefreshToken);
		} catch (JOSEException e) {
			log.debug("Error creating refresh JWT", e);
			return null;
		}
	}

	@Override
	public TokenData parseToken(String token) {
		try {
			byte[] secretKey = secret.getBytes();
			SignedJWT signedJWT = SignedJWT.parse(token);
			boolean verified = signedJWT.verify(new MACVerifier(secretKey));
			if (verified) {
				ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
				JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.HS256, new ImmutableSecret<>(secretKey));
				jwtProcessor.setJWSKeySelector(keySelector);
				jwtProcessor.process(signedJWT, null);
				JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
				String subject = claims.getSubject();
				List<String> roles = claims.getStringListClaim("roles");
				return new TokenData(subject, roles);
			}
		} catch (BadJOSEException | JOSEException | ParseException e) {
			log.debug("Error auth token: " + token, e);
		}
		return null;
	}
}
