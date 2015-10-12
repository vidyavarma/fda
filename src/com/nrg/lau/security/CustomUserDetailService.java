/**
 * 
 */
package com.nrg.lau.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nrg.lau.utils.ReadConfig;

/**
 * 
 *
 */
public class CustomUserDetailService implements UserDetailsService {

	private static Logger log	= Logger.getLogger(CustomUserDetailService.class);
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		UserDetails userDetails =null; 
		String webServiceEnabled="N";
		String userNameCon="";
		String password="";
		
		try{
			webServiceEnabled=ReadConfig.getMessage().getString("ENABLE_WEB_SERVICE");
			userNameCon=ReadConfig.getMessage().getString("WEB_SERVICE_USER_NAME");
			password=ReadConfig.getMessage().getString("WEB_SERVICE_PASSWORD");
			
			userNameCon=userNameCon==null?"":userNameCon;
			password=password==null?"":password;
		}
		catch(Exception ex){
			log.error("Either ENABLE_WEB_SERVICE or WEB_SERVICE_USER_NAME or WEB_SERVICE_PASSWORD propery is missing from config file",ex);
		}
		userDetails = new User(userNameCon.equals("")?"6c6c37c2144fd77f39e62327cd079a1a":userNameCon, 
				password.equals("")?"6c6c37c2144fd77f39e62327cd079a1a":password, webServiceEnabled.equals("Y"), true, true, true, authorities);

		return userDetails;
	}
}
