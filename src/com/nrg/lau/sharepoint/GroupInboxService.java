package com.nrg.lau.sharepoint;

import java.io.ByteArrayOutputStream;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import com.nrg.lau.sharepoint.SPResponse;
import com.nrg.lau.sharepoint.SPUtil;
import com.nrg.lau.utils.ReadConfig;

public class GroupInboxService {
	
	private static Logger log = Logger.getLogger(GroupInboxService.class);
	
	public static SPResponse uploadToGroupInbox(String groupId,
			String fileName, ByteArrayOutputStream fileContent) throws Exception{
		
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

		String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_GROUP_INBOX_URL"));		
		log.info("uploadToGroupInbox -> " + serviceURL);
		
		PostMethod method = new PostMethod(URIUtil.encodeQuery(serviceURL));
		NameValuePair pair1 = new NameValuePair( "groupid",  groupId);
		log.info("Ian - I bet this is already encoded -> " + fileName);
		NameValuePair pair2 = new NameValuePair( "filename", fileName);
		log.info("Ian - after  encoded -> " + fileName);
		NameValuePair[] pairs = new NameValuePair[] { pair1, pair2 };
		method.setQueryString( pairs );;
		ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(fileContent.toByteArray(),"text/rtf");
		method.setRequestEntity(requestEntity);
		client.executeMethod(method);
		
		SPUtil sPUtil = new SPUtil();
		SPResponse sPResponse = sPUtil.getResponse(method.getResponseBodyAsString());
		log.info("FileId -> " + sPResponse.getFileId());
		log.info("Message -> " + sPResponse.getMessage());
		log.info("Success -> " + sPResponse.getSuccess());
		log.info("fileUrl -> " + sPResponse.getFileUrl());
		
		return sPResponse;
	}
	
	public static SPResponse uploadToGroupInbox(HttpServletRequest request,
			String fileName, String fileContent) throws Exception{
		
		if(request.getParameter("groupid") != null) {
			
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

			String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_GROUP_INBOX_URL"));
			
		//	"?filename=" + fileName + "&groupid=" + request.getParameter("groupid");
			log.info("uploadToGroupInbox -> " + serviceURL);
			
			PostMethod method = new PostMethod(URIUtil.encodeQuery(serviceURL));
			NameValuePair pair1 = new NameValuePair( "groupid",  request.getParameter("groupid") );
			log.info("Ian - I bet this is already encoded -> " + fileName);
			NameValuePair pair2 = new NameValuePair( "filename", fileName );
			log.info("Ian - after  encoded -> " + fileName);
			NameValuePair[] pairs = new NameValuePair[] { pair1, pair2 };
			method.setQueryString( pairs );;
			byte[] theByteArray = fileContent.getBytes();
			ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(theByteArray,"text/xml");
			method.setRequestEntity(requestEntity);
			client.executeMethod(method);
			
			SPUtil sPUtil = new SPUtil();
			SPResponse sPResponse = sPUtil.getResponse(method.getResponseBodyAsString());
			log.info("FileId -> " + sPResponse.getFileId());
			log.info("Message -> " + sPResponse.getMessage());
			log.info("Success -> " + sPResponse.getSuccess());
			
			return sPResponse;
			
		}else {
			log.info("groupid parameter not found in request");
		}
		return new SPResponse();
	}
	
}
