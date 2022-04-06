package com.creativehub.backend.services.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
	private final String email;
	private final String password;
}
