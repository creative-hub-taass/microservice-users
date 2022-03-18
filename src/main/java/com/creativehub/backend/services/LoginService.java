package com.creativehub.backend.services;

import com.creativehub.backend.services.dto.LoginRequest;
import com.creativehub.backend.services.dto.UserDto;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

public interface LoginService {
	ResponseEntity<String> refresh(String token);

	Pair<UserDto, HttpHeaders> login(LoginRequest request) throws AuthenticationException;
}
