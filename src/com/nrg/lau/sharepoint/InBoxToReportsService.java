package com.nrg.lau.sharepoint;

import java.util.ResourceBundle;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import com.nrg.lau.utils.ReadConfig;

public class InBoxToReportsService {
	
	private static Logger log	= Logger.getLogger(InBoxToReportsService.class);
	
	public static String rewriteSPDocumentURL(String lauReportID, 
			String fileName, String url) {
		
		String tempURL = "";
		if(lauReportID.length() > 0 && fileName.length() > 0){
			try {
				tempURL = 	ReadConfig.replaceBackslash(ReadConfig.getMessage().getString("SP_DOCUMENT_ROOT"));
				tempURL	=	tempURL + "/" + String.valueOf(lauReportID) + "/" + fileName;
				log.info("rewriteSPDocumentURL -> " + tempURL);
				return	tempURL;
				
			}catch (Exception e) {
				log.error(e);
			}	
		}		
		
		return url;
	}
	
	public static SPResponse moveSPDocFromInBoxToReports(String ObjectID, 
			String lauReportID) throws Exception{
		try {
			if(lauReportID.length() > 0 && ObjectID.length() > 0){
				String temp = callSPServiceForInBoxToReports(ObjectID, lauReportID);
				SPUtil sPUtil = new SPUtil();
				SPResponse sPResponse = sPUtil.getResponse(temp);
				log.info("FileId -> " + sPResponse.getFileId());
				log.info("Message -> " + sPResponse.getMessage());
				log.info("Success -> " + sPResponse.getSuccess());
				if(sPResponse.getSuccess().length() > 0) {
					return sPResponse;
				}				
			}
		}catch (Exception e) {
			log.info("moveSPDocFromInBoxToReports service failed " + e);
		}	
		
		return new SPResponse();
	}
	
	public static String callSPServiceForInBoxToReports(String ObjectID, 
			String lauReportID) throws Exception{
		
		ResourceBundle resource 	= ReadConfig.getMessage();
		HttpClient client = SPAuthentication.sPAuthenticate(resource);
		
		GetMethod get = new GetMethod(URIUtil.encodeQuery(ReadConfig.replaceBackslash(resource.getString("SP_SERVICE_REPORT_DOC_MOV_URL")) 
				+ "?fileid=" + ObjectID + "&reportid=" + lauReportID) );
		
		log.info("SP_SERVICE_REPORT_DOC_MOV_URL  ->  " + ReadConfig.replaceBackslash(resource.getString("SP_SERVICE_REPORT_DOC_MOV_URL")) +
				"SP_DOMAIN  -> " + ReadConfig.replaceBackslash(resource.getString("SP_DOMAIN")) + 
				"SP_HOST  ->  " + ReadConfig.replaceBackslash(resource.getString("SP_HOST")));		
		log.info(URIUtil.encodeQuery(ReadConfig.replaceBackslash(resource.getString("SP_SERVICE_REPORT_DOC_MOV_URL")) 
				+ "?fileid=" + ObjectID + "&reportid=" + lauReportID));
		
		client.executeMethod(get);		
		log.info("getResponseBodyAsString() -> " + get.getResponseBodyAsString());
		return get.getResponseBodyAsString();
	}
}
