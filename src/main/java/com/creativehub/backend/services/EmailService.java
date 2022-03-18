package com.creativehub.backend.services;

public interface EmailService {
	void send(String to, String email) throws IllegalStateException;
}