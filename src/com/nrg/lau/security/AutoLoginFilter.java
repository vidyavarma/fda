package com.nrg.lau.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.nrg.lau.utils.ReadConfig;

public class AutoLoginFilter extends AbstractPreAuthenticatedProcessingFilter {
	
	

	private static Logger log	= Logger.getLogger(AutoLoginFilter.class);

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
			
		if(validateUser(request) != null) {
			return "ROLE_USER";		
		}
		
		return null;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		
		if(validateUser(request) != null) {
			try {
				
				HttpSession session  = request.getSession();
				if(session.getAttribute("lauUsers") == null) {
					log.info("lauUsers not in session - getting userProfile");
					try{
						PrimoUserProfile userProfile = new PrimoUserProfile();
						userProfile.loadUserInSession(request, validateUser(request));
					}catch (Exception e) {
						log.info("getPreAuthenticatedPrincipal() -> " + e);						
						request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, e.getMessage());						
					}					
				}				
				return validateUser(request);
			}catch (Exception e) {
				log.error("PreAuthenticatedPrincipal -> " + e);
				return null;
			}
				
		}
		
		return null;
		
		
	}
	
	private String validateUser(HttpServletRequest request) {
		
		String remoteUser = null;
		if(ReadConfig.getAuthenticationOption().equalsIgnoreCase("SSO")) {
			if(request.getRemoteUser() != null) {
				remoteUser = request.getRemoteUser();			
			}			
			
			if(request.getHeader("Proxy-Remote-User") != null) {
				remoteUser = request.getHeader("Proxy-Remote-User");			
			}		
			log.info("Remote User -> " + remoteUser);
		}
		return remoteUser;
	}

}
