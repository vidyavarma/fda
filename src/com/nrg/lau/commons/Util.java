package com.nrg.lau.commons;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.log4j.Logger;

import com.nrg.lau.utils.ReadConfig;

public class Util {
	
	private static Logger log	= Logger.getLogger(Util.class);
	
	public static String getUTF8String(String value) throws Exception{	
		return value != null ? new String(value.getBytes("UTF8"), "UTF8") : "";
	}
	
	@SuppressWarnings("unchecked")
	public static int LDAPBind(String userName, 
			String userPassword) throws Exception{
		
		int validate = -1;
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ReadConfig.replaceBackslash(ReadConfig.getMessage().getString("LDAP_PROVIDER_URL")));
		
		// Specify SSL
		//env.put(Context.SECURITY_PROTOCOL, "ssl");
		
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, userName);
		env.put(Context.SECURITY_CREDENTIALS, userPassword);
		
		try {
			
			@SuppressWarnings("unused")
			DirContext authContext = new InitialDirContext(env);
			//AUTHENTICATED 0
			validate = 0;
		} catch (javax.naming.AuthenticationException authEx) {
			log.info("Password incorrect!" + authEx.toString());
			throw authEx;		    
		} catch (NamingException namEx) {
			log.info("Something went wrong!" + namEx.toString());
			throw namEx;			
		}
		
		return validate;
	}	
		
}
