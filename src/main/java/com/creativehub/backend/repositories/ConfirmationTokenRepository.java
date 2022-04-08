package com.creativehub.backend.repositories;

import com.creativehub.backend.models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, UUID> {
	Optional<ConfirmationToken> findByToken(String token);

	@Transactional
	@Modifying
	@Query("UPDATE ConfirmationToken c SET c.confirmedAt = ?2 WHERE c.token = ?1")
	void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}