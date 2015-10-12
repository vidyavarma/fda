package com.nrg.lau.render;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.ResourceBundle;

import java.io.File;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.View;
import org.apache.commons.httpclient.util.URIUtil;

import com.nrg.lau.beans.ExternalDocuments;
import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.e2b.E2BFileOutput;
import com.nrg.lau.e2b.E2BUtil;
import com.nrg.lau.sharepoint.SPUtil;
import com.nrg.lau.utils.ReadConfig;

@SuppressWarnings("unused")
public class GetE2BViewerAction implements View {

	private static Logger log = Logger.getLogger(GetE2BViewerAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	
	private String rejectReason 	= "";
	private String sPURL 			= "";
	private String status 			= "";
	private java.sql.Timestamp dt  	= null; 
	private String userId			= "";
	
	public void setDataSource(DataSource dataSource) {
		this.template 	= new SimpleJdbcTemplate(dataSource);
		this.ds 		= dataSource;
		log.info("GetE2BViewerAction - Initialize db template()");
	}
	
	public String getContentType() {
		return "text/xml";
	}
	
	private static String SQL_ACTIVITY_UPDATE_STRING = "UPDATE LAU_EXTERNAL_DOCUMENTS SET DOCUMENT_STATUS=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?," +
			"DOCUMENT_REJECTION_REASON=? WHERE EXTERNAL_DOCUMENT_ID =?";

	private int setDocumentStatus(long docId, String actStatus, String user, 
			java.sql.Timestamp dstamp) {
	  
		int id = 0;
		long activityLogId = 0;
		try {
		   if(ds != null)
		   {
			    template = new SimpleJdbcTemplate(ds);
		   }
		    id = template.update(SQL_ACTIVITY_UPDATE_STRING,new Object[] {
		    		actStatus,userId,dt,rejectReason,docId});
		}catch (Exception e) {
		   log.info("GetE2BViewerAction - setDocumentStatus() failed: " + e.getMessage());
		   log.error(e);
		} 
		return id;
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try {
			userId 	= CommonDAO.getUSERID(request);
			dt 		= CommonDAO.getTimestamp(template);
			log.info("ENTER - GetE2BViewerAction render()");
	
			long documentId = 0;
			if (request.getParameter("docid") != null && 
					request.getParameter("docid").length() > 0) {
				documentId = Long.valueOf((String) request.getParameter("docid"));
				log.info("ENTER - GetE2BViewerAction render() documentId -> " + documentId);
			}
			if (request.getParameter("e2bRejectReason") != null) {
				rejectReason = request.getParameter("e2bRejectReason");
			}
			if (request.getParameter("fileName") != null && 
					request.getParameter("fileURL") != null) {
				
				String fileName = request.getParameter("fileName");
				//sPURL			= request.getParameter("fileURL");
				//String fileName = request.getParameter("fileName");
                sPURL            = request.getParameter("fileURL");
                sPURL			 = SPUtil.appendPortToURL(sPURL);
                log.info("sPURL 1 -> " + sPURL);
                log.info("Request Parameter - fileName  " + fileName);
				log.info("Request Parameter - fileName  " + fileName);
				
				if (request.getParameter("format") != null) {
					
					String fileFormat = request.getParameter("format");
					log.info("Request Parameter - format  " + fileFormat);
					if (fileFormat.equalsIgnoreCase("e2bpdf")) {
	
						E2BFileOutput.outputPDF(fileName, response, sPURL);
	
					}	else if (fileFormat.equalsIgnoreCase("e2bhtml")) {
	
						E2BFileOutput.outputHTML(fileName, response, sPURL);
	
					}	else if (fileFormat.equalsIgnoreCase("e2brtf")) {
						
						E2BFileOutput.outputRTF(fileName, response, sPURL);
						
					}	else if ( (fileFormat.equalsIgnoreCase("e2breject")) || (fileFormat.equalsIgnoreCase("otherreject"))) {
						
						ExternalDocuments externalDocs = new ExternalDocuments();
						externalDocs.setStatus("REJECTED");
						externalDocs.setUser(userId);
						externalDocs.setDstamp(dt);
						externalDocs.setRejectReason(request.getParameter("e2bRejectReason") != null ?  
								request.getParameter("e2bRejectReason") : "");
						externalDocs.setDocID(documentId);
						externalDocs.setObjectID(request.getParameter("fileid") != null ?  
								request.getParameter("fileid") : "");
						externalDocs.setFileID(request.getParameter("fileid") != null ?  
								request.getParameter("fileid") : "");
						externalDocs.setFileURL(request.getParameter("fileURL") != null ?  
								request.getParameter("fileURL") : "");
						externalDocs.setGroupID(request.getParameter("groupid") != null ?  
								request.getParameter("groupid") : "");
						E2BFileOutput.outputE2BReject(fileName, sPURL, externalDocs, template, ds, request, response);						
										
						
					}	
				
				}	else {
					// if it's not a e2b file it will read the file from share point
					// and the user will be able to open or save the file in his/her machine
					E2BFileOutput.outputOthers(fileName, response, sPURL);
				}			
							
			} else {
				response.setContentType(Constants.CONTENT_TYPE);
				response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
				PrintWriter pw = response.getWriter();
				pw.write(XMLException.status("F", "File Name not found in request"));
				pw.flush();
				pw.close();
			}
			
			log.info("EXIT - GetE2BViewerAction render()");
		
		}catch (Exception e) {
			E2BUtil.getE2BError(response, e);
		}	
			
	}

}