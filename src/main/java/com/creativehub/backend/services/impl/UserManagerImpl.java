package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.ConfirmationToken;
import com.creativehub.backend.models.User;
import com.creativehub.backend.repositories.UserRepository;
import com.creativehub.backend.services.UserManager;
import com.creativehub.backend.services.dto.UserDto;
import com.creativehub.backend.services.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserManagerImpl implements UserManager {
	private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;

	public List<UserDto> findAll() {
		return userRepository.findAll().stream().map(userMapper::userToUserDto).collect(Collectors.toList());
	}

	public UserDto save(UserDto user) {
		return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(user)));
	}

	@Override
	public Optional<UserDto> findById(long id) {
		return userRepository.findById(id).map(userMapper::userToUserDto);
	}

	@Override
	public boolean existsById(long id) {
		return userRepository.existsById(id);
	}

	@Override
	public Optional<Boolean> deleteById(long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return Optional.of(true);
		} else return Optional.empty();
	}

	@Override
	public Optional<UserDto> updateUser(long id, UserDto update) {
		return userRepository.findById(id).map(user -> {
			userMapper.updateUserFromUserDto(update, user);
			return userMapper.userToUserDto(userRepository.save(user));
		});
	}

	public String signUpUser(User user) {
		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

		if (userExists) {
			// TODO check of attributes are the same and
			// TODO if email not confirmed send confirmation email.
			throw new IllegalStateException("email already taken");
		}

		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);

		String token = UUID.randomUUID().toString();

		ConfirmationToken confirmationToken = new ConfirmationToken();
		confirmationToken.setToken(token);
		confirmationToken.setCreatedAt(LocalDateTime.now());
		confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(20));
		confirmationToken.setUser(user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);

		//TODO: SEND EMAIL
		return token;
	}

	public int enableUser(long id) {
		return userRepository.enableUser(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.orElseThrow(() ->
						new UsernameNotFoundException(
								String.format(USER_NOT_FOUND_MSG, username)));
	}
}
