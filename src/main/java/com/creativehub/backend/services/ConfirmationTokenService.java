package com.creativehub.backend.services;

import com.creativehub.backend.models.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
	void saveConfirmationToken(ConfirmationToken token);

	Optional<ConfirmationToken> getToken(String token);

	void setConfirmedAt(String token);
}
