package com.creativehub.backend.util;

import lombok.Data;

import java.util.List;

@Data
public class TokenData {
	private final String subject;
	private final List<String> roles;
}
