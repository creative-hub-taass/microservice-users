package com.creativehub.backend.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationToken extends UsernamePasswordAuthenticationToken {
	public AuthenticationToken(String email, String password, Collection<? extends GrantedAuthority> authorities) {
		super(email, password, authorities);
	}

	@Override
	public String getPrincipal() {
		return (String) super.getPrincipal();
	}

	@Override
	public String getCredentials() {
		return (String) super.getCredentials();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return super.getAuthorities();
	}

	public List<String> getRoles() {
		return super.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}
}
