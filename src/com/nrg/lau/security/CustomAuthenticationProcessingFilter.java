package com.nrg.lau.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import weblogic.servlet.security.ServletAuthentication;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.Util;
import com.nrg.lau.utils.ReadConfig;
public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
	
	private static Logger log	= Logger.getLogger(CustomAuthenticationProcessingFilter.class);
	
	protected CustomAuthenticationProcessingFilter() {
		super("/j_spring_security_check");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
				
		String uid 							= null;		
		boolean authenticated 				= false;
		Collection<GrantedAuthority> list 	= null;
		String autoLoginUser 	= null;
		Authentication auth 	= null;
		String authOptionString = "";
		String rtnUserID = "";
		
		try {
			auth = SecurityContextHolder.getContext().getAuthentication();
			autoLoginUser = auth.getName(); 
			log.info("Auto Login User -> " + autoLoginUser);
		}catch (Exception e) {
			log.error("Authentication Object is null -> " + e);
		}
				
		authOptionString = ReadConfig.getAuthenticationOption();
		log.info("Authentication Option:" + authOptionString);		
		
		if (authOptionString.trim().equalsIgnoreCase("SSO")) {
			log.info("AUTHENTICATION_EXCEPTION -> " + request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
			if(request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null) {
				String ssoError = String.valueOf(request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
				log.error(ssoError);
				throw new AuthenticationCredentialsNotFoundException(ssoError);
			}
		}
		
		
		if(autoLoginUser == null) {
			
			log.info("Auto Login User is null -> triggering custom handler");
			ApplicationContext ctx 	= 	AppContext.getApplicationContext();
			HashMap errorMap = (HashMap)ctx.getBean("userLoginErrorMap");
			if(request.getParameter(Constants.SPRING_USER_NAME) != null && 
					request.getParameter(Constants.SPRING_USER_PASSWORD) != null)
			{
				if(request.getParameter(Constants.SPRING_USER_NAME).trim().length() > 0 )
					//&& 	request.getParameter(Constants.SPRING_USER_PASSWORD).trim().length() > 0)
				{
					int validate = -1;
					
					try
	    			{						
						//0 - weblogic ServletAuthentication.login
						//1 - LDAP Authentication
						//2 - Return true, this is for DEBUG
						int authOption = 2; //default
						if (authOptionString.trim().equalsIgnoreCase("WEBLOGIC"))
							authOption = 0; //weblogic
						else if (authOptionString.trim().equalsIgnoreCase("LDAP"))
							authOption = 1; //LDAP
						else
							authOption = 2; //DEBUG
						log.info("------------Authentication option:"+authOptionString+" - "+authOption);
						validate = validateUser(request, response, 
								authOption,request.getParameter(Constants.SPRING_USER_NAME),
								request.getParameter(Constants.SPRING_USER_PASSWORD));					
	    				
	    			}catch(Exception e)	{
	    				errorMap.put(request.getSession().getId(), "User Authentication failed!");
	    				log.error("Servlet Authentication failed!");
	    				log.error(e);
	        			throw new AuthenticationCredentialsNotFoundException("CustomAuthenticationProcessingFilter - attemptAuthentication() -> User not connected!");
	    			}  
						
					if(validate == 0) {
						
						try {
							
							uid =	request.getParameter(Constants.SPRING_USER_NAME);
							PrimoUserProfile userProfile = new PrimoUserProfile();
							rtnUserID = userProfile.loadUserInSession(request, uid);
							authenticated 	= true;
	    					list 			= getAuthorities(true);
	    					
						} catch (Exception e) {
							throw new AuthenticationCredentialsNotFoundException(e.toString());
						}				
						 					
					}else {
						errorMap.put(request.getSession().getId(),"Invalid usename/password!");
			            log.error("Invalid usename/password!");
						throw new AuthenticationCredentialsNotFoundException("CustomAuthenticationProcessingFilter - attemptAuthentication() -> LDAP - Not Authenticated!-weblogic returned value="+validate);
					}    			
	    			  				        			 		
				}
			}	
		}
		
		log.info("--------------------############################--------------------------------" + autoLoginUser);
		
		if(autoLoginUser != null)
				return auth;
		
		
		log.info("----------------------------------------------------");	
		CustomAuthenticationToken token = new CustomAuthenticationToken(list,rtnUserID,authenticated);
		token.setDetails(authenticationDetailsSource.buildDetails(request));
        AuthenticationManager authenticationManager = getAuthenticationManager();
        Authentication authentication = authenticationManager.authenticate(token);
        log.info("----------------------------------------------------");	
        return authentication;		
	}

	private Collection<GrantedAuthority> getAuthorities(boolean isAdmin) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (isAdmin) {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authList;
	}
	
	private int validateUser(HttpServletRequest request,
			HttpServletResponse response, int option, String userName, 
			String userPassword) throws Exception{
		
		int validate = -1;
		if(option == 0) {
			//weblogic.servlet.security.ServletAuthentication 
			//AUTHENTICATED 0 
			//FAILED_AUTHENTICATION 1 
			//NEEDS_CREDENTIALS 2 
			validate = ServletAuthentication.login(userName,userPassword, request, response);
		}else if(option == 1) {
			validate = Util.LDAPBind(userName,userPassword);
		}else if(option == 2) {
			validate = 0;
		}
		
		return validate; 
	}

}
