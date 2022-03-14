package com.creativehub.backend.services;

public interface EmailSender {
	void send(String to, String email) throws IllegalStateException;
}