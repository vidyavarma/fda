package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nrg.lau.beans.ExternalDocuments;
import com.nrg.lau.beans.LauReportAttachments;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.service.docmgt.DocMgtLoader;
import com.nrg.lau.service.docmgt.DocMgtParameters;
import com.nrg.lau.service.docmgt.DocMgtService;

public class LauReportAttachmentsDAO {

	private Vector<LauReportAttachments> attachment	= null;
	private LauReportAttachments reportAttachments	= null;
	private static Logger log	= Logger.getLogger(LauReportAttachmentsDAO.class);

	protected void finalize() throws Throwable {
		this.attachment.removeAllElements();
		this.attachment = null;
		super.finalize();
	}

	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		
		String	userId = CommonDAO.getUSERID(request);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		if(request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME).length() > 0) {

			log.info("LauReportAttachmentsDAO save() LAU_REPORT_ATTACHMENTS_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME));

			Iterator<LauReportAttachments> itr = this.attachment.iterator();
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauReportAttachments lauReportAttachments = (LauReportAttachments)itr.next();
				log.info("itr.hasNext() -> " + lauReportAttachments.toString());
				this.reportAttachments	= null;
				this.reportAttachments	= lauReportAttachments;
				if(lauReportAttachments.getTransactionType() == IReportsConstants.deleteFlag)	{
					delete(template,userId);
				}else	insert(datasource,request,template,userId,dt);
			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME + " not found in request");
		}

	}

	public void insert(DataSource datasource, HttpServletRequest request,
			SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) throws Exception {
		
		// Normal HTTP Attachments
		insertAttachment(datasource, request,template,userId,dt);
	}

	public void delete(SimpleJdbcTemplate template, String userId) throws Exception {
		int id = 0;
		
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_REPORT_ATTACHMENTS WHERE ATTACHMENT_ID = ?",
				new Object[]{this.reportAttachments.getAttachmentId()});
		log.info("LauReportAttachmentsDAO delete() ID -> " + id);

	}

	private void createBeansFromXml(String xml) throws Exception {

		String mainXmlTag 	= "ROWSET/ROW";
		attachment			= new Vector<LauReportAttachments>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauReportAttachments.class );

		//Explicitly specify property
		//FILE_NAME
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENT_NAME", "attachment" );
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENT_NAME", "fileName" );
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENT_ID", "attachmentId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_NAME", "documentName" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_TYPE", "documentType" );
		digester.addBeanPropertySetter( mainXmlTag+"/LAU_REPORT_ID", "LAU_REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_ID", "activityId" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "DISPLAY_NUMBER" );
		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauReportAttachments lauReportAttachments) {
		attachment.add(lauReportAttachments);
	}

	private void insertAttachment(DataSource datasource, HttpServletRequest request,
			SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {		
		
		try	{

			MultipartHttpServletRequest httpRequest = (MultipartHttpServletRequest)request;
			MultipartFile file = httpRequest.getFile(this.reportAttachments.getAttachment());

			int rtnStatus = CommonDAO.insertActivityDetails(template, null, this.reportAttachments.getReportId(), "EDIT", "", "", request,userId,dt);
			log.info("insertActivityDetails -> " + rtnStatus);
			
			long attachmentId = CommonDAO.getPrimaryKey(template);
			this.reportAttachments.setAttachmentId(attachmentId);			

			log.info(file.getOriginalFilename()  + "    file.getOriginalFilename()");
			try {
				String documentName = this.reportAttachments.getDocumentName().trim();
				String orgFileName	= file.getOriginalFilename().trim();
				String temp	= documentName;
				if(documentName.lastIndexOf(".") != -1 && orgFileName.lastIndexOf(".") != -1) {
					documentName
						= documentName.substring(documentName.lastIndexOf("."), documentName.length());
					orgFileName
						= orgFileName.substring(orgFileName.lastIndexOf("."), orgFileName.length());
					if(documentName.equalsIgnoreCase(orgFileName) == false ||
							documentName.length() != orgFileName.length()) {
						temp = temp.substring(0,temp.lastIndexOf(".") );
						temp = temp + orgFileName;
						this.reportAttachments.setDocumentName(temp);
					}
				}else if(documentName.lastIndexOf(".") == -1 && orgFileName.lastIndexOf(".") != -1){
					orgFileName
						= orgFileName.substring(orgFileName.lastIndexOf("."), orgFileName.length());
					temp = temp + orgFileName;
					this.reportAttachments.setDocumentName(temp);
				}

			}catch(Exception e) {
				log.info(e.toString());
			}
			
			//Document Management Implementation
			//DUAL support
			
			DocMgtService<?> doc = null;
			doc = DocMgtLoader.getDocMgtService();
			DocMgtParameters params = new DocMgtParameters();
			
			ExternalDocuments documents = new ExternalDocuments();
			documents.setUser(userId);
			documents.setDstamp(dt);
			documents.setDocumentName(this.reportAttachments.getDocumentName());
			documents.setDocumentType(this.reportAttachments.getDocumentType());
			documents.setDocumentContentType(file.getContentType());
			documents.setDocumentOrigFilename(file.getOriginalFilename());
			documents.setDocumentBytes(file.getBytes());
			documents.setDISPLAY_NUMBER(this.reportAttachments.getDISPLAY_NUMBER());
			
			params.setDs(datasource);
			params.setExternalDocs(documents);
			params.setLauReportId(String.valueOf(this.reportAttachments.getReportId()));
			params.setAttachmentId(String.valueOf(this.reportAttachments.getAttachmentId()));
			
			doc.upload(httpRequest, params);

		} catch(Exception e)	{
			log.info(e.toString());			
			throw new Exception(e);

		} 
	}

	/*
	  	LAU_REPORT_ATTACHMENTS

		ATTACHMENT_ID NUMBER  NOT NULL ,
		REPORT_ID NUMBER  NOT NULL ,
		DOCUMENT_NAME VARCHAR2 (300 BYTE) ,
		DOCUMENT_TYPE VARCHAR2 (50 BYTE) ,
		BINARY_FILE_TYPE VARCHAR2 (50 BYTE) ,
		ATTACHMENT CLOB ,
		UPDATE_USER_ID VARCHAR2 (300 BYTE)  NOT NULL ,
		UPDATE_TIMESTAMP TIMESTAMP (0) WITH LOCAL TIME ZONE  NOT NULL

	 */

}