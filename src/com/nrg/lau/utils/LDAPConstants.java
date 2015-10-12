package com.nrg.lau.utils;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;


public class LDAPConstants {

	private static Logger log	= Logger.getLogger(LDAPConstants.class);
	private int port;
	private int version;
	private int srchScope;
	private String userSearch;
	private String objUser;
	private String host;

	public LDAPConstants() {
		try
		{
			ResourceBundle resource = LAUResourceBundle.getMessage();
			host 					= resource.getString("LDAPHost");
			port 					= Integer.valueOf(resource.getString("LDAPPort"));
			version 				= Integer.valueOf(resource.getString("LDAPversion"));			
			userSearch 				= resource.getString("LDAPUserSearch");
			objUser 				= resource.getString("LDAPObjUser");
			srchScope 				= Integer.valueOf(resource.getString("LDAPSrchScope"));
		}catch (Exception e) {
			log.error(e, e);
		}	
	}

	/**
	 * This method gets the host
	 * 
	 * @return host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * This method gets the objUser
	 * 
	 * @return objUser
	 */
	public String getObjUser() {
		return objUser;
	}

	/**
	 * This method gets the port
	 * 
	 * @return port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * This method gets the srchScope
	 * 
	 * @return srchScope
	 */
	public int getSrchScope() {
		return srchScope;
	}

	/**
	 * This method gets the userSearch
	 * 
	 * @return userSearch
	 */
	public String getUserSearch() {
		return userSearch;
	}

	/**
	 * This method gets the version
	 * 
	 * @return version
	 */
	public int getVersion() {
		return version;
	}

}
