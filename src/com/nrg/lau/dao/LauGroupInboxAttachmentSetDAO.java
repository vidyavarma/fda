package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauReportActivities;
import com.nrg.lau.beans.LauReportAttachments;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.sharepoint.InBoxToReportsService;
import com.nrg.lau.sharepoint.SPResponse;

public class LauGroupInboxAttachmentSetDAO implements IReportChildSetDAO<LauReportActivities>{  

	private static Logger log	= Logger.getLogger(LauGroupInboxAttachmentSetDAO.class);
	private Vector<LauReportAttachments> attachment	= null;
	private LauReportAttachments reportAttachments	= null;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.attachment.removeAllElements();
		this.attachment = null;
		super.finalize();		
	}
	
	
	public String save(HttpServletRequest request, SimpleJdbcTemplate template,
			DataSource datasource, String user, java.sql.Timestamp  dstamp) throws Exception {
		//Check to make sure that XML is there in Request.
		
		dt = dstamp;
		userId = user;
		
		if(request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME).length() > 0) {
			log.info("INSIDE ATTACHMENT INSERT");
			createBeansFromXmlForAttachment(request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME));
			Iterator<LauReportAttachments> itrAtt = this.attachment.iterator();
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itrAtt.hasNext())	{
				LauReportAttachments lauReportAttachments = (LauReportAttachments)itrAtt.next();
				if(lauReportAttachments.getReportId() > 0 && 
						lauReportAttachments.getLAU_REPORT_ID().length() > 0) {
					
					log.info("LauGroupInboxAttachmentSetDAO save() LAU_REPORT_ATTACHMENTS_PARAM_NAME -> "
							+ request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME));
					this.reportAttachments	= null;
					this.reportAttachments	= lauReportAttachments;
					insertAttachment(template);
					updateExternalDocStatus(template,lauReportAttachments.getEXTERNAL_DOCUMENT_ID());
				}
		}	
			
		}else {
			log.info(IReportsConstants.LAU_REPORT_ATTACHMENTS_PARAM_NAME + " not found in request for attachment insert");
		}
		
		return "Group Inbox attachment transaction successful";
		
	}	
	
	public void updateExternalDocStatus(SimpleJdbcTemplate template, String documentID) throws Exception {
		int id = 0;
		log.info("BEFORE LauGroupInboxAttachmentSetDAO update() -> " + documentID);
		id = template.update("UPDATE LAU_EXTERNAL_DOCUMENTS SET " +
				"DOCUMENT_STATUS = 'ACCEPTED' WHERE EXTERNAL_DOCUMENT_ID = ?" ,new Object[]{documentID});
		log.info("LauGroupInboxAttachmentSetDAO update() ID -> " + id);	
	}
	
	private void createBeansFromXmlForAttachment(String xml) throws Exception {
		attachment            = new Vector<LauReportAttachments>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauReportAttachments.class );

		//Explicitly specify property
		//FILE_NAME
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENT_NAME", "attachment" );
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENT_NAME", "fileName" );
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENT_ID", "attachmentId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId2" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_NAME", "documentName" );
		digester.addBeanPropertySetter( mainXmlTag+"/FILE_NAME", "fileName" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_TYPE", "documentType" );
		digester.addBeanPropertySetter( mainXmlTag+"/BINARY_FILE_TYPE", "BINARY_FILE_TYPE" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROMOTE_DOCUMENT", "PROMOTE_DOCUMENT" );
		digester.addBeanPropertySetter( mainXmlTag+"/EXTERNAL_DOCUMENT_URL", "EXTERNAL_DOCUMENT_URL" );
		digester.addBeanPropertySetter( mainXmlTag+"/OBJECT_ID", "OBJECT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/EXTERNAL_DOCUMENT_ID", "EXTERNAL_DOCUMENT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/LAU_REPORT_ID", "LAU_REPORT_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_ID", "activityId" );

		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlDataForAttachment" );
		digester.parse(new StringReader(xml));

	}
	
	public void addXmlDataForAttachment(LauReportAttachments lauReportAttachments) {
		log.info("before add....");
		attachment.add(lauReportAttachments);
		log.info(lauReportAttachments.toString());
	}
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void insertAttachment(SimpleJdbcTemplate template)
			throws Exception {
		int id = 0;
		long attachmentId = CommonDAO.getPrimaryKey(template);
		this.reportAttachments.setAttachmentId(attachmentId);
			
		SPResponse sPRes = InBoxToReportsService.moveSPDocFromInBoxToReports(
				this.reportAttachments.getOBJECT_ID(), this.reportAttachments.getLAU_REPORT_ID());
		if(Boolean.valueOf(sPRes.getSuccess()) == true) {		
			log.info("Generated Primary Key for LAU_REPORT_ATTACHMENT  ->" + attachmentId);
			id = template.update(SQL_ATTACH_INSERT_STRING,getParametersAttachments());
			log.info("LauReportActivityAttachmentSetDAO insert() ID -> " + id);
		} else {
			log.error("Sharepoint operation to attach the document with the Report has failed.");
			throw new Exception(sPRes.getMessage());
		}
	}
	
	//SQL Statements
	private final String SQL_ATTACH_INSERT_STRING = "INSERT INTO LAU_REPORT_ATTACHMENTS (DOCUMENT_NAME,FILE_NAME," +
			"EXTERNAL_DOCUMENT_URL,OBJECT_ID,UPDATE_USER_ID,UPDATE_TIMESTAMP,ATTACHMENT_ID,ACTIVITY_ID,REPORT_ID) VALUES" +
			"(?,?,?,?,?,?,?,?,?)";
	
	
	private Object[] getParametersAttachments()
	{
		LauReportAttachments lauReportAttachments = this.reportAttachments; 
		return new Object[]{			
			lauReportAttachments.getDocumentName(),
			lauReportAttachments.getFileName(),
			InBoxToReportsService.rewriteSPDocumentURL(lauReportAttachments.getLAU_REPORT_ID(), 
					lauReportAttachments.getFileName(), 
					lauReportAttachments.getEXTERNAL_DOCUMENT_URL()),
			lauReportAttachments.getOBJECT_ID(),
			userId,dt,
			lauReportAttachments.getAttachmentId(),
			lauReportAttachments.getActivityId(),
			lauReportAttachments.getReportId()			
		};
	}


	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp) throws Exception {
		// TODO Auto-generated method stub
		
	}


	public void update(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub
		
	}


	public void createBeansFromXml(String xml) throws Exception {
		// TODO Auto-generated method stub
		
	}


	public void delete(SimpleJdbcTemplate template) throws Exception {
		// TODO Auto-generated method stub
		
	}


	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
			
		LAU_REPORT_ATTACHMENTS
 
		ATTACHMENT_ID         NOT NULL NUMBER                           
		REPORT_ID             NOT NULL NUMBER                           
		ACTIVITY_ID                    NUMBER                           
		DOCUMENT_NAME                  VARCHAR2(300)                    
		DOCUMENT_TYPE                  VARCHAR2(50)                     
		BINARY_FILE_TYPE               VARCHAR2(50)                     
		FILE_NAME                      VARCHAR2(500)                    
		PROMOTE_DOCUMENT               VARCHAR2(5)                      
		EXTERNAL_DOCUMENT_URL          VARCHAR2(4000)                   
		ATTACHMENT                     BLOB()                           
		UPDATE_USER_ID        NOT NULL VARCHAR2(300)                    
		UPDATE_TIMESTAMP      NOT NULL TIMESTAMP(2) WITH LOCAL TIME ZONE 
	 
	 */

}
