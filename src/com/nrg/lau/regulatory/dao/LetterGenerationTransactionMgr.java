package com.nrg.lau.regulatory.dao;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.commons.XMLException;
import com.nrg.lau.dao.CommonDAO;

import java.io.StringReader;
import 	java.sql.Timestamp; 
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LetterGenerationTransactionMgr {
	
	private DataSourceTransactionManager txManager;
	private static Logger log	= Logger.getLogger(LetterGenerationTransactionMgr.class);
	private SimpleJdbcTemplate template;
	private boolean activityCheck = false;
	private Vector<LauGeneratedLetters> reports		= null;
	private LauGeneratedLetters generatedLetters = null;

	public void setTxManager(DataSourceTransactionManager transactionManager){
        this.txManager = transactionManager;
        this.template = new SimpleJdbcTemplate(transactionManager.getDataSource());    
    }
	
	public String insertLetterGenValues(HttpServletRequest request, 
			HttpServletResponse response) {
		
		DefaultTransactionDefinition def	= new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status 			= txManager.getTransaction(def);
		String xmlStatus					= "";
		java.sql.Timestamp dt  = null; 
		String userId	= "";

		String fileName = request.getParameter("templateName") ;
		String genPrvwFlag = request.getParameter("genPrvwFlag") ;
		log.info("genPrvwFlag-------->"+genPrvwFlag);
		try	{
			
			log.info("In LetterGenerationTransactionMgr");
			userId = CommonDAO.getUSERID(request);
			log.warn ("USER ID is : " + userId );			
			
			dt = CommonDAO.getTimestamp(template);			
			LauGeneratedLettersDAO iReportParentSetDAO = new LauGeneratedLettersDAO();			
			HashMap<Long, List<String>> LetterGeneratedMap = iReportParentSetDAO.save(request, template, txManager.getDataSource(), userId, dt);
			if (LetterGeneratedMap != null && LetterGeneratedMap.size() > 0) {
				for (Long key : LetterGeneratedMap.keySet()) {
					List<String> list = LetterGeneratedMap.get(key);
					log.info("list Size: "+list.size());
					String letterID = list.get(0);
					String due = list.get(1);
					String generatedActivity = list.get(2);
					String outputFormat= list.get(3);
					long dispNo = 0;
					log.info("letterID: "+letterID+"due: "+due+"generatedActiivy: "+generatedActivity);
					Long generatedLetterID = Long.valueOf(letterID); 
					int dueDay = Integer.valueOf(due);
					log.info("generatedLetterID-----> "+generatedLetterID+"dueDay------>"+dueDay);
					if(generatedLetterID != 0){
						log.info("GeneratedID found!!!");
						IReportChildSetDAO<LauIncludedQuestions> lauIncludedQuestionsDAO = new LauIncludedQuestionsDAO();
						lauIncludedQuestionsDAO.save(request, template, generatedLetterID, userId, dt);
						
						LauIncludedReportsDAO lauIncludedReportsDAO = new LauIncludedReportsDAO();
						String[] arrRepIds = lauIncludedReportsDAO.save(request, template, generatedLetterID, userId, dt);						
						for( int j = 0; j < arrRepIds.length ; j++)
						{ 
							log.info("ARRAY_REPORT_IDS["+j+"]--------------------"+arrRepIds[j]);
						}
						
						if(genPrvwFlag.trim().equals("G")){
							log.info("Inside Gen act and attach!!!");
						//if((generatedActivity != null) && (generatedActivity.toUpperCase().trim().equals("Y"))  ){
							log.info("Going inside the report loop next!!!!");
							for( int i = 0; i < arrRepIds.length ; i++)
							{   
								dispNo = dispNo + 1;
								long actId = 0;
								log.info("LetterGenerationTransactionManager  Report ID -> " + arrRepIds[i]);
								if((generatedActivity != null) && (generatedActivity.toUpperCase().trim().equals("Y"))  ){
									actId = insertActivities(template,userId,dt,arrRepIds[i],generatedLetterID,dueDay,dispNo);
								}
								insertAttachment(template,userId,dt,arrRepIds[i],generatedLetterID,outputFormat,actId,fileName);
								log.info("attachId: "+actId);
							
						 }
						}
						
						xmlStatus = XMLException.status("Generated Letter save was successful ", generatedLetterID);
						txManager.commit(status);
					}
				}
			}
		
		
		}catch (Exception e) {			
			log.error(e);
			txManager.rollback(status);
			xmlStatus = XMLException.xmlError(e, "Letter Generation failed");			
		}
		
		return xmlStatus;
		
	}
	
	public String insertLetterAttAct(HttpServletRequest request, 
			HttpServletResponse response) {
		log.info("insertLetterAttAct----------->");
		String userId = "";
		String fileName = "";
		String reportId = "";
		String dueInDays = "";
		String outputFormat = "";
		String generatedActivity = "";
		String xmlStatus = "";
		long dispNo = 0;
		int dueDay = 0;
		ArrayList<String> arrRepId = new ArrayList<String>();

		try {
			java.sql.Timestamp dt = null;
			fileName = request.getParameter("templateName");
			String letterId = request.getParameter("generatedLetterId");			
			long generatedLetterID = Long.parseLong(letterId);
			log.info("generatedLetterID----------->" + generatedLetterID);

			userId = CommonDAO.getUSERID(request);
			log.warn("USER ID is : " + userId);
			dt = CommonDAO.getTimestamp(template);

			if (request
					.getParameter(IReportsConstants.LAU_GENERATED_LETTER_PARAM_NAME) != null
					&& request.getParameter(
							IReportsConstants.LAU_GENERATED_LETTER_PARAM_NAME)
							.length() > 0) {

				log.info("LAU_GENERATED_LETTER_PARAM_NAME ----> "
						+ request
								.getParameter(IReportsConstants.LAU_GENERATED_LETTER_PARAM_NAME));

				// Create LauGeneratedLetters beans from XML Request
				createBeansFromXml(request
						.getParameter(IReportsConstants.LAU_GENERATED_LETTER_PARAM_NAME));
				Iterator<LauGeneratedLetters> itr = this.reports.iterator();
				while (itr.hasNext()) {
					LauGeneratedLetters lauGeneratedLetters = (LauGeneratedLetters) itr
							.next();
					log.info("itr.hasNext() -> "
							+ lauGeneratedLetters.toString());
					this.generatedLetters = null;
					this.generatedLetters = lauGeneratedLetters;
					generatedActivity = generatedLetters.getGENERATE_ACTIVITY();
					outputFormat = generatedLetters.getOUTPUT_FORMAT();
					long genLetID = generatedLetters
							.getLAU_GENERATED_LETTER_ID();
					log.info("genLetID ---->" + genLetID);
					log.info("generatedActivity ---->" + generatedActivity);
					log.info("outputFormat---->" + outputFormat);
				}
			}
			log.info("generatedActivity1---->" + generatedActivity);
			log.info("outputFormat1---->" + outputFormat);

			String getGenLtrSQL = "select * from LAU_GENERATED_LETTERS where lau_generated_letter_id = ?";
			List<Map<String, Object>> rws = template.queryForList(getGenLtrSQL,
					new Object[] { generatedLetterID });
			for (Map<String, Object> row : rws) {
				dueInDays = row.get("DUE_IN_DAYS").toString();
			}
			log.info("dueInDays---->" + dueInDays);
			dueDay = Integer.parseInt(dueInDays);
			log.info("dueDay---->" + dueDay);
			log.info("fileName1---->" + fileName);
			log.info("dt---->" + dt);

			String getRepIdSQL = "select REPORT_ID from LAU_INCLUDED_REPORTS where lau_generated_letter_id = ?";
			List<Map<String, Object>> rows = template.queryForList(getRepIdSQL,
					new Object[] { generatedLetterID });
			for (Map<String, Object> row : rows) {
				reportId = row.get("report_id").toString();
				arrRepId.add(reportId);
			}
			log.info("reportId---->" + reportId);
			for (int i = 0; i < arrRepId.size(); i++) {
				log.info("2nd arrayREPID["+i+"]-------->"+arrRepId.get(i));
			}
			/*if ((generatedActivity != null)
					&& (generatedActivity.toUpperCase().trim().equals("Y"))) {*/
			log.info("arrRepId.size(): "+arrRepId.size());
				for (int i = 0; i < arrRepId.size(); i++) {
					dispNo = dispNo + 1;
					long activityID = 0;
					if ((generatedActivity != null)
							&& (generatedActivity.toUpperCase().trim().equals("Y"))) {
						activityID = insertActivities(template, userId, dt, arrRepId.get(i),
							generatedLetterID, dueDay,  dispNo);
						log.info("attachId: " + activityID);
					}
					log.info("insert attachment");
					insertAttachment(template, userId, dt,
							arrRepId.get(i), generatedLetterID, outputFormat,activityID,
							fileName);
					
				}
			//}
			xmlStatus = XMLException.status(
					"Generated Letter save was successful ", generatedLetterID);
		} catch (Exception e) {
			log.error(e);
			xmlStatus = XMLException.xmlError(e, "Letter Generation failed");
		}

		return xmlStatus;
	}
	
	public void deletePreviewData(String letterId){
		log.info("deletePreviewData start--------->");
		long genLetId = Long.parseLong(letterId);
		int id1 = template.update("DELETE FROM LAU_INCLUDED_REPORTS WHERE lau_generated_letter_id = ?",genLetId);
		log.info("delete() for LAU_INCLUDED_REPORTS ----> " + id1);
		int id2 = template.update("DELETE FROM LAU_INCLUDED_QUESTIONS WHERE lau_generated_letter_id = ?",genLetId);
		log.info("delete() for LAU_INCLUDED_QUESTIONS ----> " + id2);
		int id3 = template.update("DELETE FROM LAU_GENERATED_LETTERS WHERE lau_generated_letter_id = ?",genLetId);
		log.info("delete() for LAU_GENERATED_LETTERS ----> " + id3);
		log.info("deletePreviewData end--------->");
	}

	private void insertAttachment(SimpleJdbcTemplate template, String userId,Timestamp dt,String repId, Long generatedLetterID, String outputFormat,Long activityID, String fileName) {
		// TODO Auto-generated method stub
		int id = 0;
		long attachmentID = 0;
		attachmentID = CommonDAO.getPrimaryKey(template);
		id = template.update(SQL_INSERT_STRING_ATTACH,getAttachmentParameters(userId,dt,repId,generatedLetterID,attachmentID,outputFormat,activityID,fileName));
		log.info("LauIncludedReportsDAO insert() ID -> " + id);
		
	}
	
	private Object[] getAttachmentParameters(String userId,Timestamp dt,String strRepId,Long generatedLetterID,Long attachId, String outputFormat, Long activityID, String fileName) {
		Calendar date = new GregorianCalendar();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd:hhmm");	
		return new Object[]{			
			userId,
			dt,
			attachId,
			activityID,
			generatedLetterID,
			strRepId,
			fileName+" "+formatter.format(date.getTime()),
			"GENERATED LETTER",
			fileName+"_"+formatter.format(date.getTime())+"."+outputFormat
		};
	}
	
	private long insertActivities(SimpleJdbcTemplate template,String userId,Timestamp dt,String repId, Long generatedLetterID,int dueDay,Long dispNo) {
		// TODO Auto-generated method stub
		int id = 0;
		log.info("******insertActivities:");
		long activitiesID = CommonDAO.getPrimaryKey(template);
		log.info("activitiesID insert() ID -> " + activitiesID);
		id = template.update(SQL_INSERT_STRING_ACT ,getActivitiesParameters(userId,dt,repId,activitiesID,generatedLetterID,dueDay,dispNo));
		log.info("LauIncludedReportsDAO insert() ID -> " + id);
		return	activitiesID;
	}
	
	private Object[] getActivitiesParameters(String userId,Timestamp dt,String strRepId,Long activitesID,Long generatedLetterID,int dueDay,Long dispNo) {
		DateFormat dateFormat =  new SimpleDateFormat("yyyyMMdd");
		
    	Calendar date = new GregorianCalendar();

        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.set(Calendar.MINUTE, 0);
		
		String actDate = dateFormat.format(date.getTime());
		log.info("actDate: "+actDate);
	
		date.add(Calendar.DAY_OF_MONTH, dueDay);

		String dueInDay = dateFormat.format(date.getTime());
		log.info("dueInDay: "+dueInDay);
		String direction= "OUT";
		String actType  = "AI Letter";
		log.info("ReportID: "+strRepId);
		return new Object[]{			
			userId,
			dt,
			strRepId,
			activitesID,
			generatedLetterID,
			dispNo,
		//	attachId,
			actDate,
			dueInDay,
			actType,
			direction
		};
	}
	private final String SQL_INSERT_STRING_ATTACH = "INSERT INTO LAU_REPORT_ATTACHMENTS(UPDATE_USER_ID,UPDATE_TIMESTAMP,ATTACHMENT_ID,ACTIVITY_ID,LAU_GENERATED_LETTER_ID," +
			"REPORT_ID,DOCUMENT_NAME,DOCUMENT_DESCRIPTION,FILE_NAME" +
			") VALUES (?,?,?,?,?,?,?,?,?)";
	private final String SQL_INSERT_STRING_ACT = "INSERT INTO LAU_REPORT_ACTIVITIES(UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORT_ID," +
			"ACTIVITY_ID,LAU_GENERATED_LETTER_ID,DISPLAY_NUMBER,ACTIVITY_DATE,DUE_DATE,ACTIVITY_TYPE,DIRECTION) VALUES (?,?,?,?,?,?,?,?,?,?)";
	
public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag 	= "ROWSET/ROW";
		reports				= new Vector<LauGeneratedLetters>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauGeneratedLetters.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/LAU_GENERATED_LETTER_ID", "LAU_GENERATED_LETTER_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/LAYOUT_TEMPLATE_ID", "LAYOUT_TEMPLATE_ID" );
		digester.addBeanPropertySetter( mainXmlTag+"/DUE_IN_DAYS", "DUE_IN_DAYS" );
		digester.addBeanPropertySetter( mainXmlTag+"/BATCH_PRINTED", "BATCH_PRINTED" );
		digester.addBeanPropertySetter( mainXmlTag+"/GENERATION_COMMENT", "GENERATION_COMMENT" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_ADDRESSEE_TYPE", "LETTER_ADDRESSEE_TYPE" );		
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_CONTACT_SOURCE", "ADDRESSEE_CONTACT_SOURCE" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_CONTACT_EDITED", "ADDRESSEE_CONTACT_EDITED" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_TITLE", "ADDRESSEE_TITLE" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_FIRST_NAME", "ADDRESSEE_FIRST_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_LAST_NAME", "ADDRESSEE_LAST_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_PHONE_CC", "ADDRESSEE_PHONE_CC" );		
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_PHONE_NO", "ADDRESSEE_PHONE_NO" );		
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_PHONE_EXT", "ADDRESSEE_PHONE_EXT" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_EMAIL", "ADDRESSEE_EMAIL" );
		digester.addBeanPropertySetter( mainXmlTag+"/ADDRESSEE_COMPANY_NAME", "ADDRESSEE_COMPANY_NAME" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_ADDRESS1", "LETTER_ADDRESS1" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_ADDRESS2", "LETTER_ADDRESS2" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_ADDRESS3", "LETTER_ADDRESS3" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_ADDRESS4", "LETTER_ADDRESS4" );		
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_CITY", "LETTER_CITY" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_STATE", "LETTER_STATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_POSTAL_CODE", "LETTER_POSTAL_CODE" );
		digester.addBeanPropertySetter( mainXmlTag+"/LETTER_COUNTRY", "LETTER_COUNTRY" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "UPDATE_USER_ID" );	
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "UPDATE_TIMESTAMP" );
		digester.addBeanPropertySetter( mainXmlTag+"/GENERATE_ACTIVITY", "GENERATE_ACTIVITY" );		
		digester.addBeanPropertySetter( mainXmlTag+"/OUTPUT_FORMAT", "OUTPUT_FORMAT" );	
		
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));	
		
	}

	public void addXmlData(LauGeneratedLetters lauGeneratedLetters) {
			reports.add(lauGeneratedLetters);
	}
}

