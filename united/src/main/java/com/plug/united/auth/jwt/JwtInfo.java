package com.plug.united.auth.jwt;

import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;

public class JwtInfo {
	private static final String TOKEN_KEY = "plug.united";
	
	public static final String HEADER_NAME = "Plug-Token";
	public static final String ISSUER = "plug";
	public static final long EXPIRES_LIMIT = 3L;

	public static Algorithm getAlgorithm() {
		try {
			return Algorithm.HMAC256(JwtInfo.TOKEN_KEY);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			return Algorithm.none();
		}
	}
}
