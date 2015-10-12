package com.nrg.lau.dao;


import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauLayoutQuestions;
import com.nrg.lau.beans.LauLayoutTemplate;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.service.bi.BIPublisher;
import com.nrg.lau.service.bi.BIPublisherLoader;
import com.nrg.lau.utils.ReadConfig;

public class LauLayoutTemplateDAO {

	private Vector<LauLayoutTemplate> layout	= null;
	private LauLayoutTemplate layoutTemplate	= null;
	private static Logger log	= Logger.getLogger(LauLayoutTemplateDAO.class);
	//	private java.sql.Timestamp dt  = null; 
	//	private String userId	= "";



	private final String SQL_UPDATE_STRING = "UPDATE LAU_LAYOUT_TEMPLATES SET UPDATE_USER_ID=?," +
			"UPDATE_TIMESTAMP=?,TEMPLATE_NAME=?,TEMPLATE_DESCRIPTION=?,TEMPLATE_PERMISSION=?,STANDARD_REPORT=?,PROMPT_FOR_CONTACT=?,"+
			"PDF_OUTPUT_SUPPORTED=?,RTF_OUTPUT_SUPPORTED=?,XML_OUTPUT_SUPPORTED=?,XLS_OUTPUT_SUPPORTED=?,DEFAULT_OUTPUT_FORMAT=?,LANGUAGE_CODE=?,DATE_FORMAT_CODE=?,DATA_TEMPLATE_ID=?,AUTO_UPLOAD=?,GENERATE_EMAIL=?  " +
			" WHERE  LAYOUT_TEMPLATE_ID=?";

	private final String SQL_INSERT_STRING="INSERT INTO LAU_LAYOUT_QUESTIONS ( LAU_QUESTION_ID, LAYOUT_TEMPLATE_ID, "
			+ "DISPLAY_NUMBER,UPDATE_USER_ID,UPDATE_TIMESTAMP) VALUES(?,?,?,?,?)";

	private final String SQL_DELETE_QUESTIONS_STRING="DELETE FROM LAU_LAYOUT_QUESTIONS WHERE LAYOUT_TEMPLATE_ID =?";
	/*public void save(HttpServletRequest request,SimpleJdbcTemplate template,
			DataSource datasource) throws Exception {*/
	public void save(HttpServletRequest request,SimpleJdbcTemplate template) throws Exception {
		log.info("Entered SAVE!!!");
		String	userId = CommonDAO.getUSERID(request);
		log.info("USERID: "+userId);
		java.sql.Timestamp dt = CommonDAO.getTimestamp(template);
		log.info("TimeStamp: "+dt.toString());
		log.info("To check if null: "+request.getParameter(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME));
		if(request.getParameter(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME).length() > 0) {

			log.info("LauLayoutTemplateDAO save() LAU_LAYOUT_TEMPLATE_PARAM_NAME -> "
					+ request.getParameter(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME));
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME));

