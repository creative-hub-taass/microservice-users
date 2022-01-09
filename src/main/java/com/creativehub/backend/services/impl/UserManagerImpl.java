package com.creativehub.backend.services.impl;

import com.creativehub.backend.repositories.UserRepository;
import com.creativehub.backend.services.UserManager;
import com.creativehub.backend.services.dto.UserDto;
import com.creativehub.backend.services.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserManagerImpl implements UserManager {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;

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
}
