package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauAttachmentProperties;
import com.nrg.lau.commons.IReportChildUpdateDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.sharepoint.DetachService;
import com.nrg.lau.sharepoint.FileRenameService;
import com.nrg.lau.sharepoint.SPResponse;

public class LauAttachmentPropertiesSetDAO implements IReportChildUpdateDAO<LauAttachmentProperties>{

	private static Logger log	= Logger.getLogger(LauAttachmentPropertiesSetDAO.class);
	private Vector<LauAttachmentProperties> reports	= null;
	private LauAttachmentProperties attachmentProperties 		= null;	
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PROPERTIES) != null && 
				request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PROPERTIES).length() > 0) {
			
			log.info("LauAttachmentPropertiesSetDAO save() LAU_REPORT_ATTACHMENTS_PROPERTIES -> " 
					+ request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PROPERTIES));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_PROPERTIES));
			Iterator<LauAttachmentProperties> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauAttachmentProperties lauAttachmentProperties = (LauAttachmentProperties)itr.next();
				log.info("itr.hasNext() -> " + lauAttachmentProperties.toString());
				this.attachmentProperties	= null;
				this.attachmentProperties	= lauAttachmentProperties;
				if(lauAttachmentProperties.getAttachmentId() != 0)	 {
					if(lauAttachmentProperties.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_REPORT_ATTACHMENTS_PROPERTIES + " not found in request");
		}
		
	}
	
	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		String documentName = this.attachmentProperties.getDocumentName().trim();
		String orgFileName	= this.attachmentProperties.getAttachmentName().trim();
		
		try {
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
					this.attachmentProperties.setDocumentName(temp);					
				}
			}else if(documentName.lastIndexOf(".") == -1 && orgFileName.lastIndexOf(".") != -1){
				orgFileName	
					= orgFileName.substring(orgFileName.lastIndexOf("."), orgFileName.length());
				temp = temp + orgFileName;
				this.attachmentProperties.setDocumentName(temp);
			}
			
		}catch(Exception e) {
			log.error(e);
		}		
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauAttachmentPropertiesSetDAO update() ID -> " + id);
		//If update has happened i.e (1) call rename share point service AND fileid <> null - UI set file id only if the title changed
		if(id != 0 && this.attachmentProperties.getFileID().length()>0) {
			FileRenameService.rename(this.attachmentProperties.getFileID(), 
					this.attachmentProperties.getDocumentName());
		}	
		
	}
	
	private Object[] getParameters()
	{
		LauAttachmentProperties lauAttachmentProperties = this.attachmentProperties; 
		return new Object[]{
			lauAttachmentProperties.getDocumentName(),
			lauAttachmentProperties.getDocumentType(),
			lauAttachmentProperties.getPROMOTE_DOCUMENT(),
			userId,
			dt,
			Long.valueOf(lauAttachmentProperties.getDISPLAY_NUMBER()),
			lauAttachmentProperties.getAttachmentId()
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		reports				= new Vector<LauAttachmentProperties>();
		Digester digester	= new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate( mainXmlTag, LauAttachmentProperties.class );
		
		log.info(xml);
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENT_ID", "attachmentId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_NAME", "documentName" );
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_TYPE", "documentType" );		
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/FILE_NAME", "attachmentName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROMOTE_DOCUMENT", "PROMOTE_DOCUMENT" );
		digester.addBeanPropertySetter( mainXmlTag+"/FILE_ID", "fileID" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "DISPLAY_NUMBER" );
		
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		//DOCUMENT_TYPE
	}
	
	public void addXmlData(LauAttachmentProperties lauAttachmentProperties) {
		reports.add(lauAttachmentProperties);
		log.info(lauAttachmentProperties.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = " UPDATE LAU_REPORT_ATTACHMENTS " +
			"SET DOCUMENT_NAME=?,DOCUMENT_TYPE=?,PROMOTE_DOCUMENT=?," +
			"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?, DISPLAY_NUMBER=? WHERE ATTACHMENT_ID =? ";
	
 
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		
		// 02/21/2012 sa/rahul Added detach web service
		String detachSQL = "SELECT A.report_id,REPORT_CREATE_USER_GROUP,PROMOTED_AE_CASE_ID," +
				"B.OBJECT_ID,B.PROMOTE_DOCUMENT FROM LAU_REPORTS a,LAU_REPORT_ATTACHMENTS B " +
				"WHERE B.REPORT_ID = A.REPORT_ID AND B.ATTACHMENT_ID = ?";
		
		List<Map<String, Object>> rows 	= template.queryForList(detachSQL, new Object[]{
				this.attachmentProperties.getAttachmentId()	});
		
		for(Map<String, Object> row : rows) {
			
			log.info("report_id -> " + row.get("report_id")
					+ " REPORT_CREATE_USER_GROUP -> " + row.get("REPORT_CREATE_USER_GROUP")
					+ " PROMOTED_AE_CASE_ID -> " + row.get("PROMOTED_AE_CASE_ID") 
					+ " OBJECT_ID -> " + row.get("OBJECT_ID") 
					+ " PROMOTE_DOCUMENT -> " + row.get("PROMOTE_DOCUMENT"));
			
			String caseID ="";
			String coreDoc="";
			
			caseID =  String.valueOf(row.get("PROMOTED_AE_CASE_ID"));
			coreDoc =  String.valueOf(row.get("PROMOTE_DOCUMENT"));
			log.info(" BEFORE caseID -> " + caseID);
			if(!coreDoc.trim().equalsIgnoreCase("Y")) {
				caseID = "";	}
			log.info("after caseID -> " + caseID);
			boolean rtn = DetachService.detach(String.valueOf(row.get("OBJECT_ID")), 
					String.valueOf(row.get("REPORT_CREATE_USER_GROUP")), caseID);
			log.info("return value for DetachService.detach() -> " + rtn);
			
		
		
			id = template.update("DELETE FROM LAU_REPORT_ATTACHMENTS WHERE ATTACHMENT_ID = ?",
					new Object[]{this.attachmentProperties.getAttachmentId()});
			log.info("LauAttachmentPropertiesSetDAO  delete() ID -> " + id);
		}

/**	
		
		
		id = template.update("DELETE FROM LAU_REPORT_ATTACHMENTS WHERE ATTACHMENT_ID = ?",
				new Object[]{this.attachmentProperties.getAttachmentId()});
		log.info("LauAttachmentPropertiesSetDAO delete() ID -> " + id);		
		**/
	}
	
	/**
	 	LAU_REPORT_ATTACHMENTS
	 	
	 	DOCUMENT_NAME,ATTACHMENT_ID
	 
	 */

}
