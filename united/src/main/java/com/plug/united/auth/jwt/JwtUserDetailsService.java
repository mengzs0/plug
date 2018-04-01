package com.plug.united.auth.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plug.united.auth.UserDetailsImpl;
import com.plug.united.member.entity.Member;
import com.plug.united.comm.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String token) {
		DecodedJWT decodedJWT = JwtUtil.tokenToJwt(token);

		if (decodedJWT == null) {
			throw new BadCredentialsException("Not used Token");
		}

		String id = decodedJWT.getClaim("id").asString();
		String role = decodedJWT.getClaim("role").asString();

		return new UserDetailsImpl(id, AuthorityUtils.createAuthorityList(role));
	}
}
