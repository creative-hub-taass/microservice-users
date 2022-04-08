package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.ConfirmationToken;
import com.creativehub.backend.repositories.ConfirmationTokenRepository;
import com.creativehub.backend.services.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
	private final ConfirmationTokenRepository confirmationTokenRepository;

	@Override
	public void saveConfirmationToken(ConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}

	@Override
	public Optional<ConfirmationToken> getToken(String token) {
		return confirmationTokenRepository.findByToken(token);
	}

	@Override
	public void setConfirmedAt(String token) {
		confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
	}
}