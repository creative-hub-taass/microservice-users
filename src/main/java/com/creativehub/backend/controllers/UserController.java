package com.creativehub.backend.controllers;

import com.creativehub.backend.models.User;
import com.creativehub.backend.services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	private UserManager userManager;

	@GetMapping("/")
	public List<User> getAllUsers() {
		return userManager.findAll();
	}

	@PostMapping("/create")
	public User createUser(@RequestBody User user) {
		return userManager.save(user);
	}

	@GetMapping("/{id}")
	public User getUser(@PathVariable long id) {
		return userManager.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable long id, @RequestBody User user) {
		return userManager.updateUser(id, user).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable long id) {
		userManager.deleteById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}
}
