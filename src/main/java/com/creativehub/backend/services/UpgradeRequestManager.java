package com.creativehub.backend.services;

import com.creativehub.backend.services.dto.UpgradeRequestDto;
import com.creativehub.backend.util.UpgradeRequestException;

import java.util.List;
import java.util.Optional;

public interface UpgradeRequestManager {
    Optional<UpgradeRequestDto> findById(long id);
    List<UpgradeRequestDto> findByUserId(long id);
    public boolean existsById(Long id);
    void acceptRequest(long id) throws Exception;
    void rejectRequest(long id) throws UpgradeRequestException;
}
