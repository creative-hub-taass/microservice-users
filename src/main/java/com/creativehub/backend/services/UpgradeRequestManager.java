package com.creativehub.backend.services;

import com.creativehub.backend.services.dto.UpgradeRequestDto;
import java.util.Optional;

public interface UpgradeRequestManager {
    Optional<UpgradeRequestDto> findById(long id);
    UpgradeRequestDto findByUserId(long id);
    public boolean existsById(Long id);
    void acceptRequest(long id) throws Exception;
    void rejectRequest(long id);
}
