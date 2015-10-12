package com.nrg.lau.dao;


import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.nrg.lau.beans.LauLayoutQuestions;
import com.nrg.lau.beans.LauLayoutTemplate;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.service.bi.BIPublisher;
import com.nrg.lau.service.bi.BIPublisherLoader;
import com.nrg.lau.utils.ReadConfig;

public class LauLayoutTemplateAttachmentDAO {

	private Vector<LauLayoutTemplate> layout	= null;
	private LauLayoutTemplate layoutTemplate	= null;
	private static Logger log	= Logger.getLogger(LauLayoutTemplateAttachmentDAO.class);
	private long transaction;

	private final String SQL_INSERT_STRING = "INSERT INTO LAU_LAYOUT_TEMPLATES (LAYOUT_TEMPLATE,DATA_TEMPLATE_ID,LAYOUT_TEMPLATE_ID," +
			"UPDATE_USER_ID,UPDATE_TIMESTAMP,TEMPLATE_NAME,TEMPLATE_DESCRIPTION,TEMPLATE_PERMISSION,STANDARD_REPORT,PROMPT_FOR_CONTACT,"+
			"PDF_OUTPUT_SUPPORTED,RTF_OUTPUT_SUPPORTED,XML_OUTPUT_SUPPORTED,XLS_OUTPUT_SUPPORTED,DEFAULT_OUTPUT_FORMAT,LANGUAGE_CODE,DATE_FORMAT_CODE,BI_REPORT_ID,AUTO_UPLOAD,GENERATE_EMAIL) " +
			"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final String SQL_UPDATE_STRING = "UPDATE LAU_LAYOUT_TEMPLATES SET LAYOUT_TEMPLATE=?," +
			"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?,TEMPLATE_NAME=?,TEMPLATE_DESCRIPTION=?,TEMPLATE_PERMISSION=?,STANDARD_REPORT=?,PROMPT_FOR_CONTACT=?,"+
			"PDF_OUTPUT_SUPPORTED=?,RTF_OUTPUT_SUPPORTED=?,XML_OUTPUT_SUPPORTED=?,XLS_OUTPUT_SUPPORTED=?,DEFAULT_OUTPUT_FORMAT=?,LANGUAGE_CODE=?,DATE_FORMAT_CODE=?,AUTO_UPLOAD=?,GENERATE_EMAIL=?" +
			"WHERE DATA_TEMPLATE_ID=? AND LAYOUT_TEMPLATE_ID=?";
	private final String SQL_INSERT_LAU_QUESTION_STRING="INSERT INTO LAU_LAYOUT_QUESTIONS ( LAU_QUESTION_ID, LAYOUT_TEMPLATE_ID, "
			+ "DISPLAY_NUMBER,UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES(?,?,?,?,?)";

