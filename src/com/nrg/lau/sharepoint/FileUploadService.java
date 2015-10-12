package com.nrg.lau.sharepoint;

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

import com.nrg.lau.utils.ReadConfig;

public class FileUploadService {
	
	private static Logger log = Logger.getLogger(FileUploadService.class);
	
	public static SPResponse uploadACK(HttpServletRequest request,
			String fileName, String fileContent, String lauReportId) throws Exception{
		
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

		String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_FILE_UPLOAD_URL")) ;
	//	"?filename=" + fileName + "&reportid=" + lauReportId;
		log.info("upload URL-> " + serviceURL);
		
		PostMethod method = new PostMethod(URIUtil.encodeQuery(serviceURL));

		NameValuePair pair1 = new NameValuePair( "reportid", lauReportId );
		log.info("Ian - I bet this is already encoded -> " + fileName);
		NameValuePair pair2 = new NameValuePair( "filename", fileName );
		log.info("Ian - upload after  encoded -> " + fileName);
		NameValuePair[] pairs = new NameValuePair[] { pair1, pair2 };
		method.setQueryString( pairs );
		byte[] theByteArray = fileContent.getBytes();
		ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(theByteArray,"text/xml");
		method.setRequestEntity(requestEntity);
		client.executeMethod(method);
		
		SPUtil sPUtil = new SPUtil();
		SPResponse sPResponse = sPUtil.getResponse(method.getResponseBodyAsString());
		log.info("FileId -> " + sPResponse.getFileId());
		log.info("Message -> " + sPResponse.getMessage());
		log.info("Success -> " + sPResponse.getSuccess());
		log.info("FileName -> " + sPResponse.getFileName());
		
		if(sPResponse.getSuccess().length() > 0) {
			return sPResponse;
		}
		
		return new SPResponse();		
	}
	
}
