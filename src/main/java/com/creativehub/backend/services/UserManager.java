package com.creativehub.backend.services;

import com.creativehub.backend.models.User;
import com.creativehub.backend.services.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserManager extends UserDetailsService {
	List<UserDto> findAll();

	UserDto save(UserDto user);

	Optional<UserDto> findById(long id);

	boolean existsById(long id);

	Optional<Boolean> deleteById(long id);

	Optional<UserDto> updateUser(long id, UserDto update);

	String signUpUser(User user);

	int enableUser(long id);
}
