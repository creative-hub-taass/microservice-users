package com.creativehub.backend.controllers;

import com.creativehub.backend.models.UpgradeRequest;
import com.creativehub.backend.services.dto.UpgradeRequestDto;
import com.creativehub.backend.services.impl.UpgradeRequestManagerImpl;
import com.creativehub.backend.services.mapper.UpgradeRequestMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/users/upgrade")
@AllArgsConstructor
public class UpgradeRequestController {
    private final UpgradeRequestManagerImpl upgradeRequestManager;
    private final UpgradeRequestMapper upgradeRequestMapper;

    @PostMapping("/request")
    public UpgradeRequestDto addUpgradeRequest(@RequestBody UpgradeRequest upgradeRequest) {
        //TODO: controllare che l'utente non sia già creator
        return upgradeRequestManager.addRequest(upgradeRequest);
    }

    @GetMapping("/requests")
    public List<UpgradeRequestDto> getAllRequests() {
        return upgradeRequestManager.findAll();
    }

    @GetMapping("/request/{id}")
    public Optional<UpgradeRequestDto> getRequestbyId(@PathVariable long id) {
        // TODO: controllare che esista la richiesta
        return upgradeRequestManager.findById(id);
    }

    // TODO: implementare getRequestOfUser
    @GetMapping("/request/user/{id}")
    public UpgradeRequestDto getRequestOfUser(@PathVariable long id) {
        return upgradeRequestManager.findByUserId(id);
    }

    @GetMapping("/accept/{id}")
    public void acceptUpgradeRequest(@PathVariable long id) {
        try {
            upgradeRequestManager.acceptRequest(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/reject/{id}")
    public void rejectUpgradeRequest(@PathVariable long id) {
        upgradeRequestManager.rejectRequest(id);
        // TODO: controllare che ci sia la richiesta
        // cambiare lo status della richiesta in rejected
    }
}
