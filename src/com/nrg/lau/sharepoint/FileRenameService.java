package com.nrg.lau.sharepoint;

import java.util.ResourceBundle;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;
import com.nrg.lau.commons.Constants;
import com.nrg.lau.utils.ReadConfig;

public class FileRenameService {
	
	private static Logger log = Logger.getLogger(FileRenameService.class);
	
	public static boolean rename(String fileid, String fileName){
		
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
			//S.Abraham 11/05/2012 fileName= java.net.URLEncoder.encode( fileName, "UTF-8");added to handle "&" in file names
		//	fileName= java.net.URLEncoder.encode( fileName, "UTF-8");
			String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_FILE_RENAME_URL")) ;
					//+
		//	"?fileid=" + fileid + "&name=" + fileName;
			
			
			GetMethod method = new GetMethod(URIUtil.encodeQuery(serviceURL));
			NameValuePair pair1 = new NameValuePair( "fileid", fileid);
			log.info("Ian - I bet this is already encoded -> " + fileName);
			NameValuePair pair2 = new NameValuePair( "name", fileName );
			log.info("Ian - after  encoded -> " + fileName);
			NameValuePair[] pairs = new NameValuePair[] { pair1, pair2 };
			method.setQueryString( pairs );
			log.info("RenameServiceURL -> " + serviceURL);
			log.info("@@@ Encoded Query String"+method.getQueryString());
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
