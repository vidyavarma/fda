package com.nrg.lau.dao;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauReportAttachments;
import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.IReportsConstants;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;

public class LauSiebelReportAttachmentsDAO {
	
	private Vector<LauReportAttachments> attachment	= null;
	private LauReportAttachments reportAttachments	= null;
	private static Logger log	= Logger.getLogger(LauSiebelReportAttachmentsDAO.class);	

	protected void finalize() throws Throwable {		
		this.attachment.removeAllElements();
		this.attachment = null;
		super.finalize();
	}
	
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		String userId = CommonDAO.getUSERID(request);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		if(request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_SIEBEL_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_SIEBEL_PARAM_NAME).length() > 0) {
			
			log.info("LauReportAttachmentsDAO save() LAU_REPORT_ATTACHMENTS_SIEBEL_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_SIEBEL_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REPORT_ATTACHMENTS_SIEBEL_PARAM_NAME));
			
			Iterator<LauReportAttachments> itr = this.attachment.iterator();			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauReportAttachments lauReportAttachments = (LauReportAttachments)itr.next();
				log.info("itr.hasNext() -> " + lauReportAttachments.toString());
				this.reportAttachments	= null;
				this.reportAttachments	= lauReportAttachments;
				insert(datasource,request,template,userId,dt);	
			}//end of while(itr.hasNext())
			
		} else {
			log.info(IReportsConstants.LAU_REPORT_ATTACHMENTS_SIEBEL_PARAM_NAME + " not found in request");
		}		
	}

	public void insert(DataSource datasource, HttpServletRequest request, 
			SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) throws Exception {
		// Normal HTTP Attachments


		log.info("insertAttachment - > Siebel Attachments");
		insertSiebelAttachment(datasource, request, SQL_INSERT_STRING, template,userId,dt);	
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
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_TYPE", "documentType" );	
		digester.addBeanPropertySetter( mainXmlTag+"/DOCUMENT_NAME", "documentName" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_ID", "contactId" );	
				
		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	/**
	 * <ROWSET>
  <ROW>
    <ATTACHMENT_ID/>
    <ATTACHMENT_NAME>SOB Package1-7CX1522</ATTACHMENT_NAME>
    <CONTACT_ID>xxxx</CONTACT_ID>
    <DOCUMENT_NAME>SOB Package1-7CX1522-09/19/2008 12:15:10</DOCUMENT_NAME>
    <DOCUMENT_TYPE/>
    <REPORT_ID>1188</REPORT_ID>
  </ROW>
</ROWSET>		
	 * @param lauReportAttachments
	 */
	public void addXmlData(LauReportAttachments lauReportAttachments) {
		attachment.add(lauReportAttachments);
	}
	
	//SQL Statements	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REPORT_ATTACHMENTS (ATTACHMENT," +
			"UPDATE_USER_ID,UPDATE_TIMESTAMP,DOCUMENT_NAME,DOCUMENT_TYPE,BINARY_FILE_TYPE,REPORT_ID,ATTACHMENT_ID,FILE_NAME) " +
			"VALUES(EMPTY_BLOB(),?," +
			"?,?,?,?,?,?,?)";

	private void insertSiebelAttachment(DataSource datasource, HttpServletRequest request, 
			String sql, SimpleJdbcTemplate template, String userId, java.sql.Timestamp dt)	throws Exception {
		
		Connection con					= null;
		PreparedStatement pst 			= null;
		ResultSet rs 					= null;
		Statement stmt 					= null;
		OutputStream blobOutputStream   = null;
		SiebelDataBean dataBean = null;

		try	{

			System.setProperty("file.encoding", Constants.SIEBEL_FILE_ENCODING);
			int intInsStatus = CommonDAO.insertActivityDetails(template, null, this.reportAttachments.getReportId(), "EDIT", "", "", request,userId,dt);
			long attachmentId = CommonDAO.getPrimaryKey(template);
			this.reportAttachments.setAttachmentId(attachmentId);
			dataBean = new SiebelDataBean();
			LauReferenceListDetailsGetDAO lauReferenceListDetailsGetDAO = new LauReferenceListDetailsGetDAO();
			Map<String, String> listMap = lauReferenceListDetailsGetDAO.getCodeValuesAsMap(template, Constants.SIEBEL_FEFLIST_NAME);			 

			log.info(listMap.get(Constants.SIEBEL_FEFLIST_URL));  

			String siebelUrl = listMap.get(Constants.SIEBEL_FEFLIST_URL);
			String siebelUser = listMap.get(Constants.SIEBEL_FEFLIST_USER);
			String siebelPwd = listMap.get(Constants.SIEBEL_FEFLIST_PASSWORD);
			String siebelFolderLoc = listMap.get(Constants.SIEBEL_FEFLIST_FOLDERLOC);
			log.info("axis connection info"+"siebelUrl:"+siebelUrl+",siebelUser:"+siebelUser+ ",siebelFolderLoc:"+siebelFolderLoc);
			//SIEBEL_FEFLIST_FOLDERLOC

			//dataBean.login(Constants.SIEBEL_CONNECT_URL, Constants.SIEBEL_USER, Constants.SIEBEL_USER);
			dataBean.login(siebelUrl, siebelUser, siebelPwd );
			log.info("connected to databean");	
			
			//dataBean.login(Constants.SIEBEL_CONNECT_URL, Constants.SIEBEL_USER, Constants.SIEBEL_USER);
			SiebelBusObject busObject	= dataBean.getBusObject(Constants.SIEBEL_CONTACT_OBJ);
			SiebelBusComp busComp 		= busObject.getBusComp(Constants.SIEBEL_CONTACT_COMP); 
			busComp.setViewMode(3);			
			busComp.clearToQuery();
			busComp.activateField("Contact Id");
			busComp.setSearchSpec("Contact Id", this.reportAttachments.getContactId());
			busComp.setSearchSpec("ContactFileName", this.reportAttachments.getAttachment());
			busComp.setSortSpec("Created(DESCENDING)");
			busComp.executeQuery(true);
			
			if (busComp.firstRecord()) {
				
				String fileName = busComp.invokeMethod("GetFile", new String[]{"ContactFileName"});
				con = datasource.getConnection();		
				con.setAutoCommit(false);				
				String orgFileName	= fileName.substring(fileName.lastIndexOf("_") + 1,fileName.length());	
				String orginalFilename = orgFileName;
				try {					
					String documentName = this.reportAttachments.getDocumentName().trim();
					//String orgFileName	= file.getOriginalFilename().trim();
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
					log.error(e);
				}
				
				pst = con.prepareStatement(sql);
				
				pst.setString(1, userId);
				pst.setTimestamp(2, dt);
	
				pst.setString(3, this.reportAttachments.getDocumentName() );
				pst.setString(4, this.reportAttachments.getDocumentType());
				pst.setString(5, busComp.getFieldValue("Attachment Type BIIB"));	
				pst.setLong(6, this.reportAttachments.getReportId());
				pst.setLong(7, this.reportAttachments.getAttachmentId());
				pst.setString(8, orginalFilename);
				pst.execute();
				
				sql = "SELECT ATTACHMENT FROM LAU_REPORT_ATTACHMENTS WHERE ATTACHMENT_ID = " + 
				this.reportAttachments.getAttachmentId() + " FOR UPDATE";
				log.info(sql);	
				BLOB blb 						= null;
				stmt 							= con.createStatement();
				rs 								= stmt.executeQuery(sql);
				weblogic.jdbc.wrapper.ResultSet wlsResultSet = (weblogic.jdbc.wrapper.ResultSet)rs;   
				OracleResultSet oracleResultSet = (OracleResultSet)wlsResultSet.getVendorObj();
				
				rs.next();
				blb = oracleResultSet.getBLOB("ATTACHMENT");			
				blobOutputStream = blb.getBinaryOutputStream();	
				URL url;
				URLConnection urlConn;
				DataInputStream dis;			
/*				url = new URL(Constants.SIEBEL_FILE_PATH 
						+ fileName.substring(fileName.lastIndexOf("\\") + 1,
								fileName.length()));*/
				
				url = new URL(siebelFolderLoc
						+ fileName.substring(fileName.lastIndexOf("\\") + 1,
								fileName.length()));
				
				urlConn = url.openConnection();
				urlConn.setDoInput(true);
				urlConn.setUseCaches(false);
				dis = new DataInputStream(urlConn.getInputStream());
				byte[] binaryBuffer = new byte[1024];
				int i = dis.read(binaryBuffer);
				while (i != -1) {
					blobOutputStream.write(binaryBuffer, 0, i);
					i = dis.read(binaryBuffer); 
				} 
				//inputFileInputStream.close();
				dis.close();
				blobOutputStream.close(); 
				log.info("insertAttachment() -> Siebel attachment save completed");
				con.commit();
				
			}	
		} catch(Exception e)	{
			log.info("in error");
			log.error(e);
			con.rollback();		
			try {
				dataBean.logoff();
			} catch (SiebelException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				log.error("Exception in LauSiebelReportAttachmentsDAO, full stack trace follows:", e1);
			}
			
		} finally {			

			con.setAutoCommit(true);		
			try
			{
				dataBean.logoff();
				if (blobOutputStream != null) blobOutputStream.close();
				if (pst != null) 	pst.close();
				if (rs != null) 	rs.close();
				if (stmt != null) 	stmt.close();
				if (con != null)	con.close();
				log.info("siebel attachment connection closed");
			}catch (Exception e)	{
				con.rollback();				
				throw new Exception(e);
			}	 
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
