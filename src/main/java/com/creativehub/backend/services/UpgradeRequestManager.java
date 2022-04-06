package com.creativehub.backend.services;

import com.creativehub.backend.services.dto.UpgradeRequestDto;
import com.creativehub.backend.util.UpgradeRequestException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UpgradeRequestManager {
	Optional<UpgradeRequestDto> findById(UUID id);

	List<UpgradeRequestDto> findByUserId(UUID id);

	boolean existsById(UUID id);

	void acceptRequest(UUID id) throws Exception;

	void rejectRequest(UUID id) throws UpgradeRequestException;
}
