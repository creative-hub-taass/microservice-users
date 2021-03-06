package com.creativehub.backend.services.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegistrationRequest implements Serializable {
	private final String email;
	private final String password;
	private final String nickname;
}