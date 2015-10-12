package com.nrg.lau.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	private static final long serialVersionUID = -680812308606281381L;
	private String uid = "";
	public CustomAuthenticationToken(Collection<GrantedAuthority> authorities,String uid,boolean authenticated) {
		super(authorities);
		this.uid = uid;
	    super.setAuthenticated(authenticated);
	}	
	
	public Object getCredentials() {
		return String.valueOf(uid);
	}

	
	public Object getPrincipal() {
		return null;
	}

}
