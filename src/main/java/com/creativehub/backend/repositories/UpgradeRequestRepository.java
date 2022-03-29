package com.creativehub.backend.repositories;

import com.creativehub.backend.models.UpgradeRequest;
import com.creativehub.backend.models.enums.UpgradeRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UpgradeRequestRepository extends JpaRepository<UpgradeRequest, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE UpgradeRequest ur SET ur.status = ?2 WHERE ur.id = ?1")
    void updateRequestStatus(Long id, UpgradeRequestStatus status);

    @Query("SELECT ur FROM UpgradeRequest ur WHERE ur.user.id = ?1")
    <Optional>UpgradeRequest findByUserId(Long id);
}
