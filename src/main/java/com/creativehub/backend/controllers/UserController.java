package com.creativehub.backend.controllers;

import com.creativehub.backend.services.ProducerService;
import com.creativehub.backend.services.UserManager;
import com.creativehub.backend.services.dto.PublicUserDto;
import com.creativehub.backend.services.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
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
		return userManager.saveUser(userDto).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "E-mail or username alredy taken"));
	}

	@GetMapping("/{id}")
	public UserDto getUser(@PathVariable UUID id) {
		return userManager.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}

	@GetMapping("/-/{id}")
	public PublicUserDto getCreator(@PathVariable UUID id) {
		return userManager.findById(id)
				.map(PublicUserDto::new)
				.filter(PublicUserDto::isCreator)
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}

	@PostMapping("/-/public")
	public List<PublicUserDto> getUsers(@RequestBody List<UUID> uuidList) {
		List<PublicUserDto> result = userManager.getUsers(uuidList);
		if (result == null) throw new ResponseStatusException(NOT_FOUND, "Users not found");
		return result;
	}

	@PutMapping("/{id}")
	public UserDto updateUser(@PathVariable UUID id, @RequestBody UserDto user) {
		return userManager.updateUser(id, user).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable UUID id) {
		userManager.deleteById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
		producerService.sendMessage(id);
	}

	@GetMapping("/{id}/followed")
	public List<UserDto> getFollowed(@PathVariable UUID id) {
		if (!userManager.existsById(id))
			throw new ResponseStatusException(NOT_FOUND, "User not found");
		return userManager.getFollowed(id);
	}

	@GetMapping("/{id}/followers")
	public List<UserDto> getFollowers(@PathVariable UUID id) {
		if (!userManager.existsById(id))
			throw new ResponseStatusException(NOT_FOUND, "User not found");
		return userManager.getFollowers(id);
	}

	@PutMapping("/{idFollower}/follow/{idFollowed}")
	public UserDto addFollow(@PathVariable UUID idFollower, @PathVariable UUID idFollowed) {
		if (!userManager.existsById(idFollower))
			throw new ResponseStatusException(NOT_FOUND, "User not found");
		if (!userManager.existsById(idFollowed))
			throw new ResponseStatusException(BAD_REQUEST, "Followed user not found");
		return userManager.addFollow(idFollower, idFollowed);
	}

	@PutMapping("{idFollower}/unfollow/{idFollowed}")
	public UserDto deleteFollow(@PathVariable UUID idFollower, @PathVariable UUID idFollowed) {
		if (!userManager.existsById(idFollower))
			throw new ResponseStatusException(NOT_FOUND, "User not found");
		if (!userManager.existsById(idFollowed))
			throw new ResponseStatusException(BAD_REQUEST, "Followed user not found");
		return userManager.deleteFollow(idFollower, idFollowed);
	}
}
