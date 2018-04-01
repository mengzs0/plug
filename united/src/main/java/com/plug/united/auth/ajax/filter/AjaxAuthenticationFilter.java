package com.plug.united.auth.ajax.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plug.united.member.entity.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private final ObjectMapper objectMapper;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public AjaxAuthenticationFilter(RequestMatcher requestMatcher, ObjectMapper objectMapper) {
		super(requestMatcher);
		this.objectMapper = objectMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
		if (isJson(request)) {
			Member member = objectMapper.readValue(request.getReader(), Member.class);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(member.getUserId(), member.getPassword());
			return getAuthenticationManager().authenticate(authentication);
		} else {
			throw new AccessDeniedException("Don't use content type for " + request.getContentType());
		}
	}

	private boolean isJson(HttpServletRequest request) {
		logger.debug(request.getContentType());
		return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.getContentType())
				|| MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(request.getContentType());
	}
}
