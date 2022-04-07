package com.creativehub.backend.controllers;

import com.creativehub.backend.models.UpgradeRequest;
import com.creativehub.backend.services.UpgradeRequestManager;
import com.creativehub.backend.services.dto.UpgradeRequestDto;
import com.creativehub.backend.util.UpgradeRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users/upgrade")
@AllArgsConstructor
public class UpgradeRequestController {
	private final UpgradeRequestManager upgradeRequestManager;

	@PostMapping("/request")
	public UpgradeRequestDto addUpgradeRequest(@RequestBody UpgradeRequest upgradeRequest) {
		try {
			return upgradeRequestManager.addRequest(upgradeRequest);
		} catch (UpgradeRequestException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}

	@GetMapping("/requests")
	public List<UpgradeRequestDto> getAllRequests() {
		return upgradeRequestManager.findAll();
	}

	@GetMapping("/request/{id}")
	public UpgradeRequestDto getRequestById(@PathVariable UUID id) {
		return upgradeRequestManager.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Request not found"));
	}

	@GetMapping("/request/user/{id}")
	public List<UpgradeRequestDto> getRequestOfUser(@PathVariable UUID id) {
		return upgradeRequestManager.findByUserId(id);
	}

	@GetMapping("/accept/{id}")
	public ResponseEntity<String> acceptUpgradeRequest(@PathVariable UUID id) {
		try {
			upgradeRequestManager.acceptRequest(id);
			return ResponseEntity.ok("Request accepted");
		} catch (UpgradeRequestException e) {
			// TODO: migliorare error message
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}

	@GetMapping("/reject/{id}")
	public ResponseEntity<String> rejectUpgradeRequest(@PathVariable UUID id) {
		try {
			upgradeRequestManager.rejectRequest(id);
			return ResponseEntity.ok("Request rejected");
		} catch (UpgradeRequestException e) {
			// TODO: migliorare error message
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}
}
