package com.creativehub.backend.controllers;

import com.creativehub.backend.services.UserManager;
import com.creativehub.backend.services.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
	private final UserManager userManager;

	@GetMapping("/")
	public List<UserDto> getAllUsers() {
		return userManager.findAll();
	}

	@GetMapping("/{id}")
	public UserDto getUser(@PathVariable long id) {
		return userManager.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}

	@PutMapping("/{id}")
	public UserDto updateUser(@PathVariable long id, @RequestBody UserDto user) {
		return userManager.updateUser(id, user).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable long id) {
		userManager.deleteById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}
}
