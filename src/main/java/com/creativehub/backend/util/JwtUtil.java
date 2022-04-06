package com.creativehub.backend.util;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class JwtUtil {
	private static final Duration expireToken = Duration.ofMinutes(10);
	private static final Duration expireRefreshToken = Duration.ofDays(7);
	@Value("${security.jwt.secret}")
	private static String SECRET;

	private static String createJWT(String email, List<String> roles, Duration expireToken) throws JOSEException {
		JWTClaimsSet claims = new JWTClaimsSet.Builder()
				.subject(email)
				.claim("roles", roles)
				.expirationTime(Date.from(Instant.now().plus(expireToken)))
				.issueTime(new Date())
				.build();
		Payload payload = new Payload(claims.toJSONObject());
		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), payload);
		jwsObject.sign(new MACSigner(SECRET));
		return jwsObject.serialize();
	}

	public static String createAccessToken(String email, List<String> roles) {
		try {
			return createJWT(email, roles, expireToken);
		} catch (JOSEException e) {
			log.debug("Error creating JWT", e);
			return null;
		}
	}

	public static String createRefreshToken(String email, List<String> roles) {
		try {
			return createJWT(email, roles, expireRefreshToken);
		} catch (JOSEException e) {
			log.debug("Error creating refresh JWT", e);
			return null;
		}
	}

	public static AuthenticationToken parseToken(String token) {
		try {
			byte[] secretKey = SECRET.getBytes();
			SignedJWT signedJWT = SignedJWT.parse(token);
			boolean verified = signedJWT.verify(new MACVerifier(secretKey));
			if (verified) {
				ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
				JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.HS256, new ImmutableSecret<>(secretKey));
				jwtProcessor.setJWSKeySelector(keySelector);
				jwtProcessor.process(signedJWT, null);
				JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
				String email = claims.getSubject();
				List<String> roles = claims.getStringListClaim("roles");
				var authorities = roles == null ? null : roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
				return new AuthenticationToken(email, null, authorities);
			}
		} catch (BadJOSEException | JOSEException | ParseException e) {
			log.debug("Error auth token: " + token, e);
		}
		return null;
	}
}
