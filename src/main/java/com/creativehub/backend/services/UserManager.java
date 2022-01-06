package com.creativehub.backend.services;

import com.creativehub.backend.models.User;

import java.util.List;
import java.util.Optional;

public interface UserManager {
	List<User> findAll();

	User save(User user);

	Optional<User> findById(long id);

	boolean existsById(long id);

	Optional<Boolean> deleteById(long id);

	Optional<User> updateUser(long id, User update);
}
