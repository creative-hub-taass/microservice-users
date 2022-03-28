package com.creativehub.backend.services;

import com.creativehub.backend.models.User;
import com.creativehub.backend.services.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserManager extends UserDetailsService {
	List<UserDto> findAll();

	Optional<UserDto> findById(long id);

	boolean existsById(long id);

	Optional<Boolean> deleteById(long id);

	Optional<UserDto> updateUser(long id, UserDto update);

	UserDto signUpUser(User user) throws IllegalStateException;

	void enableUser(long id);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	UserDetails getUserByUsername(String username);
}
