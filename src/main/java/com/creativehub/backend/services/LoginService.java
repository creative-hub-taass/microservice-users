package com.creativehub.backend.services;

import com.creativehub.backend.services.dto.LoginRequest;
import com.creativehub.backend.services.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface LoginService {
	ResponseEntity<String> refresh(String token);

	ResponseEntity<UserDto> login(LoginRequest request);
}
