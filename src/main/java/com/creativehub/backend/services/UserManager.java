package com.creativehub.backend.services;

import com.creativehub.backend.models.User;
import com.creativehub.backend.services.dto.PublicUserDto;
import com.creativehub.backend.services.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserManager extends UserDetailsService {
	List<UserDto> findAll();

	Optional<UserDto> findById(UUID id);

	boolean existsById(UUID id);

	Optional<Boolean> deleteById(UUID id);

	Optional<UserDto> updateUser(UUID id, UserDto update);

	void signUpUser(User user) throws IllegalStateException;

	void setupRootUser();

	void enableUser(UUID id);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	Optional<User> getUserByEmail(String email);

	Boolean changePassword(UUID uuid, String oldPassword, String newPassword);

	UserDto addFollow(UUID idFollower, UUID idFollowed) throws IllegalStateException;

	UserDto deleteFollow(UUID idFollower, UUID idFollowed) throws IllegalStateException;

	List<UserDto> getFollowed(UUID id);

	List<UserDto> getFollowers(UUID id);

	List<PublicUserDto> getPublicUsers(List<UUID> ids);

	/**
	 * Only for testing purposes
	 */
	Optional<UserDto> saveUser(UserDto userDto);

	/**
	 * Only for testing purposes
	 */
	void addFollows(List<UUID[]> follows);
}
