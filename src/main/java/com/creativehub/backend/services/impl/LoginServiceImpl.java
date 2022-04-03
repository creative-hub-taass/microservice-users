package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.ConfirmationToken;
import com.creativehub.backend.models.User;
import com.creativehub.backend.models.enums.Role;
import com.creativehub.backend.services.ConfirmationTokenService;
import com.creativehub.backend.services.LoginService;
import com.creativehub.backend.services.UserManager;
import com.creativehub.backend.services.dto.LoginRequest;
import com.creativehub.backend.services.dto.RegistrationRequest;
import com.creativehub.backend.services.dto.UserDto;
import com.creativehub.backend.services.mapper.UserMapper;
import com.creativehub.backend.util.AuthenticationToken;
import com.creativehub.backend.util.JwtUtil;
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
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	private final AuthenticationManager authenticationManager;
	private final UserMapper userMapper;
	private final UserManager userManager;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public ResponseEntity<String> refresh(String token) {
		AuthenticationToken authenticationToken = JwtUtil.parseToken(token);
		if (authenticationToken != null) {
			String accessToken = JwtUtil.createAccessToken(authenticationToken.getPrincipal(), authenticationToken.getRoles());
			return ResponseEntity.ok(accessToken);
		} else return ResponseEntity.badRequest().body("Invalid refresh token");
	}

	@Override
	public Pair<UserDto, HttpHeaders> login(LoginRequest request) throws AuthenticationException {

		return this.Authentication(request.getEmail(), request.getPassword());
	}


	public Pair<UserDto, HttpHeaders> loginSocial(RegistrationRequest request) throws AuthenticationException {

	/*	String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
		//creo l'utente se non esiste già
		UserDetails temp = userManager.getUserByEmail(request.getEmail());
		if(temp==null)
			createUser(request.getEmail(), request.getNickname(), encodedPassword);
		 else
			userManager.changePassword(request.getEmail(), encodedPassword); */

		UserDetails temp = userManager.getUserByEmail(request.getEmail());
		if(temp==null) createUser(request.getEmail(), request.getNickname(), request.getEmail()+request.getNickname());

		return this.Authentication(request.getEmail(), request.getEmail()+request.getNickname());
	}

	private Pair<UserDto, HttpHeaders> Authentication(String email, String password)throws AuthenticationException{
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);

		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		User user = (User) authentication.getPrincipal();
		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		String accessToken = JwtUtil.createAccessToken(user.getUsername(), roles);
		String refreshToken = JwtUtil.createRefreshToken(user.getUsername(), roles);
		if (accessToken != null && refreshToken != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-ACCESS-TOKEN", accessToken);
			headers.add("X-REFRESH-TOKEN", refreshToken);
			return Pair.of(userMapper.userToUserDto(user), headers);
		} else throw new AuthenticationServiceException("Cannot create JWT tokens");
	}


	private void createUser(String email, String nickname, String password){
			User userM = new User();
			userM.setUsername(UUID.randomUUID().toString());
			userM.setNickname(nickname);
			userM.setEmail(email);
			userM.setPassword(password);
			userM.setEnabled(true);
			userM.setRole(Role.USER);
			userManager.signUpUser(userM);
	}

}

