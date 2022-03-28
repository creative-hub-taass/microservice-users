package com.creativehub.backend.controllers;

import com.creativehub.backend.services.LoginService;
import com.creativehub.backend.services.RegistrationService;
import com.creativehub.backend.services.dto.LoginRequest;
import com.creativehub.backend.services.dto.RegistrationRequest;
import com.creativehub.backend.services.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/access")
@AllArgsConstructor
public class RegistrationLoginController {
	private final RegistrationService registrationService;
	private final LoginService loginService;

	@PostMapping(path = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			Pair<UserDto, HttpHeaders> pair = loginService.login(request);
			return ResponseEntity.ok().headers(pair.getSecond()).body(pair.getFirst());
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}

	@PostMapping(path = "/loginsocial")
	public ResponseEntity<?> loginSocial(@RequestBody RegistrationRequest request) {
		try {
			Pair<UserDto, HttpHeaders> pair = loginService.loginSocial(request);
			return ResponseEntity.ok().headers(pair.getSecond()).body(pair.getFirst());
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}

	@PostMapping(path = "/refresh")
	public ResponseEntity<String> refresh(@RequestBody String token) {
		return loginService.refresh(token);
	}

	@PostMapping(path = "/register")
	public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
		try {
			return ResponseEntity.ok(registrationService.register(request));
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping(path = "/confirm")
	public ResponseEntity<String> confirm(@RequestParam("token") String token) {
		try {
			return ResponseEntity.ok(registrationService.confirmToken(token));
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}