	public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {

		String	userId = CommonDAO.getUSERID(request);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		if(request.getParameter(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME).length() > 0) {

			log.info("LauLayoutTemplateDAO save() LAU_LAYOUT_TEMPLATE_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME));

			Iterator<LauLayoutTemplate> itr = this.layout.iterator();
			while(itr.hasNext())	{
				LauLayoutTemplate lauLayoutTemplate = (LauLayoutTemplate)itr.next();
				this.layoutTemplate	= null;
				this.layoutTemplate	= lauLayoutTemplate;
				transaction = lauLayoutTemplate.getTransactionType();
				if(lauLayoutTemplate.getTransactionType() == IReportsConstants.deleteFlag){
					deleteTemplate(template,userId);
				}else if(lauLayoutTemplate.getTransactionType() == 1)
					insert(datasource,request,transaction,template,userId,dt);
				else
					insertOrUpdateTemplate(datasource, request,transaction,SQL_UPDATE_STRING,template,userId,dt);

			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME + " not found in request");
		}

	}


	public void insert(DataSource datasource, HttpServletRequest request,long transactionType,
			SimpleJdbcTemplate template,String userId, java.sql.Timestamp dt) throws Exception {
		// Normal HTTP Attachments
		insertOrUpdateTemplate(datasource, request,transactionType,SQL_INSERT_STRING,template,userId,dt);
		updateLayoutQuestions(template, this.layoutTemplate.getQuestions(), userId, dt);
	}



	private void insertOrUpdateTemplate(DataSource datasource, HttpServletRequest request,long transactionType,
			String sql,SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Statement stmt = null;
		long biReportID = 0;
		boolean biServer = Boolean.valueOf(ReadConfig.getValue("BI_SERVER"));

		try	{

			MultipartHttpServletRequest httpRequest = (MultipartHttpServletRequest)request;
			MultipartFile file = httpRequest.getFile(this.layoutTemplate.getAttachment());
			String fileAsString = CommonDAO.getFileContents(file);

			con = datasource.getConnection();
			con.setAutoCommit(false);


			pst = (PreparedStatement)con.prepareStatement(sql);


			if(transactionType != 2){

				long layoutTemplateId = CommonDAO.getPrimaryKey(template);
				this.layoutTemplate.setLayoutTemplateId(layoutTemplateId);

				pst.setString(1, fileAsString);
				pst.setLong(2, this.layoutTemplate.getDataTemplateId());
				pst.setLong(3, this.layoutTemplate.getLayoutTemplateId());
				pst.setString(4, userId);
				pst.setTimestamp(5, dt);
				pst.setString(6, this.layoutTemplate.getTemplateName());
				pst.setString(7, this.layoutTemplate.getTemplateDescription());
				pst.setString(8, this.layoutTemplate.getTemplatePermission());
				pst.setString(9, this.layoutTemplate.getStdReport());
				pst.setString(10, this.layoutTemplate.getPromptContact());
				pst.setString(11, this.layoutTemplate.getPdfOutput());
				pst.setString(12, this.layoutTemplate.getRtfOutput());
				pst.setString(13, this.layoutTemplate.getXmlOutput());
				pst.setString(14, this.layoutTemplate.getXlsOutput());
				pst.setString(15, this.layoutTemplate.getDefaultOutputFormat());
				pst.setString(16, this.layoutTemplate.getLanguageCode());
				pst.setString(17, this.layoutTemplate.getDateFormat());

				if(biServer) {
					biReportID = CommonDAO.getPrimaryKey(template);
					pst.setLong(18, biReportID);
				}else {
					pst.setLong(18, biReportID);
				}
				pst.setString(19, this.layoutTemplate.getAutoUpload());
				pst.setString(20, this.layoutTemplate.getGenerateEmail());
			}

			else
			{	
				pst.setString(1, fileAsString);
				pst.setString(2, userId);
				pst.setTimestamp(3, dt);
				pst.setString(4, this.layoutTemplate.getTemplateName());
				pst.setString(5, this.layoutTemplate.getTemplateDescription());
				pst.setString(6, this.layoutTemplate.getTemplatePermission());
				pst.setString(7, this.layoutTemplate.getStdReport());
				pst.setString(8, this.layoutTemplate.getPromptContact());
				pst.setString(9, this.layoutTemplate.getPdfOutput());
				pst.setString(10, this.layoutTemplate.getRtfOutput());
				pst.setString(11, this.layoutTemplate.getXmlOutput());
				pst.setString(12, this.layoutTemplate.getXlsOutput());
				pst.setString(13, this.layoutTemplate.getDefaultOutputFormat());
				pst.setString(14, this.layoutTemplate.getLanguageCode());
				pst.setString(15, this.layoutTemplate.getDateFormat());
				pst.setString(16, this.layoutTemplate.getAutoUpload());
				pst.setString(17, this.layoutTemplate.getGenerateEmail());
				pst.setLong(18, this.layoutTemplate.getDataTemplateId());
				pst.setLong(19, this.layoutTemplate.getLayoutTemplateId());

			}

			boolean query = pst.execute();
			log.info(query);

			//BI Publisher 11g web service integration
			if(biServer) {

				BIPublisher bi = BIPublisherLoader.getBIPublisher();
				if(transactionType != 2){

					String biReportName = String.valueOf(biReportID);
					log.info("*** Start creating BI Publisher report template ***");	
					boolean rtn = bi.createReportWithDataModel(biReportName, String.valueOf(this.layoutTemplate.getDataTemplateId()), 
							String.valueOf(this.layoutTemplate.getLayoutTemplateId()), file.getBytes());
					log.info("*** End creating BI Publisher report template *** return -> "+ rtn);					

				} else {				

					String sq = "SELECT BI_REPORT_ID FROM LAU_LAYOUT_TEMPLATES WHERE LAYOUT_TEMPLATE_ID = ?";
					Long biRptID = template.queryForObject(sq, Long.class, this.layoutTemplate.getLayoutTemplateId());				
					log.info("BI_REPORT_ID for update -> " + biRptID);
					if(biRptID != null) {

						String biReportName = String.valueOf(biRptID);
						log.info("*** Start updating BI Publisher report template ***");
						boolean rtn = bi.updateTemplate(biReportName, String.valueOf(this.layoutTemplate.getLayoutTemplateId()), file.getBytes());
						log.info("*** End updating BI Publisher report template *** return -> "+ rtn);

					}				
				}
			}//****************************************

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

		layout			= new Vector<LauLayoutTemplate>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauLayoutTemplate.class );

		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/ATTACHMENT_NAME", "attachment" );
		digester.addBeanPropertySetter( mainXmlTag+"/LAYOUT_TEMPLATE", "layoutTemplate" );	
		digester.addBeanPropertySetter( mainXmlTag+"/DATA_TEMPLATE_ID", "dataTemplateId" );
		digester.addBeanPropertySetter( mainXmlTag+"/LAYOUT_TEMPLATE_ID", "layoutTemplateId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEMPLATE_NAME", "templateName" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEMPLATE_DESCRIPTION", "templateDescription" );
		digester.addBeanPropertySetter( mainXmlTag+"/TEMPLATE_PERMISSION", "templatePermission" );
		digester.addBeanPropertySetter( mainXmlTag+"/STANDARD_REPORT", "stdReport" );
		digester.addBeanPropertySetter( mainXmlTag+"/PROMPT_FOR_CONTACT", "promptContact" );
		digester.addBeanPropertySetter( mainXmlTag+"/PDF_OUTPUT_SUPPORTED", "pdfOutput" );
		digester.addBeanPropertySetter( mainXmlTag+"/RTF_OUTPUT_SUPPORTED", "rtfOutput" );
		digester.addBeanPropertySetter( mainXmlTag+"/XML_OUTPUT_SUPPORTED", "xmlOutput" );
		digester.addBeanPropertySetter( mainXmlTag+"/XLS_OUTPUT_SUPPORTED", "xlsOutput" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEFAULT_OUTPUT_FORMAT", "defaultOutputFormat" );
		digester.addBeanPropertySetter( mainXmlTag+"/LANGUAGE_CODE", "languageCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/DATE_FORMAT_CODE", "dateFormat" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/AUTO_UPLOAD", "autoUpload" );
		digester.addBeanPropertySetter( mainXmlTag+"/GENERATE_EMAIL", "generateEmail" );
		digester.addObjectCreate(mainXmlTag+"/LETTER_QUESTIONS/ROWSET/LAU_QUESTION", LauLayoutQuestions.class);
		digester.addBeanPropertySetter(mainXmlTag+"/LETTER_QUESTIONS/ROWSET/LAU_QUESTION/LAU_QUESTION_ID","LAU_QUESTION_ID");
		digester.addBeanPropertySetter(mainXmlTag+"/LETTER_QUESTIONS/ROWSET/LAU_QUESTION/QUESTION_TYPE","QUESTION_TYPE");

		digester.addSetNext(mainXmlTag + "/LETTER_QUESTIONS/ROWSET/LAU_QUESTION", "addLetterQuestions");

		//Move to next LauReportAttachments
		digester.addSetNext( mainXmlTag, "addXmlData" );
		digester.parse(new StringReader(xml));

	}

	public void addXmlData(LauLayoutTemplate lauLayoutTemplates) {
		layout.add(lauLayoutTemplates);
		log.info(lauLayoutTemplates.toString());
	}

	public void deleteTemplate(SimpleJdbcTemplate template, String userId) throws Exception {

		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_LAYOUT_TEMPLATES WHERE LAYOUT_TEMPLATE_ID = ?",
				new Object[]{this.layoutTemplate.getLayoutTemplateId()});
		template.update(SQL_DELETE_QUESTIONS_STRING, this.layoutTemplate.getLayoutTemplateId());
		log.info("LauLayoutTemplateDAO delete() ID -> " + id);

		//BI Publisher 11g web service integration
		boolean biServer = Boolean.valueOf(ReadConfig.getValue("BI_SERVER"));		
		if(biServer) {

			//Get the report ID to get the .xdo
			String sq = "SELECT BI_REPORT_ID FROM LAU_LAYOUT_TEMPLATES WHERE LAYOUT_TEMPLATE_ID = ?";
			Long biRptID = template.queryForObject(sq, Long.class, this.layoutTemplate.getLayoutTemplateId());				
			log.info("BI_REPORT_ID for update -> " + biRptID);
			if(biRptID != null) {

				log.info("*** Start deleting BI Publisher report template ***");
				BIPublisher bi = BIPublisherLoader.getBIPublisher();
				boolean rtn = bi.removeReport(String.valueOf(biRptID));
				log.info("*** End deleting BI Publisher report template *** return -> "+ rtn);				
			}
		}//***************************************	

	}
	private final String SQL_DELETE_QUESTIONS_STRING="DELETE FROM LAU_LAYOUT_QUESTIONS WHERE LAYOUT_TEMPLATE_ID =?";
	public void updateLayoutQuestions(SimpleJdbcTemplate template,ArrayList<LauLayoutQuestions> questions,String userId,Timestamp dt ){
		log.info("ID: "+this.layoutTemplate.getLayoutTemplateId());

		// Executing all sql operation in transaction 
		template.update(SQL_DELETE_QUESTIONS_STRING, this.layoutTemplate.getLayoutTemplateId());

		if(questions!=null && questions.size()>0){
			log.info("No of questions "+questions.size());

			int count=0;
			for (Iterator iterator = questions.iterator(); iterator.hasNext();) {
				LauLayoutQuestions lauLayoutQuestions = (LauLayoutQuestions) iterator
						.next();
				count++;
				template.update(SQL_INSERT_LAU_QUESTION_STRING,getParametersForLetterQuestions(lauLayoutQuestions,userId,dt,count));
			}
		}
	}
	private Object[] getParametersForLetterQuestions(LauLayoutQuestions lauLayoutQuestions,String userId, Timestamp dt,int displayNumber){
		log.info("Entered getParameters");
		//LauDataTemplate lauTemplate = this.dataTemplate; 
		log.info("Lau Question ID---->"+lauLayoutQuestions.getLAU_QUESTION_ID());
		log.info("Question Type---->"+lauLayoutQuestions.getQUESTION_TYPE());

		log.info("Id"+userId);
		log.info("DT"+dt);
		return new Object[]{ 
				lauLayoutQuestions.getLAU_QUESTION_ID(),
				this.layoutTemplate.getLayoutTemplateId(),
				displayNumber,
				userId,
				dt,
		};
	}	

}
