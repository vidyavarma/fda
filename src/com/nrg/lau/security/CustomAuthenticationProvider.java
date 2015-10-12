package com.nrg.lau.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		return authentication;
	}

	
	public boolean supports(Class<? extends Object> arg0) {
		return true;
	}

}
