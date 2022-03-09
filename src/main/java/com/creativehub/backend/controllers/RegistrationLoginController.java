package com.creativehub.backend.controllers;

import com.creativehub.backend.services.dto.RegistrationRequest;
import com.creativehub.backend.services.impl.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class RegistrationLoginController {
    private final RegistrationService registrationService;

    @PostMapping(path = "registration")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}