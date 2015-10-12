package com.nrg.lau.utils;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ReadConfig {
	
	private static Logger log	= Logger.getLogger(ReadConfig.class);
	
	public static ResourceBundle getMessage() throws Exception	{   
		return ResourceBundle.getBundle("config");
	}
	
	public static String replaceBackslash(String val) {
		return val.replaceAll("\\\\", "");
	}
	
	public static String getAuthenticationOption() {
		
		String tmp = "";
		try {
			tmp = getMessage().getString("AUTHENTICATION_OPTION");
		}catch (Exception e) {
			log.error("Failed to fetch AUTHENTICATION_OPTION -> " + e);
		}
		
		return tmp;
	}
	
	public static String getValue(String msg)	{   
		String val = "";
		try {	val = getMessage().getString(msg);
		}catch (Exception e) {	log.error(e);	}		
		return val;
	}
}
