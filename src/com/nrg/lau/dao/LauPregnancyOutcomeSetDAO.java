package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauPregnancyOutcome;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauPregnancyOutcomeSetDAO implements IReportChildSetDAO<LauPregnancyOutcome>{

	private static Logger log	= Logger.getLogger(LauPregnancyOutcomeSetDAO.class);
	private Vector<LauPregnancyOutcome> reports		= null;
	private LauPregnancyOutcome pregnancyOutcome = null;	
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
		if(request.getParameter(IReportsConstants.LAU_PREGNANCY_OUTCOME_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_PREGNANCY_OUTCOME_PARAM_NAME).length() > 0) {
			
			log.info("LauPregnancyOutcomeDAO save() LAU_PREGNANCY_OUTCOME_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_PREGNANCY_OUTCOME_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_PREGNANCY_OUTCOME_PARAM_NAME));
			Iterator<LauPregnancyOutcome> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauPregnancyOutcome lauPregnancyOutcome = (LauPregnancyOutcome)itr.next();
				log.info("itr.hasNext() -> " + lauPregnancyOutcome.toString());
				this.pregnancyOutcome	= null;
				this.pregnancyOutcome	= lauPregnancyOutcome;
				if(lauPregnancyOutcome.getPregnancyOutcomeId() == 0	)	{
						insert(template,reportId);
				}else   {
					if(lauPregnancyOutcome.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_PREGNANCY_OUTCOME_PARAM_NAME + " not found in request");
		}
		
	}

	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long pregnancyOutcomeId = CommonDAO.getPrimaryKey(template);
		this.pregnancyOutcome.setPregnancyOutcomeId(pregnancyOutcomeId);
		if(this.pregnancyOutcome.getReportId() == 0)	{
			this.pregnancyOutcome.setReportId(reportId);	}
		log.info("Generated Primary Key for PREGNANCY_OUTCOME_ID  ->" + pregnancyOutcomeId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauPregnancyOutcomeDAO insert() ID -> " + id);
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauPregnancyOutcomeDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters() {
		
		LauPregnancyOutcome lauPregnancyOutcome = this.pregnancyOutcome; 
		return new Object[]{
				lauPregnancyOutcome.getDisplayNumber(),
				lauPregnancyOutcome.getSex(),
				lauPregnancyOutcome.getWeight(),
				lauPregnancyOutcome.getLength(),
				lauPregnancyOutcome.getHeadCircumference(),
				lauPregnancyOutcome.getApgarScore1(),
				lauPregnancyOutcome.getApgarScore2(),
				lauPregnancyOutcome.getAbnormalitiesFlag(),
				lauPregnancyOutcome.getAbnormalitiesComments(),
				lauPregnancyOutcome.getDeliveryDate(),
				lauPregnancyOutcome.getDeliveryTime(),
				lauPregnancyOutcome.getDeliveryMode(),
				lauPregnancyOutcome.getDeliveryDetails(),
				lauPregnancyOutcome.getPlacentaCondition(),
				lauPregnancyOutcome.getPlacentaConditionComments(),
				lauPregnancyOutcome.getDeliveryComments(),
				userId,
				dt,
				lauPregnancyOutcome.getDeliveryComplications(),
				
				lauPregnancyOutcome.getPregOutcome(),
				lauPregnancyOutcome.getPregOutcomeComments(),
				lauPregnancyOutcome.getPregOutcomeDate(),
				lauPregnancyOutcome.getPregTermAtOutcome(),
				lauPregnancyOutcome.getPregTermAtOutcomeUnits(),				
				lauPregnancyOutcome.getReportId(),
				lauPregnancyOutcome.getPregnancyOutcomeId()				
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauPregnancyOutcome>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauPregnancyOutcome.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/PREGNANCY_OUTCOME_ID", "pregnancyOutcomeId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNumber2" );
		digester.addBeanPropertySetter( mainXmlTag+"/SEX", "sex" );
		digester.addBeanPropertySetter( mainXmlTag+"/WEIGHT", "weight" );
		digester.addBeanPropertySetter( mainXmlTag+"/LENGTH", "length" );
		digester.addBeanPropertySetter( mainXmlTag+"/HEAD_CIRCUMFERENCE", "headCircumference" );
		digester.addBeanPropertySetter( mainXmlTag+"/APGAR_SCORE_1", "apgarScore1" );
		digester.addBeanPropertySetter( mainXmlTag+"/APGAR_SCORE_2", "apgarScore2" );
		digester.addBeanPropertySetter( mainXmlTag+"/ABNORMALITIES_FLAG", "abnormalitiesFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/ABNORMALITIES_COMMENTS", "abnormalitiesComments" );
		digester.addBeanPropertySetter( mainXmlTag+"/DELIVERY_DATE", "deliveryDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/DELIVERY_TIME", "deliveryTime" );
		digester.addBeanPropertySetter( mainXmlTag+"/DELIVERY_MODE", "deliveryMode" );
		digester.addBeanPropertySetter( mainXmlTag+"/DELIVERY_DETAILS", "deliveryDetails" );
		digester.addBeanPropertySetter( mainXmlTag+"/PLACENTA_CONDITION", "placentaCondition" );
		digester.addBeanPropertySetter( mainXmlTag+"/PLACENTA_CONDITION_COMMENTS", "placentaConditionComments" );
		digester.addBeanPropertySetter( mainXmlTag+"/DELIVERY_COMMENTS", "deliveryComments" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/DELIVERY_COMPLICATIONS", "deliveryComplications" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_OUTCOME", "pregOutcome" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_OUTCOME_COMMENTS", "pregOutcomeComments" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_OUTCOME_DATE", "pregOutcomeDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_TERM_AT_OUTCOME", "pregTermAtOutcome" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_TERM_AT_OUTCOME_UNITS", "pregTermAtOutcomeUnits" );		
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
						
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauPregnancyOutcome lauPregnancyOutcome) {
		reports.add(lauPregnancyOutcome);
		log.info(lauPregnancyOutcome.toString());
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_PREGNANCY_OUTCOME SET DISPLAY_NUMBER=?,SEX=?," +
		"WEIGHT=?,LENGTH=?,HEAD_CIRCUMFERENCE=?,APGAR_SCORE_1=?,APGAR_SCORE_2=?,ABNORMALITIES_FLAG=?," +
		"ABNORMALITIES_COMMENTS=?,DELIVERY_DATE=?,DELIVERY_TIME=?,DELIVERY_MODE=?,DELIVERY_DETAILS=?," +
		"PLACENTA_CONDITION=?,PLACENTA_CONDITION_COMMENTS=?,DELIVERY_COMMENTS=?,UPDATE_USER_ID=?," +
		"UPDATE_TIMESTAMP=?,DELIVERY_COMPLICATIONS=?,"+
		"PREG_OUTCOME =? ,PREG_OUTCOME_COMMENTS =? ,PREG_OUTCOME_DATE =? ,PREG_TERM_AT_OUTCOME =? ,PREG_TERM_AT_OUTCOME_UNITS =? "+
		" WHERE REPORT_ID=? AND PREGNANCY_OUTCOME_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PREGNANCY_OUTCOME (DISPLAY_NUMBER,SEX,WEIGHT,LENGTH," +
		"HEAD_CIRCUMFERENCE,APGAR_SCORE_1,APGAR_SCORE_2,ABNORMALITIES_FLAG,ABNORMALITIES_COMMENTS," +
		"DELIVERY_DATE,DELIVERY_TIME,DELIVERY_MODE,DELIVERY_DETAILS,PLACENTA_CONDITION,PLACENTA_CONDITION_COMMENTS," +
		"DELIVERY_COMMENTS,UPDATE_USER_ID,UPDATE_TIMESTAMP,DELIVERY_COMPLICATIONS,"+
		"PREG_OUTCOME,PREG_OUTCOME_COMMENTS,PREG_OUTCOME_DATE,PREG_TERM_AT_OUTCOME,PREG_TERM_AT_OUTCOME_UNITS,"+
		" REPORT_ID,PREGNANCY_OUTCOME_ID) VALUES" +
		"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_PREGNANCY_OUTCOME WHERE PREGNANCY_OUTCOME_ID = ?",
				new Object[]{this.pregnancyOutcome.getPregnancyOutcomeId()});
		log.info("LauPregnancyOutcomeSetDAO delete() ID -> " + id);			
	}
	
	/**
	 	LAU_PREGNANCY_OUTCOME
	 
	 	PREGNANCY_OUTCOME_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		DISPLAY_NUMBER NUMBER(22 , 0) NOT NULL,
		PREG_OUTCOME VARCHAR2(50),
		PREG_OUTCOME_DATE VARCHAR2(8),
		PREG_OUTCOME_COMMENTS VARCHAR2(4000),
		PREG_TERM_AT_OUTCOME NUMBER(5 , 0),
		PREG_TERM_AT_OUTCOME_UNITS VARCHAR2(50),
		SEX VARCHAR2(50),
		WEIGHT NUMBER(5 , 2),
		WEIGHT_UNITS VARCHAR2(50),
		LENGTH NUMBER(5 , 2),
		LENGTH_UNITS VARCHAR2(50),
		HEAD_CIRCUMFERENCE NUMBER(5 , 2),
		APGAR_SCORE_1 NUMBER(5 , 2),
		APGAR_SCORE_2 NUMBER(5 , 2),
		ABNORMALITIES_FLAG VARCHAR2(50),
		ABNORMALITIES_COMMENTS VARCHAR2(4000),
		DELIVERY_DATE VARCHAR2(6),
		DELIVERY_TIME VARCHAR2(6),
		DELIVERY_MODE VARCHAR2(50),
		DELIVERY_DETAILS VARCHAR2(4000),
		PLACENTA_CONDITION VARCHAR2(50),
		PLACENTA_CONDITION_COMMENTS VARCHAR2(4000),
		DELIVERY_COMMENTS VARCHAR2(4000),
		DELIVERY_COMPLICATIONS VARCHAR2(50),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP null NOT NULL
	 */

}
