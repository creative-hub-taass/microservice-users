package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.User;
import com.creativehub.backend.models.enums.Role;
import com.creativehub.backend.repositories.UserRepository;
import com.creativehub.backend.services.UserManager;
import com.creativehub.backend.services.dto.UserDto;
import com.creativehub.backend.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {
	private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<UserDto> findAll() {
		return userRepository.findAll().stream().map(userMapper::userToUserDto).collect(Collectors.toList());
	}

	@Override
	public Optional<UserDto> findById(UUID id) {
		return userRepository.findById(id).map(userMapper::userToUserDto);
	}

	@Override
	public boolean existsById(UUID id) {
		return userRepository.existsById(id);
	}

	@Override
	public Optional<Boolean> deleteById(UUID id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return Optional.of(true);
		} else return Optional.empty();
	}

	@Override
	public Optional<UserDto> updateUser(UUID id, UserDto update) {
		return userRepository.findById(id).map(user -> {
			userMapper.updateUserFromUserDto(update, user);
			return userMapper.userToUserDto(userRepository.save(user));
		});
	}

	public UserDto signUpUser(User user) throws IllegalStateException {
		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
		if (userExists) {
			throw new IllegalStateException("Email already taken");
		}
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setUsername(UUID.randomUUID().toString());
		user = userRepository.save(user);
		user.setUsername(user.getId().toString());
		return userMapper.userToUserDto(userRepository.save(user));
	}

	public void setupRootUser() {
		User user = new User();
		user.setNickname("root");
		user.setUsername("root");
		user.setEmail("root@creativehub.com");
		user.setRole(Role.ADMIN);
		user.setEnabled(true);
		String encodedPassword = bCryptPasswordEncoder.encode("root");
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}

	public void enableUser(UUID id) {
		userRepository.enableUser(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username).orElseThrow(() ->
				new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<UUID> getId(String email) {
		return userRepository.findByEmail(email).map(User::getId);
	}

	public void changePassword(String email, String newPassword) {
		if (userRepository.findByEmail(email).isPresent())
			userRepository.findByEmail(email).get().setPassword(newPassword);
	}

	@Override
	public UserDto addFollow(UUID idFollower, UUID idFollowed) throws IllegalStateException {
		Optional<User> follower = userRepository.findById(idFollower);
		Optional<User> followed = userRepository.findById(idFollowed);
		if (follower.isEmpty() || followed.isEmpty())
			throw new IllegalStateException();
		follower.get().getInspirers().add(followed.get());
		followed.get().getFans().add(follower.get());
		userRepository.save(follower.get());
		userRepository.save(followed.get());
		return userMapper.userToUserDto(follower.get());
	}

	@Override
	public UserDto deleteFollow(UUID idFollower, UUID idFollowed) throws IllegalStateException {
		Optional<User> follower = userRepository.findById(idFollower);
		Optional<User> followed = userRepository.findById(idFollowed);
		if (follower.isEmpty() || followed.isEmpty())
			throw new IllegalStateException();
		follower.get().getInspirers().remove(followed.get());
		followed.get().getFans().remove(follower.get());
		userRepository.save(follower.get());
		userRepository.save(followed.get());
		return userMapper.userToUserDto(followed.get());
	}

	@Override
	public List<UserDto> getFollowed(UUID id) {
		return userRepository.findById(id).stream()
				.map(User::getInspirers)
				.flatMap(Collection::stream)
				.map(userMapper::userToUserDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<UserDto> getFollowers(UUID id) {
		return userRepository.findById(id).stream()
				.map(User::getFans)
				.flatMap(Collection::stream)
				.map(userMapper::userToUserDto)
				.collect(Collectors.toList());
	}

	/**
	 * Only for testing purposes
	 */
	@Override
	public Optional<UserDto> saveUser(UserDto userDto) {
		if (userRepository.existsByUsernameOrEmail(userDto.getUsername(), userDto.getEmail()))
			return Optional.empty();
		User user = userMapper.userDtoToUser(userDto);
		// use the username as password
		String encodedPassword = bCryptPasswordEncoder.encode(user.getUsername());
		user.setPassword(encodedPassword);
		return Optional.of(userMapper.userToUserDto(userRepository.save(user)));
	}
}
