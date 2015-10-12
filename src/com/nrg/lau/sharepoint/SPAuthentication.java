package com.nrg.lau.sharepoint;

import java.util.ResourceBundle;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.log4j.Logger;

import com.nrg.lau.utils.ReadConfig;

public class SPAuthentication {
	
	private static Logger log = Logger.getLogger(SPAuthentication.class);
	public static HttpClient sPAuthenticate(ResourceBundle resource)  throws Exception{
				
			// HTTP client with authentication enabled
			HttpClient client = new HttpClient();
			client.getParams().setAuthenticationPreemptive(true);
	
			// Share Point authentication
			log.info("SPAuthentication -> "+
					resource.getString("SP_USER_NAME")+ ":"+resource.getString("SP_USER_PASS"));
			Credentials defaultcreds = new NTCredentials(resource.getString("SP_USER_NAME"),
					resource.getString("SP_USER_PASS"), ReadConfig.replaceBackslash(resource.getString("SP_HOST")),
					ReadConfig.replaceBackslash(resource.getString("SP_DOMAIN")));
			client.getState().setCredentials(AuthScope.ANY, defaultcreds);
			return client;	
	}
}
