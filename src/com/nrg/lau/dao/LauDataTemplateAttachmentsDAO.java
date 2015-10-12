package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import oracle.jdbc.OraclePreparedStatement;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nrg.lau.beans.LauDataTemplate;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.service.bi.BIPublisher;
import com.nrg.lau.service.bi.BIPublisherLoader;
import com.nrg.lau.utils.ReadConfig;
;
public class LauDataTemplateAttachmentsDAO {
	
	private Vector<LauDataTemplate> templates	= null;
	private LauDataTemplate dataTemplate	= null;
	private static Logger log	= Logger.getLogger(LauDataTemplateDAO.class);
	private long transaction;
	private long dataTemplateId;
	
	static public long DATATEMPLATE_ID;
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_DATA_TEMPLATES (DATA_TEMPLATE,DATA_TEMPLATE_ID," +
			"UPDATE_USER_ID,UPDATE_TIMESTAMP,TEMPLATE_NAME,TEMPLATE_DESCRIPTION,STANDARD_REPORT) " +
			"values(?,?,?,?,?,?,?)";     
	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_DATA_TEMPLATES SET DATA_TEMPLATE=?," +
			"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?,TEMPLATE_NAME=?,TEMPLATE_DESCRIPTION=?,STANDARD_REPORT=?" +
			"WHERE DATA_TEMPLATE_ID=?";
	
			
	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {
		
		String	userId = CommonDAO.getUSERID(request);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		if(request.getParameter(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME).length() > 0) {

			log.info("LauDataTemplateDAO save() LAU_DATA_TEMPLATE_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME));

			Iterator<LauDataTemplate> itr = this.templates.iterator();
			while(itr.hasNext())	{
				
				LauDataTemplate lauDataTemplate = (LauDataTemplate)itr.next();
				
				this.dataTemplate	= null;
				this.dataTemplate	= lauDataTemplate;
				
				transaction = lauDataTemplate.getTransactionType();
				
				if(lauDataTemplate.getTransactionType() == IReportsConstants.deleteFlag)	{
					delete(template,userId);
				}else if(lauDataTemplate.getTransactionType() == 1)
					insert(datasource,request,transaction,template,userId,dt);
				else 
					insertOrUpdatetTemplate(datasource, request,SQL_UPDATE_STRING,transaction,template,userId,dt);
			}//end of while(itr.hasNext())
		}	else {
			log.info(IReportsConstants.LAU_DATA_TEMPLATE_PARAM_NAME + " not found in request");
		}

	}
	
	
	public void insert(DataSource datasource, HttpServletRequest request,long transactionType,
			SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) throws Exception {
		// Normal HTTP Attachments
		insertOrUpdatetTemplate(datasource, request,SQL_INSERT_STRING,transactionType,template,userId,dt);
	}
							
	
	@SuppressWarnings("deprecation")
	private void insertOrUpdatetTemplate(DataSource datasource, HttpServletRequest request,
			String sql,long transactionType,SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
	 
	
		Connection con					= null;
		PreparedStatement pst 			= null;
		ResultSet rs 					= null;
		Statement stmt 					= null;
		
		try	{

			MultipartHttpServletRequest httpRequest = (MultipartHttpServletRequest)request;
			MultipartFile file = httpRequest.getFile(this.dataTemplate.getAttachment());
			String fileAsString = CommonDAO.getFileContents(file);
			log.info("input stream: "+file.getSize());

			log.info("insertTemplate - > Normal HTTP Attachments");
			con = datasource.getConnection();
			con.setAutoCommit(false);
			log.info("datasource.getConnection()");
	
			
			log.info(file.getOriginalFilename()  + "    file.getOriginalFilename()");
			log.info("filetoString: -"+fileAsString);
			log.info("DT----->"+dt);
			log.info("userid----->"+userId);
			log.info("this.dataTemplate.getTemplateName()--->"+ this.dataTemplate.getTemplateName());
			log.info("this.dataTemplate.getTemplateDescription()--->"+this.dataTemplate.getTemplateDescription());
			log.info("this.dataTemplate.getStdReport()--->"+this.dataTemplate.getStdReport());
			log.info("this.dataTemplate.getDataTemplate()--->"+ this.dataTemplate.getDataTemplate());

			pst = (PreparedStatement)con.prepareStatement(sql);
			
			if(transactionType!=2) //if(datatemplateId does not exist, Insert Record)
			{
			dataTemplateId = CommonDAO.getPrimaryKey(template);
			this.dataTemplate.setDataTemplateId(dataTemplateId);
			log.info("this.dataTemplate.getDataTemplateId()"+this.dataTemplate.getDataTemplateId());
			
			pst.setString(1, fileAsString);
			pst.setLong(2, this.dataTemplate.getDataTemplateId());
			pst.setString(3, userId);
			pst.setTimestamp(4, dt);
			pst.setString(5, this.dataTemplate.getTemplateName());
			pst.setString(6, this.dataTemplate.getTemplateDescription());
			pst.setString(7, this.dataTemplate.getStdReport());
			
			}
			else
			{
				
							
				pst.setString(1, fileAsString);
				pst.setString(2, userId);
				pst.setTimestamp(3, dt);
				pst.setString(4, this.dataTemplate.getTemplateName());
				pst.setString(5, this.dataTemplate.getTemplateDescription());
				pst.setString(6, this.dataTemplate.getStdReport());
				pst.setLong(7,this.dataTemplate.getDataTemplateId());
			}

			boolean query = pst.execute();
			log.info(query);
			
			//BI Publisher 11g web service integration
			boolean biServer = Boolean.valueOf(ReadConfig.getValue("BI_SERVER"));
			if(biServer) {
				
				BIPublisher bi = BIPublisherLoader.getBIPublisher();
				if(transactionType != 2){
					
					log.info("*** Start creating BI Publisher datamodel ***");									
					boolean rtn = bi.createDataModel(String.valueOf(this.dataTemplate.getDataTemplateId()), 
							String.valueOf(this.dataTemplate.getDataTemplateId()), file.getBytes());					
					log.info("*** End creating BI Publisher datamodel *** return -> "+ rtn);
					
				} else {				
					
					log.info("*** Start updating BI Publisher datamodel ***");									
					boolean rtn = bi.updateDataModel(String.valueOf(this.dataTemplate.getDataTemplateId()), file.getBytes());					
					log.info("*** End updating BI Publisher datamodel *** return -> "+ rtn);
							
				}
			}//******************************************

		
		}catch(Exception e)	{
			log.error(e);
			con.rollback();
			throw new Exception(e);

		} finally {
			con.setAutoCommit(true);
			try
			{
				if (pst != null) 	pst.close();
				if (rs != null) 	rs.close();
				if (stmt != null) 	stmt.close();
				if (con != null)	con.close();
				log.info("connection closed");
			}catch (Exception e)	{
				con.rollback();
				throw new Exception(e);
			}
		}
		
	}
	
	
	
	private void createBeansFromXml(String xml) throws Exception {

		templates			= new Vector<LauDataTemplate>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauDataTemplate.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENT_NAME", "attachment" );
		digester.addBeanPropertySetter(mainXmlTag+"/DATA_TEMPLATE", "dataTemplate" );
		digester.addBeanPropertySetter( mainXmlTag+"/DATA_TEMPLATE_ID", "dataTemplateId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEMPLATE_NAME", "templateName" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEMPLATE_DESCRIPTION", "templateDescription" );
		digester.addBeanPropertySetter( mainXmlTag+"/STANDARD_REPORT", "stdReport" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );

		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauDataTemplate lauDataTemplates) {
		templates.add(lauDataTemplates);
		log.info(lauDataTemplates.toString());
	}
	
	
	public void delete(SimpleJdbcTemplate template, String userId) throws Exception {
		
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_DATA_TEMPLATES WHERE DATA_TEMPLATE_ID = ?",
				new Object[]{this.dataTemplate.getDataTemplateId()});
		
		log.info("LauReportAttachmentsDAO delete() ID -> " + id);
		
		//BI Publisher 11g web service integration
		//If using BI WebService we have to remove the data model
		boolean biServer = Boolean.valueOf(ReadConfig.getValue("BI_SERVER"));		
		if(biServer) {
			
			log.info("*** Start deleting BI Publisher datamodel ***");		
			BIPublisher bi = BIPublisherLoader.getBIPublisher();
			boolean rtn = bi.deleteDataModel(String.valueOf(this.dataTemplate.getDataTemplateId()));
			log.info("*** End deleting BI Publisher datamodel *** return -> " + rtn);
			
		}
		//****************************************
		
			
		
	}
}	
	

/*
<?xml version="1.0"?>
<ROWSET>
 <ROW>
  <DATA_TEMPLATE_ID>1</DATA_TEMPLATE_ID>
  <TEMPLATE_NAME>Product_Template</TEMPLATE_NAME>
  <DATA_TEMPLATE> </DATA_TEMPLATE>
  <STANDARD_REPORT>1</STANDARD_REPORT>
  <UPDATE_USER_ID>Rahul</UPDATE_USER_ID>
 </ROW>
</ROWSET>
*/