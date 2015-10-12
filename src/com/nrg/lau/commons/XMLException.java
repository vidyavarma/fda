package com.nrg.lau.commons;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class XMLException {
	
	//This method will return Error details in XML
	//Status is identified from the node value STATUS
	//S:Success 
	//F:Failure
	private static Logger log 	= Logger.getLogger(XMLException.class);
	public static String xmlError(Exception e, String message)	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Constants.FLEX_XML_ROWSET);
		buffer.append(Constants.FLEX_XML_DATAROOT);
		
		buffer.append("<STATUS>F</STATUS>");
		buffer.append("<STATUS_MESSAGE>" + message  + "</STATUS_MESSAGE>");
		buffer.append("<ERROR_CAUSE>" + e.getCause() + "</ERROR_CAUSE>");
		buffer.append("<ERROR_MESSAGE>" + e.getMessage() + "</ERROR_MESSAGE>");		
		buffer.append("<ERROR_STRING>" + e.toString() + "</ERROR_STRING>");
				
		buffer.append(Constants.FLEX_XML_END_DATAROOT);
		buffer.append(Constants.FLEX_XML_END_ROWSET);
		log.info(buffer);
		return buffer.toString();
	}
	
	public static String xmlErrorSiebel(Exception e, String message)	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Constants.FLEX_XML_ROWSET);
		buffer.append(Constants.FLEX_XML_DATAROOT);
		
		buffer.append("<STATUS>F</STATUS>");
		buffer.append("<STATUS_MESSAGE>" + message  + "</STATUS_MESSAGE>");
		buffer.append("<ERROR_CAUSE>" + e.getCause() + "</ERROR_CAUSE>");
		buffer.append("<ERROR_MESSAGE>" + e.getMessage() + "</ERROR_MESSAGE>");		
		buffer.append("<ERROR_STRING/>");
				
		buffer.append(Constants.FLEX_XML_END_DATAROOT);
		buffer.append(Constants.FLEX_XML_END_ROWSET);
		log.info(buffer);
		return buffer.toString();
	}
	
	//This method will return status description in XML
	//Status is identified from the node value STATUS
	//S:Success 
	//F:Failure
	public static String status(String status)	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Constants.FLEX_XML_ROWSET);
		buffer.append(Constants.FLEX_XML_DATAROOT);
		
		buffer.append("<STATUS>S</STATUS>");
		buffer.append("<STATUS_MESSAGE>" + status  + "</STATUS_MESSAGE>");
		buffer.append("<ERROR_CAUSE/>");
		buffer.append("<ERROR_MESSAGE/>");		
		buffer.append("<ERROR_STRING/>");
				
		buffer.append(Constants.FLEX_XML_END_DATAROOT);
		buffer.append(Constants.FLEX_XML_END_ROWSET);
		
		return buffer.toString();
	}
	
	public static String status(String status, String statusMsg)	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Constants.FLEX_XML_ROWSET);
		buffer.append(Constants.FLEX_XML_DATAROOT);
		
		buffer.append("<STATUS>" + status + "</STATUS>");
		buffer.append("<STATUS_MESSAGE>" + statusMsg  + "</STATUS_MESSAGE>");
		buffer.append("<ERROR_CAUSE/>");
		buffer.append("<ERROR_MESSAGE/>");		
		buffer.append("<ERROR_STRING/>");
				
		buffer.append(Constants.FLEX_XML_END_DATAROOT);
		buffer.append(Constants.FLEX_XML_END_ROWSET);
		
		return buffer.toString();
	}
	
	public static String status(String status, long reportId)	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Constants.FLEX_XML_ROWSET);
		buffer.append(Constants.FLEX_XML_DATAROOT);
		
		buffer.append("<STATUS>S</STATUS>");
		buffer.append("<REPORT_ID>" +reportId+ "</REPORT_ID>");
		buffer.append("<STATUS_MESSAGE>" + status  + "</STATUS_MESSAGE>");
		buffer.append("<ERROR_CAUSE/>");
		buffer.append("<ERROR_MESSAGE/>");		
		buffer.append("<ERROR_STRING/>");
				
		buffer.append(Constants.FLEX_XML_END_DATAROOT);
		buffer.append(Constants.FLEX_XML_END_ROWSET);
		
		return buffer.toString();
	}
	
	public static String status(String status, long reportId, String lauReportId)	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Constants.FLEX_XML_ROWSET);
		buffer.append(Constants.FLEX_XML_DATAROOT);
		
		buffer.append("<STATUS>S</STATUS>");
		buffer.append("<REPORT_ID>" +reportId+ "</REPORT_ID>");
		buffer.append("<LAU_REPORT_ID>" +lauReportId+ "</LAU_REPORT_ID>");
		buffer.append("<STATUS_MESSAGE>" + status  + "</STATUS_MESSAGE>");
		buffer.append("<ERROR_CAUSE/>");
		buffer.append("<ERROR_MESSAGE/>");		
		buffer.append("<ERROR_STRING/>");
				
		buffer.append(Constants.FLEX_XML_END_DATAROOT);
		buffer.append(Constants.FLEX_XML_END_ROWSET);
		
		return buffer.toString();
	}
	
	public static void rtnXMLException(HttpServletResponse response, Exception e) {
		try {
			response.setContentType(Constants.CONTENT_TYPE);
			response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
			PrintWriter pw = response.getWriter();
			pw.write(XMLException.status(xmlError(e,e.toString())));
			pw.flush();
			pw.close();		
		}catch (Exception err) {
			log.error(err);
		}
	}
	
	public static void rtnStatus(HttpServletResponse response, String status, String msg) {
		try {
			response.setContentType(Constants.CONTENT_TYPE);
			response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
			PrintWriter pw = response.getWriter();
			pw.write(XMLException.status(status,msg));
			pw.flush();
			pw.close();		
		}catch (Exception err) {
			log.error(err);
		}
	}
}
