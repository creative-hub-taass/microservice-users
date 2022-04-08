package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.User;
import com.creativehub.backend.models.enums.Role;
import com.creativehub.backend.services.JwtUtil;
import com.creativehub.backend.services.LoginService;
import com.creativehub.backend.services.UserManager;
import com.creativehub.backend.services.dto.LoginRequest;
import com.creativehub.backend.services.dto.SocialLoginRequest;
import com.creativehub.backend.services.dto.UserDto;
import com.creativehub.backend.services.mapper.UserMapper;
import com.creativehub.backend.util.AuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	private final AuthenticationManager authenticationManager;
	private final UserMapper userMapper;
	private final UserManager userManager;
	private final JwtUtil jwtUtil;

	@Override
	public ResponseEntity<String> refresh(String token) {
		AuthenticationToken authenticationToken = jwtUtil.parseToken(token);
		if (authenticationToken != null) {
			String accessToken = jwtUtil.createAccessToken(authenticationToken.getPrincipal(), authenticationToken.getRoles());
			return ResponseEntity.ok(accessToken);
		} else return ResponseEntity.badRequest().body("Invalid refresh token");
	}

	@Override
	public Pair<UserDto, HttpHeaders> login(LoginRequest request) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		User user = (User) authentication.getPrincipal();
		return getResponseData(user);
	}


	public Pair<UserDto, HttpHeaders> loginSocial(SocialLoginRequest request) throws AuthenticationException {
		Optional<User> optional = userManager.getUserByEmail(request.getEmail());
		User user = optional.orElseGet(() -> {
			User newUser = new User();
			newUser.setNickname(request.getNickname());
			newUser.setEmail(request.getEmail());
			newUser.setPassword(UUID.randomUUID().toString());
			newUser.setEnabled(true);
			newUser.setRole(Role.USER);
			userManager.signUpUser(newUser);
			return newUser;
		});
		return getResponseData(user);
	}

	private Pair<UserDto, HttpHeaders> getResponseData(User user) {
		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		String accessToken = jwtUtil.createAccessToken(user.getUsername(), roles);
		String refreshToken = jwtUtil.createRefreshToken(user.getUsername(), roles);
		if (accessToken != null && refreshToken != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-ACCESS-TOKEN", accessToken);
			headers.add("X-REFRESH-TOKEN", refreshToken);
			return Pair.of(userMapper.userToUserDto(user), headers);
		} else throw new AuthenticationServiceException("Cannot create JWT tokens");
	}
}

