package com.creativehub.backend.repositories;

import com.creativehub.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Transactional
	@Modifying
	@Query("UPDATE User user SET user.enabled = TRUE WHERE user.id = ?1")
	void enableUser(Long id);
}