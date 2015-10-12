package com.nrg.lau.sharepoint;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.ExternalDocuments;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.utils.ReadConfig;


public class SPUtil {
	
	private static Logger log = Logger.getLogger(SPUtil.class);
	private SPResponse sPResponse;
	public static int updateDocumentStatus(ExternalDocuments externalDocs,
			SimpleJdbcTemplate template,DataSource ds) {
	  
		int id = 0;
		try {
		   if(ds != null)
		   {
			    template = new SimpleJdbcTemplate(ds);
		   }
		    id = template.update(SQL_ACTIVITY_UPDATE_STRING,new Object[] {
		    		externalDocs.getStatus(),
		    		externalDocs.getUser(),
		    		externalDocs.getDstamp(),
		    		externalDocs.getRejectReason(),
		    		externalDocs.getDocID()});
		}catch (Exception e) {
		   log.info("SPUtil - setDocumentStatus() failed: " + e.getMessage());
		   log.error(e);
		} 
		return id;
	}
	
	public static int insertDocumentStatus(ExternalDocuments externalDocs,
			SimpleJdbcTemplate template,DataSource ds) {
	  
		int id = 0;
		try {
		   if(ds != null)	{
			    template = new SimpleJdbcTemplate(ds);
		   }
		   long externalDocumentId = CommonDAO.getPrimaryKey(template);
		    
		   id = template.update(SQL_ACTIVITY_INSERT_STRING,new Object[] {
		    		externalDocs.getStatus(),
		    		externalDocs.getUser(),
		    		externalDocs.getDstamp(),
		    		externalDocs.getRejectReason(),
		    		externalDocs.getObjectID(),
		    		externalDocs.getDocumentName(),
		    		externalDocs.getDocumentName(),
		    		externalDocs.getDocDescription(),
		    		externalDocs.getFileURL(),
		    		externalDocs.getGroupID(),
		    		externalDocumentId});
		}catch (Exception e) {
		   log.info("SPUtil - setDocumentStatus() failed: " + e.getMessage());
		   log.error(e);
		} 
		return id;
	}
	
	private static String SQL_ACTIVITY_UPDATE_STRING = "UPDATE LAU_EXTERNAL_DOCUMENTS SET DOCUMENT_STATUS=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?," +
	"DOCUMENT_REJECTION_REASON=? WHERE EXTERNAL_DOCUMENT_ID =?";
	
	private static String SQL_ACTIVITY_INSERT_STRING = "INSERT INTO LAU_EXTERNAL_DOCUMENTS (DOCUMENT_STATUS,UPDATE_USER_ID,UPDATE_TIMESTAMP," +
			"DOCUMENT_REJECTION_REASON,OBJECT_ID,DOCUMENT_NAME,FILE_NAME,DOCUMENT_DESCRIPTION,DOCUMENT_URL,USER_GROUP_ID,EXTERNAL_DOCUMENT_ID) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	
	public SPResponse getResponse(String returnString) throws Exception{
		
		log.info("returnString -> " + returnString);
		sPResponse = new SPResponse();
		setPResponse(returnString);
		return sPResponse;
	}
	
	private void setPResponse(String returnString){
		
		try {
			Digester digester = new Digester();
	        digester.push(this);
	
	        digester.addObjectCreate( "Response", SPResponse.class );
	
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
	
	public void addResponse(SPResponse res ) {
	  	this.sPResponse =  res;
	}
	
	public static InputStream getSharePointDocument(String sPURL) 
	throws Exception{

		ResourceBundle resource 	= ReadConfig.getMessage();
		
		// HTTP client with authentication enabled
		HttpClient client = new HttpClient();
		client.getParams().setAuthenticationPreemptive(true);
		
		// SharePoint authentication
		Credentials defaultcreds = new NTCredentials(
			resource.getString("SP_USER_NAME"), resource.getString("SP_USER_PASS"), 
			ReadConfig.replaceBackslash(resource.getString("SP_HOST")), ReadConfig.replaceBackslash(resource.getString("SP_DOMAIN")));
		client.getState().setCredentials(AuthScope.ANY,
			defaultcreds);
		
		sPURL = appendPortToURL(sPURL);
		
		GetMethod get = new GetMethod(URIUtil.encodeQuery(sPURL));
		log.info("SP_DOCUMENT_ROOT  ->  " + sPURL +
			"SP_DOMAIN  -> " + ReadConfig.replaceBackslash(resource.getString("SP_DOMAIN")) + 
			"SP_HOST  ->  " + ReadConfig.replaceBackslash(resource.getString("SP_HOST")));
		client.executeMethod(get);
		
		if(get.getStatusCode() == 404) {
			log.info("404 NOT FOUND -> " + get.getStatusCode());
			throw new Exception("File Not Found");
		}
		
		return get.getResponseBodyAsStream();
	}
	
	public static String appendPortToURL(String url) {
		
		String rtnURL = url;
		try {
			ResourceBundle resource 	= ReadConfig.getMessage();
			if(resource.getString("SP_APPEND_PORT_TO_URL").equalsIgnoreCase("Y")) {
				 if(url.indexOf(resource.getString("SP_DOMAIN")) != -1) {
					 if(url.indexOf(resource.getString("SP_PORT_NUMBER")) == -1) {
						 log.info("rtnURL Before replace -> " + rtnURL);
						 rtnURL = url.replace(resource.getString("SP_DOMAIN"), resource.getString("SP_DOMAIN") 
		                		 + ":" + resource.getString("SP_PORT_NUMBER"));
		                 log.info("rtnURL After replace -> " + rtnURL);
					 }else {
						 log.info("url.indexOf(resource.getString(SP_PORT_NUMBER)) != -1");
					 }
				 }else {
					 log.info("sPURL.indexOf(resource.getString(SP_DOMAIN) = -1");
				 }
			}else {
				log.info("resource.getString(SP_APPEND_PORT_TO_URL) = N -> " 
						+ resource.getString("SP_APPEND_PORT_TO_URL"));
			}
		}catch (Exception e) {
			log.error(e);
		}	
		
		return rtnURL;
	}
}
