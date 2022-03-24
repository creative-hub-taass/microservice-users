package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.ConfirmationToken;
import com.creativehub.backend.models.User;
import com.creativehub.backend.models.enums.Role;
import com.creativehub.backend.services.ConfirmationTokenService;
import com.creativehub.backend.services.EmailService;
import com.creativehub.backend.services.RegistrationService;
import com.creativehub.backend.services.UserManager;
import com.creativehub.backend.services.dto.RegistrationRequest;
import com.creativehub.backend.util.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
	//language=HTML
	private static final String EMAIL_CONFIRMED = "<html lang='en'><head><title>creativeHub</title></head><body><h2>creativeHub</h2><p>Email confirmed. You can close this page.</p></body></html>";
	private final UserManager userManager;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailService emailService;

	private final String url = "192.168.49.2";
	private final String port = "30001";

	@Override
	public String register(RegistrationRequest request) throws IllegalStateException {
		boolean isValidEmail = EmailValidator.test(request.getEmail());
		if (!isValidEmail) {
			throw new IllegalStateException("Invalid email");
		}
		User user = new User();
		user.setUsername(UUID.randomUUID().toString());
		user.setNickname(request.getNickname());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setRole(Role.USER);
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken();
		confirmationToken.setToken(token);
		confirmationToken.setCreatedAt(LocalDateTime.now());
		confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(20));
		confirmationToken.setUser(user);
		String link = "http://"+url+":"+port+"/api/v1/access/confirm?token=" + token;
		String emailBody = buildEmail(request.getNickname(), link);
		userManager.signUpUser(user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		emailService.send(request.getEmail(), emailBody);
		return "Registration successful";
	}

	@Override
	@Transactional
	public String confirmToken(String token) throws IllegalStateException {
		ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
				.orElseThrow(() -> new IllegalStateException("Token not found"));
		if (confirmationToken.getConfirmedAt() != null) {
			throw new IllegalStateException("Email already confirmed");
		}
		LocalDateTime expiredAt = confirmationToken.getExpiresAt();
		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("Token expired");
		}
		confirmationTokenService.setConfirmedAt(token);
		userManager.enableUser(confirmationToken.getUser().getId());
		return EMAIL_CONFIRMED;
	}

	private String buildEmail(String name, String link) {
		//language=HTML
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
				"\n" +
				"<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
				"\n" +
				"  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
				"    <tbody><tr>\n" +
				"      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
				"        \n" +
				"        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
				"          <tbody><tr>\n" +
				"            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
				"                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
				"                  <tbody><tr>\n" +
				"                    <td style=\"padding-left:10px\">\n" +
				"                  \n" +
				"                    </td>\n" +
				"                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
				"                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
				"                    </td>\n" +
				"                  </tr>\n" +
				"                </tbody></table>\n" +
				"              </a>\n" +
				"            </td>\n" +
				"          </tr>\n" +
				"        </tbody></table>\n" +
				"        \n" +
				"      </td>\n" +
				"    </tr>\n" +
				"  </tbody></table>\n" +
				"  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
				"    <tbody><tr>\n" +
				"      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
				"      <td>\n" +
				"        \n" +
				"                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
				"                  <tbody><tr>\n" +
				"                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
				"                  </tr>\n" +
				"                </tbody></table>\n" +
				"        \n" +
				"      </td>\n" +
				"      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
				"    </tr>\n" +
				"  </tbody></table>\n" +
				"\n" +
				"\n" +
				"\n" +
				"  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
				"    <tbody><tr>\n" +
				"      <td height=\"30\"><br></td>\n" +
				"    </tr>\n" +
				"    <tr>\n" +
				"      <td width=\"10\" valign=\"middle\"><br></td>\n" +
				"      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
				"        \n" +
				"            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
				"        \n" +
				"      </td>\n" +
				"      <td width=\"10\" valign=\"middle\"><br></td>\n" +
				"    </tr>\n" +
				"    <tr>\n" +
				"      <td height=\"30\"><br></td>\n" +
				"    </tr>\n" +
				"  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
				"\n" +
				"</div></div>";
	}
}