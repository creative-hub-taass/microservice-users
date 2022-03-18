package com.creativehub.backend.config.filters;

import com.creativehub.backend.controllers.RegistrationLoginController;
import com.creativehub.backend.util.AuthenticationToken;
import com.creativehub.backend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String accessPath = RegistrationLoginController.class.getAnnotation(RequestMapping.class).path()[0];
		if (request.getServletPath().startsWith(accessPath)) {
			filterChain.doFilter(request, response);
		} else try {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (authorizationHeader != null) {
				if (authorizationHeader.startsWith("Bearer ")) {
					String token = authorizationHeader.substring("Bearer ".length());
					AuthenticationToken authenticationToken = JwtUtil.parseToken(token);
					if (authenticationToken != null) {
						SecurityContextHolder.getContext().setAuthentication(authenticationToken);
						filterChain.doFilter(request, response);
					} else throw new IllegalStateException("Invalid authorization token");
				} else throw new IllegalStateException("Invalid authorization token");
			} else throw new IllegalStateException("Missing authorization token");
		} catch (IllegalStateException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			new ObjectMapper().writeValue(response.getOutputStream(), e.getMessage());
		}
	}
}
