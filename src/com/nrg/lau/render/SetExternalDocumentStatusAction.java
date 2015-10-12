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
import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;
import com.nrg.lau.utils.ReadConfig;

@SuppressWarnings("unused")
public class SetExternalDocumentStatusAction implements View {

	private static Logger log = Logger.getLogger(SetExternalDocumentStatusAction.class);
	private SimpleJdbcTemplate template;
	private DataSource ds;	
	private String status = "";
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	
	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.ds = dataSource;
		log.info("SetExternalDocumentStatusAction - Initialize db template()");
	}
	
	public String getContentType() {
		return "text/xml";
	}
	
	private static String SQL_ACTIVITY_UPDATE_STRING = "UPDATE LAU_EXTERNAL_DOCUMENTS SET DOCUMENT_STATUS=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?" +
			" WHERE EXTERNAL_DOCUMENT_ID =?";

	private int setDocumentStatus(long docId, String actStatus,String user,java.sql.Timestamp dstamp) {
	  
		int id = 0;
		long activityLogId = 0;
		try {
		   if(ds != null)
		   {
			    template = new SimpleJdbcTemplate(ds);
		   }

		    id = template.update(SQL_ACTIVITY_UPDATE_STRING,new Object[] {
		    		actStatus,userId,dt,docId
		    });
		}catch (Exception e) {
		   log.info("SetExternalDocumentStatusAction - setDocumentStatus() failed: " + e.getMessage());
		   log.error(e);
		} 
		  return id;
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		userId 	= CommonDAO.getUSERID(request);
		dt 		= CommonDAO.getTimestamp(template);
		log.info("ENTER - SetExternalDocumentStatusAction render()");

		long documentId = 0;		
		if (request.getParameter("docid") != null && 
				request.getParameter("docid").length() > 0) {
			
			documentId		= Long.valueOf((String) request.getParameter("docid"));
			int updateid 	= setDocumentStatus(documentId,"VIEWED",userId,dt);
			log.info("setDocumentStatus() -> inside counter() " + documentId );
			
		} else {
			response.setContentType(Constants.CONTENT_TYPE);
			response.setHeader("Cache-Control", Constants.CACHE_CONTROL);
			PrintWriter pw = response.getWriter();
			pw.write(XMLException.status("F", "DOCUMENT ID not found in request"));
			pw.flush();
			pw.close();
		}
		
		log.info("EXIT - SetExternalDocumentStatusAction render()");
			
	}	

}