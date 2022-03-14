package com.creativehub.backend.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationToken extends UsernamePasswordAuthenticationToken {
	public AuthenticationToken(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	@Override
	public String getPrincipal() {
		return (String) super.getPrincipal();
	}

	@Override
	public String getCredentials() {
		return (String) super.getCredentials();
	}
}
