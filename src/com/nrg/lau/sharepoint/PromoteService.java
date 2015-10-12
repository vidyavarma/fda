package com.nrg.lau.sharepoint;

import java.util.ResourceBundle;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import com.nrg.lau.utils.ReadConfig;

public class PromoteService {
	
	private static Logger log = Logger.getLogger(PromoteService.class);
	
	/**
	 * <CASE_ID>2011SA000323</CASE_ID><FILE_IDS>cdc36ef7-f5fa-4cc4-92f6-be432e55f8fb</FILE_IDS>
	 	read this XML, parse, get caseId, and fileIds
	 	if  FILE_IDS NOT BLANk, call
		can submit multiple files at once to the same case by comma separating the fileId array:
		http://sanofi.dev.accenda.eu/_vti_bin/Sanofi/Primo.svc/Promote?fileId=fileIds&caseId=caseId
		if SharePoint load fails - return message = Report promoted, but file upload to SharePoint failed. 
	**/		
	
	public static Boolean promoteReport(String message) throws Exception{
		
		ResourceBundle resource 	= ReadConfig.getMessage();
		//HttpClient client 			= SPAuthentication.sPAuthenticate(resource);
		boolean status = false;
		
		if(getCaseID(message).length() > 0 && 
				getfileID(message).length() > 0) {
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
			
			String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_PROMOTE_REPORT_URL")) +
			"?fileid=" + getfileID(message) + "&caseid=" + getCaseID(message);
			log.info("promoteReport -> " + serviceURL);
			
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
		}
		
		return status;
	}
	
	private static String getCaseID(String message) {
		if(message.indexOf("<CASE_ID>") != -1) {
			return message.substring(message.indexOf("<CASE_ID>") + 9, message.indexOf("</CASE_ID>"));
		}
		return "";
	}
	
	private static String getfileID(String message) {
		if(message.indexOf("<FILE_IDS>") != -1) {
			return message.substring(message.indexOf("<FILE_IDS>") + 10, message.indexOf("</FILE_IDS>"));
		}
		return "";
	}
}
