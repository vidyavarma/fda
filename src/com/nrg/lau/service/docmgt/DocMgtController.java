package com.nrg.lau.service.docmgt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nrg.lau.beans.ExternalDocuments;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;

@Controller
public class DocMgtController {
	
	private static Logger log = Logger.getLogger(DocMgtController.class);
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	//viewReportAttachment
	@RequestMapping(value={"/viewDocument","/viewReportAttachment"})	 
	public void viewDocument(HttpServletRequest request, 
			HttpServletResponse response) {
		
		try {
			
			DocMgtService<?> doc = null;
			doc = DocMgtLoader.getDocMgtService();
			DocMgtParameters params = new DocMgtParameters();
			
			//For dual implementation, pass/set Object Id to DocMgtParameters
			//Support for dynamic document management from UI
			String objectID = request.getParameter("objectId");
			if(objectID != null) params.setObjectID(objectID);
			
			//document management from SharePoint
			String url = request.getParameter("fileURL");
			String fileName = request.getParameter("fileName");
			if(fileName == null && request.getAttribute("fileName") != null) {
				fileName = String.valueOf(request.getAttribute("fileName"));//REST service
			}
			
			//document management from database
			//need to pass attachmentId
			String attachmentId = request.getParameter("attachmentId");
			if(attachmentId == null && request.getAttribute("attachmentId") != null) {
				attachmentId = String.valueOf(request.getAttribute("attachmentId"));//REST service
			}
			
			if((url != null && fileName != null) || 
					(attachmentId != null && fileName != null)) {
				
				//document management parameters for SharePoint
				params.setUrl(url);
				
				//document management parameters for database
				params.setAttachmentId(attachmentId);			
				
				InputStream in = null;
		        try {
		            in =  doc.retrieveDocument(params);
		            ByteArrayOutputStream bos = new ByteArrayOutputStream();
		            int next = in.read();
		            while (next > -1) {
		                bos.write(next);
		                next = in.read();
		            }
		            bos.flush();
		            byte[] result = bos.toByteArray();
		            //String contentType = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
		            response.setContentLength(result.length);
		            response.addHeader("Content-Disposition","attachment; filename=" + fileName);
		            response.getOutputStream().write(result);
					response.getOutputStream().flush();
		
		        } catch (Exception e) {
					log.error(e);
					throw new Exception(e);
				}
				
			}else {
				String tmp = "fileURL(" + url + ") || fileName(" + fileName 
						+ ") || attachmentId(" + attachmentId + ") not found in request";
				log.error(tmp);
				throw new Exception(tmp);
			}
		}catch (Exception e) {
			XMLException.rtnXMLException(response, e);
		}
	}
	
	@RequestMapping(value="/rejectDocument")	 
	public void rejectDocument(HttpServletRequest request, 
			HttpServletResponse response) {
		
		try {
			//Removed setObjectID(), setFileURL(), setGroupID()
			//not used in the new implementation		
			ExternalDocuments externalDocs = new ExternalDocuments();
			externalDocs.setStatus("REJECTED");
			externalDocs.setUser(CommonDAO.getUSERID(request));
			externalDocs.setDstamp(CommonDAO.getTimestamp(new SimpleJdbcTemplate(dataSource)));
			externalDocs.setRejectReason(request.getParameter("e2bRejectReason") != null ?  
					request.getParameter("e2bRejectReason") : "");
			externalDocs.setDocID(request.getParameter("docid") != null ? 
					Long.valueOf((String) request.getParameter("docid")) : 0);
			externalDocs.setFileID(request.getParameter("fileid") != null ?  
					request.getParameter("fileid") : "");
			
			DocMgtParameters params = new DocMgtParameters();
			params.setExternalDocs(externalDocs);
			params.setDs(dataSource);
			params.setFileID(externalDocs.getFileID());
			
			DocMgtService<?> doc = DocMgtLoader.getDocMgtService();
			boolean rtn= doc.reject(params);
			XMLException.rtnStatus(response, rtn == true ? "S" : "F", 
					rtn == true ? "Document reject successful" : "Document reject failed");
		}catch (Exception e) {
			XMLException.rtnXMLException(response, e);
		}
		
	}
}
