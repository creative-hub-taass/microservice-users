package com.creativehub.backend.controllers;

import com.creativehub.backend.services.ProducerService;
import com.creativehub.backend.services.UserManager;
import com.creativehub.backend.services.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final UserManager userManager;
	private final ProducerService producerService;

	@GetMapping("/")
	public List<UserDto> getAllUsers() {
		return userManager.findAll();
	}

	/**
	 * Only for testing purposes
	 */
	@PostMapping("/")
	public UserDto saveUser(@RequestBody UserDto userDto) {
		return userManager.saveUser(userDto);
	}

	@GetMapping("/{id}")
	public UserDto getUser(@PathVariable UUID id) {
		return userManager.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}

	@PutMapping("/{id}")
	public UserDto updateUser(@PathVariable UUID id, @RequestBody UserDto user) {
		return userManager.updateUser(id, user).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable UUID id) {
		if(userManager.findById(id).get().getCreator()!=null) producerService.sendMessage(id);
		userManager.deleteById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}

	@PutMapping("/addfollow/{idFollower}")
	public UserDto addFollow(@PathVariable UUID idFollower, @RequestParam UUID idFollowed) {
		if (!userManager.existsById(idFollower) || !userManager.existsById(idFollowed))
			throw new ResponseStatusException(NOT_FOUND, "Users not found");
		return userManager.addFollow(idFollower, idFollowed);
	}

	@PutMapping("/deletefollow/{idFollowed}")
	public UserDto deleteFollow(@PathVariable UUID idFollowed, @RequestParam UUID idFollower) {
		if (!userManager.existsById(idFollowed) || !userManager.existsById(idFollower))
			throw new ResponseStatusException(NOT_FOUND, "Users not found");
		return userManager.deleteFollow(idFollower, idFollowed);
	}
}
