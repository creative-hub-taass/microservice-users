package com.creativehub.backend.services;

import com.creativehub.backend.services.dto.RegistrationRequest;

public interface RegistrationService {
	String register(RegistrationRequest request) throws IllegalStateException;

	String confirmToken(String token) throws IllegalStateException;


}
