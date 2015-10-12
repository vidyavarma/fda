package com.nrg.lau.service.docmgt.sp;

import java.io.InputStream;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import com.nrg.lau.service.docmgt.DocMgtParameters;
import com.nrg.lau.service.docmgt.DocMgtResponse;
import com.nrg.lau.service.docmgt.DocMgtService;
import com.nrg.lau.utils.ReadConfig;

public class SPDocMgtServiceImpl extends SharePoint implements DocMgtService<DocMgtResponse> {
	
	private static Logger log = Logger.getLogger(SPDocMgtServiceImpl.class);
	
	@Override
	public DocMgtResponse upload(HttpServletRequest request, DocMgtParameters params)
			throws Exception {
		
		log.info(params.toString());
		ResourceBundle resource = ReadConfig.getMessage();
		HttpClient client = getHttpClient();
		
		String serviceURL = ReadConfig.replaceBackslash(
				resource.getString("SP_WEB_SERVICE_FILE_UPLOAD_URL")) ;
		log.info("upload URL-> " + serviceURL);
		
		PostMethod method = new PostMethod(URIUtil.encodeQuery(serviceURL));
		NameValuePair[] pairs = new NameValuePair[] { 
			new NameValuePair( "reportid", params.getLauReportId()), 
			new NameValuePair( "filename", params.getFileName())
		};
		method.setQueryString( pairs );
		byte[] theByteArray = params.getFileContent().getBytes();
		ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(theByteArray,"text/xml");
		method.setRequestEntity(requestEntity);
		client.executeMethod(method);
		
		return parseSPResponse(method.getResponseBodyAsString());		
		
	}

	@Override
	public boolean detach(DocMgtParameters params) {
		
		boolean rtnVal = false;
		try {
			
			log.info(params.toString());
			ResourceBundle resource = ReadConfig.getMessage();
			HttpClient client = getHttpClient();
						
			String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_DETACH_URL")) +
			"?fileid=" + params.getFileID() + "&groupid=" + params.getGroupID() + "&caseid=" + params.getCaseID();
			log.info("DetachServiceURL -> " + serviceURL);
			
			GetMethod method = new GetMethod(URIUtil.encodeQuery(serviceURL));
			client.executeMethod(method);	
			
			return parseSPResponseRtnBoolean(method.getResponseBodyAsString());
			
		}catch (Exception e) {
			log.error(e);
		}	
		
		return rtnVal;
	}

	@Override
	public InputStream retrieveDocument(DocMgtParameters params)
			throws Exception {
		
		ResourceBundle resource = ReadConfig.getMessage();
		HttpClient client = new SharePoint().getHttpClient();
		String url = params.getUrl();
		
		if(resource.getString("SP_APPEND_PORT_TO_URL").equalsIgnoreCase("Y")) {
			if(url.indexOf(resource.getString("SP_DOMAIN")) != -1) {
				if(url.indexOf(resource.getString("SP_PORT_NUMBER")) == -1) {
					 log.info("URL before replace -> " + url);
					 url = url.replace(resource.getString("SP_DOMAIN"), resource.getString("SP_DOMAIN") 
	                		 + ":" + resource.getString("SP_PORT_NUMBER"));
	                 log.info("URL after replace -> " + url);
				 }else { log.info("url.indexOf(resource.getString(SP_PORT_NUMBER)) != -1");	}
			 }else { log.info("url.indexOf(resource.getString(SP_DOMAIN) = -1"); }
		}else {
			log.info("SP_APPEND_PORT_TO_URL = N -> " + resource.getString("SP_APPEND_PORT_TO_URL"));
		}
		
		GetMethod get = new GetMethod(URIUtil.encodeQuery(url));
		log.info("SP_DOCUMENT_ROOT  ->  " + url);
		client.executeMethod(get);
		
		if(get.getStatusCode() == 404) {
			log.info("404 NOT FOUND -> " + get.getStatusCode());
			throw new Exception("File Not Found");
		}
		
		return get.getResponseBodyAsStream();
	}

	@Override
	public boolean rename(DocMgtParameters params) {
		
		boolean rtnVal = false;
		try {
			
			log.info(params.toString());
			ResourceBundle resource = ReadConfig.getMessage();
			HttpClient client = getHttpClient();
			
			String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_FILE_RENAME_URL")) ;
					
			GetMethod method = new GetMethod(URIUtil.encodeQuery(serviceURL));
			NameValuePair[] pairs = new NameValuePair[] { 
				new NameValuePair( "fileid", params.getFileID()), 
				new NameValuePair( "name", params.getFileName()) 
			};
			method.setQueryString( pairs );
			log.info("RenameServiceURL -> " + serviceURL);
			log.info("Encoded Query String" + method.getQueryString());
			client.executeMethod(method);	
			
			return parseSPResponseRtnBoolean(method.getResponseBodyAsString());
			
		}catch (Exception e) {
			log.error(e);
		}	
		
		return rtnVal;
	}

	@Override
	public boolean newGroup(DocMgtParameters params) {
		
		boolean rtnVal = false;
		try {
			
			log.info(params.toString());
			ResourceBundle resource = ReadConfig.getMessage();
			HttpClient client = getHttpClient();
			
			String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_NEW_GROUP_URL")) +
			"?name=" + params.getGrpName();
			log.info("New Group -> " + serviceURL);
			
			GetMethod method = new GetMethod(URIUtil.encodeQuery(serviceURL));
			client.executeMethod(method);
			
			return parseSPResponseRtnBoolean(method.getResponseBodyAsString());
			
		}catch (Exception e) {
			log.error(e);
		}	
		
		return rtnVal;
	}

	@Override
	public DocMgtResponse outBound(DocMgtParameters params) throws Exception {
		
		log.info(params.toString());
		ResourceBundle resource = ReadConfig.getMessage();
		HttpClient client = getHttpClient();	

		String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_OUTBOUND_URL")) ;
		log.info("OutboundService -> " + serviceURL);
		
		PostMethod method = new PostMethod(URIUtil.encodeQuery(serviceURL));		
		NameValuePair[] pairs = new NameValuePair[] { 
			new NameValuePair( "filename",params.getFileName())
		};
		method.setQueryString( pairs );
		
		log.info("RenameServiceURL -> " + serviceURL);
		log.info("Encoded Query String" + method.getQueryString());

		byte[] theByteArray = params.getFileContent().getBytes();
		ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(theByteArray,"text/xml");
		method.setRequestEntity(requestEntity);
		client.executeMethod(method);
		
		return parseSPResponse(method.getResponseBodyAsString());
		
	}

	@Override
	public boolean reject(DocMgtParameters params) {
		
		boolean rtnVal = false;
		try {
			
			log.info(params.toString());
			ResourceBundle resource = ReadConfig.getMessage();
			HttpClient client = getHttpClient();	
			
			String serviceURL = ReadConfig.replaceBackslash(resource.getString("SP_WEB_SERVICE_REJECT_URL")) +
			"?fileid=" + params.getFileID();
			log.info("Reject -> " + serviceURL);
			
			GetMethod method = new GetMethod(URIUtil.encodeQuery(serviceURL));
			client.executeMethod(method);
			
			return parseSPResponseRtnBoolean(method.getResponseBodyAsString());
			
		}catch (Exception e) {
			log.error(e);
		}	
		
		return rtnVal;
	}	

}