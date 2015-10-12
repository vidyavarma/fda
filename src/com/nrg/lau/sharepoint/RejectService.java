package com.nrg.lau.sharepoint;

import java.util.ResourceBundle;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import com.nrg.lau.utils.ReadConfig;

public class RejectService {
	
	private static Logger log = Logger.getLogger(RejectService.class);
	
	public static boolean reject(String fileid){
		
		boolean rtnVal = false;
		try {
			ResourceBundle resource 	= ReadConfig.getMessage();
			HttpClient client = new HttpClient();
			client.getParams().setAuthenticationPreemptive(true);
	
			// Share Point authentication
			log.info("SPAuthentication -> "+
					resource.getString("SP_USER_NAME")+ ":"+resource.getString("SP_USER_PASS"));
			Credentials defaultcreds = new NTCredentials(resource.getString("SP_USER_NAME"),
					resource.getString("SP_USER_PASS"), ReadConfig.replaceBackslash(resource.getString("SP_HOST")),
					ReadConfig.replaceBackslash(resource.getString("SP_DOMAIN")));
			client.getState().setCredentials(AuthScope.ANY, defaultcreds);
			
			String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_REJECT_URL")) +
			"?fileid=" + fileid;
			log.info("Reject -> " + serviceURL);
			
			GetMethod method = new GetMethod(URIUtil.encodeQuery(serviceURL));
			client.executeMethod(method);
			
			SPUtil sPUtil = new SPUtil();
			SPResponse sPResponse = sPUtil.getResponse(method.getResponseBodyAsString());
			log.info("FileId -> " + sPResponse.getFileId());
			log.info("Message -> " + sPResponse.getMessage());
			log.info("Success -> " + sPResponse.getSuccess());
			if(sPResponse.getSuccess().length() > 0) {
				return Boolean.valueOf(sPResponse.getSuccess());
			}
		}catch (Exception e) {
			log.error(e);
		}	
		
		return rtnVal;
	}
	
}
