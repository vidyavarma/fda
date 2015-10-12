package com.nrg.lau.service.docmgt.sp;

import java.io.StringReader;
import java.util.ResourceBundle;

import org.apache.commons.digester.Digester;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.ExternalDocuments;
import com.nrg.lau.service.docmgt.DocMgtResponse;
import com.nrg.lau.utils.ReadConfig;

public class SharePoint{
	
	private static Logger log = Logger.getLogger(SharePoint.class);
	private static final String SQL_ACTIVITY_UPDATE_STRING = "UPDATE LAU_EXTERNAL_DOCUMENTS SET DOCUMENT_STATUS=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?," +
			"DOCUMENT_REJECTION_REASON=? WHERE EXTERNAL_DOCUMENT_ID =?";
	private DocMgtResponse sPResponse;
	
	public HttpClient getHttpClient() throws Exception {
		
		ResourceBundle resource = ReadConfig.getMessage();
		// HTTP client with authentication enabled
		HttpClient client = new HttpClient();
		client.getParams().setAuthenticationPreemptive(true);

		// Share Point authentication
		log.info("SharePoint Authentication -> " +
				resource.getString("SP_USER_NAME")+ ":"+resource.getString("SP_USER_PASS"));
		Credentials defaultcreds = new NTCredentials(resource.getString("SP_USER_NAME"),
				resource.getString("SP_USER_PASS"), ReadConfig.replaceBackslash(resource.getString("SP_HOST")),
				ReadConfig.replaceBackslash(resource.getString("SP_DOMAIN")));
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		return client;	
	}
	
	public boolean parseSPResponseRtnBoolean(String res) throws Exception {
		DocMgtResponse response = parseSPResponse(res);
		if(response.getSuccess().length() > 0) {
			return Boolean.valueOf(response.getSuccess());
		}
		return false;
	}

	public DocMgtResponse parseSPResponse(String res) throws Exception {
		
		sPResponse = new DocMgtResponse();
		setPResponse(res);
		
		log.info("FileId -> " 	+ sPResponse.getFileId());
		log.info("Message -> " 	+ sPResponse.getMessage());
		log.info("Success -> " 	+ sPResponse.getSuccess());
		log.info("FileName -> " + sPResponse.getFileName());
		
		if(sPResponse.getSuccess().length() > 0) {
			return sPResponse;
		}
		
		return new DocMgtResponse();
	}
	
	private void setPResponse(String returnString){
		
		try {
			Digester digester = new Digester();
	        digester.push(this);
	
	        digester.addObjectCreate( "Response", DocMgtResponse.class );
	
	        digester.addBeanPropertySetter( "Response/FileId", "fileId");
	        digester.addBeanPropertySetter( "Response/Message", "message");
	        digester.addBeanPropertySetter( "Response/Success", "success");
	        digester.addBeanPropertySetter( "Response/FileName", "fileName");
	        digester.addBeanPropertySetter( "Response/DocumentUrl", "fileUrl");
	
	        digester.addSetNext( "Response", "addResponse" );
	        digester.parse(new StringReader(returnString));
	        
		}catch (Exception e) {
			log.error(e);
		}  
        
	}
	
	public void addResponse(DocMgtResponse res ) {
	  	this.sPResponse =  res;
	}
	
	/*
	 * Update the LAU_EXTERNAL_DOCUMENTS table with status
	 * 
	 */
	public static int updateDocumentStatus(ExternalDocuments externalDocs, 
			SimpleJdbcTemplate template) {
	  
		int id = 0;
		try {
			log.info("ExternalDocuments -> " + externalDocs.toString());
			id = template.update(SQL_ACTIVITY_UPDATE_STRING,new Object[] {
		    		externalDocs.getStatus(),
		    		externalDocs.getUser(),
		    		externalDocs.getDstamp(),
		    		externalDocs.getRejectReason(),
		    		externalDocs.getDocID()});
		}catch (Exception e) {
		   log.info("Update document status failed for LAU_EXTERNAL_DOCUMENTS " + e.getMessage());
		   log.error(e);
		} 
		return id;
	}

}
