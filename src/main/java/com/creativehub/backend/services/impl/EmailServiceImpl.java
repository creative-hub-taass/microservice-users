package com.creativehub.backend.services.impl;

import com.creativehub.backend.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
	private final JavaMailSender mailSender;

	@Override
	@Async
	public void send(String to, String email) throws IllegalStateException {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("creativeHub - confirm your email");
			helper.setFrom("info@creativehub.com");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			log.error("Failed to send email", e);
			throw new IllegalStateException("Failed to send email");
		}
	}
}