			Iterator<LauLayoutTemplate> itr = this.layout.iterator();
			while(itr.hasNext())	{
				LauLayoutTemplate lauLayoutTemplate = (LauLayoutTemplate)itr.next();
				log.info("itr.hasNext() -> " + lauLayoutTemplate.toString());
				this.layoutTemplate	= null;
				this.layoutTemplate	= lauLayoutTemplate;
				log.info("LAYOUT NAME: "+lauLayoutTemplate.getTemplateName());
				log.info("TRANSACTIONTYPE:::::::::::::"+lauLayoutTemplate.getTransactionType());
				if(lauLayoutTemplate.getTransactionType() == 2){
					log.info("LauLayoutTemplateDAO - save() -> [TRANSACTION_TYPE 1 & 2]: " + lauLayoutTemplate.toString());
					//updateTemplate(datasource,request,template,userId,dt);
					updateTemplate(template,userId,dt); // PR-766 By vinay.kumar
				}	
				if(lauLayoutTemplate.getTransactionType() == IReportsConstants.deleteFlag){
					log.info("lauLayoutTemplateDAO - save() -> [TRANSACTION_TYPE 0]: " + lauLayoutTemplate.toString());
					deleteTemplate(template, userId); 
				}

			}//end of while(itr.hasNext())

		}	else {
			log.info(IReportsConstants.LAU_LAYOUT_TEMPLATE_PARAM_NAME + " not found in request");
		}

	}

	private Object[] getParameters(LauLayoutTemplate lauTemplate, String userId, java.sql.Timestamp dt)
	{	
		log.info("Entered getParameters");
		//LauDataTemplate lauTemplate = this.dataTemplate; 
		log.info("Temp Name---->"+lauTemplate.getTemplateName());
		log.info("Temp DESC---->"+lauTemplate.getTemplateDescription());
		log.info("Temp Report---->"+lauTemplate.getStdReport());
		log.info("Temp Report---->"+lauTemplate.getDateFormat());
		log.info("Temp Report---->"+lauTemplate.getLanguageCode());
		log.info("Temp Id---->"+lauTemplate.getDataTemplateId());
		log.info("Layout Id---->"+lauTemplate.getLayoutTemplateId());
		log.info("Id"+userId);
		log.info("DT"+dt);
		return new Object[]{
				userId,
				dt,
				lauTemplate.getTemplateName(),
				lauTemplate.getTemplateDescription(),
				lauTemplate.getTemplatePermission(),
				lauTemplate.getStdReport(),
				lauTemplate.getPromptContact(),
				lauTemplate.getPdfOutput(),
				lauTemplate.getRtfOutput(),
				lauTemplate.getXmlOutput(),
				lauTemplate.getXlsOutput(),
				lauTemplate.getDefaultOutputFormat(),
				lauTemplate.getLanguageCode(),
				lauTemplate.getDateFormat(),
				lauTemplate.getDataTemplateId(),
				lauTemplate.getAutoUpload(),
				lauTemplate.getGenerateEmail(),
				lauTemplate.getLayoutTemplateId()

		};

	}	

	private void createBeansFromXml(String xml) throws Exception {

		layout			= new Vector<LauLayoutTemplate>();
		String mainXmlTag 	= "ROWSET/ROW";
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauLayoutTemplate.class );

		//Explicitly specify property
		//digester.addBeanPropertySetter( mainXmlTag+"/LAYOUT_TEMPLATE", "layoutTemplate" );	
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
		// PR-766 Start By Vinay.Kumar
		digester.addObjectCreate(mainXmlTag+"/LETTER_QUESTIONS/LAU_QUESTION", LauLayoutQuestions.class);
		digester.addBeanPropertySetter(mainXmlTag+"/LETTER_QUESTIONS/LAU_QUESTION/LAU_QUESTION_ID","LAU_QUESTION_ID");
		digester.addBeanPropertySetter(mainXmlTag+"/LETTER_QUESTIONS/LAU_QUESTION/QUESTION_TYPE","QUESTION_TYPE");

		digester.addSetNext(mainXmlTag + "/LETTER_QUESTIONS/LAU_QUESTION", "addLetterQuestions");
		//PR-766 End By Vinay.Kumar
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

		log.info("LauLayoutTemplateDAO delete() ID -> " + id);		
		id = template.update(SQL_DELETE_QUESTIONS_STRING, this.layoutTemplate.getLayoutTemplateId());
		
		log.info("LauLayoutTemplateDAO delete() LAU_LAYOUT_QUESTIONS ID -> " + id);
		
		//BI Publisher 11g web service integration
		//If using BI WebService we have to remove the template from the report
		boolean biServer = Boolean.valueOf(ReadConfig.getValue("BI_SERVER"));
		if(biServer) {
			
			//Get the report ID to get the .xdo
			String sq = "SELECT BI_REPORT_ID FROM LAU_LAYOUT_TEMPLATES WHERE LAYOUT_TEMPLATE_ID = ?";
			Long biRptID = template.queryForObject(sq, Long.class, this.layoutTemplate.getLayoutTemplateId());				
			log.info("biReportID for update -> " + biRptID);
			if(biRptID != null) {
				
				log.info("*** Start deleting BI Publisher report template ***");
				BIPublisher bi = BIPublisherLoader.getBIPublisher();
				boolean rtn = bi.removeReport(String.valueOf(biRptID));
				log.info("*** End deleting BI Publisher datamodel *** return -> "+ rtn);
			}
		}//**************************************

	}

	/*public void updateTemplate(DataSource datasource, HttpServletRequest request,
			SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {*/
	public void updateTemplate(	SimpleJdbcTemplate template, String userId,java.sql.Timestamp dt)	throws Exception {
		int id = 0;

		log.info("ID: "+this.layoutTemplate.getDataTemplateId());
		log.info("ID: "+this.layoutTemplate.getLayoutTemplateId());
		log.info("DT----->"+dt);
		log.info("userid----->"+userId);
		log.info("this.dataTemplate.getTemplateName()--->"+ this.layoutTemplate.getTemplateName());
		log.info("this.dataTemplate.getTemplateDescription()--->"+this.layoutTemplate.getTemplateDescription());
		log.info("this.dataTemplate.getStdReport()--->"+this.layoutTemplate.getStdReport());
		log.info("this.dataTemplate.dateformat--->"+this.layoutTemplate.getDateFormat());
		id = template.update(SQL_UPDATE_STRING,getParameters(layoutTemplate,userId,dt));
		log.info("LauDataTemplateDAO delete() ID -> " + id);

		updateLayoutQuestions(template, this.layoutTemplate.getQuestions(), userId, dt);
		

	}
	/**
	 * PR-766 -Assign Letter Questions to a Layout Template
	 *
	 * Update layout Questions related to template layout.
	 * @author vinay.kumar
	 * @param template
	 * @param questions
	 * @param userId
	 * @param dt
	 */
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
				template.update(SQL_INSERT_STRING,getParametersForLetterQuestions(lauLayoutQuestions,userId,dt,count));
			}
		}
	}
	/**
	 *  PR-766 - Assign Letter Questions to a Layout Template
	 *  @author vinay.kumar
	 * @param lauLayoutQuestions
	 * @param userId
	 * @param dt
	 * @return
	 */
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
