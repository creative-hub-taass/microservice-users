package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.User;
import com.creativehub.backend.repositories.UserRepository;
import com.creativehub.backend.services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagerImpl implements UserManager {
	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findById(long id) {
		return userRepository.findById(id);
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
	public Optional<User> updateUser(long id, User update) {
		return userRepository.findById(id).map(user -> {
			user.setEmail(update.getEmail());
			user.setUsername(update.getUsername());
			user.setNickname(update.getNickname());
			//TODO
			return userRepository.save(user);
		});
	}
}
