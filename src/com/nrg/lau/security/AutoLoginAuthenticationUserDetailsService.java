package com.nrg.lau.security;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AutoLoginAuthenticationUserDetailsService implements AuthenticationUserDetailsService{

	private static Logger log	= Logger.getLogger(AutoLoginAuthenticationUserDetailsService.class);
	
	@Override
	public UserDetails loadUserDetails(Authentication authentication)
			throws UsernameNotFoundException {
		
		if (authentication.getPrincipal() != null) {
			log.info("Authentication principal is ->"+ authentication.getPrincipal());
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
			return new User((String) authentication.getPrincipal(), "none", true, true, true, true, authorities);
		}

		return null;
	}

}
