package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauPregnancyDetails;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauPregnancyDetailsSetDAO implements IReportChildSetDAO<LauPregnancyDetails> {
	
	private static Logger log	= Logger.getLogger(LauPregnancyDetailsSetDAO.class);
	private Vector<LauPregnancyDetails> reports		= null;
	private LauPregnancyDetails pregnancyDetails 	= null;	
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
		if(request.getParameter(IReportsConstants.LAU_PREGNANCY_DETAILS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_PREGNANCY_DETAILS_PARAM_NAME).length() > 0) {
			
			log.info("LauPregnancyDetailsDAO save() LAU_PREGNANCY_DETAILS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_PREGNANCY_DETAILS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_PREGNANCY_DETAILS_PARAM_NAME));
			Iterator<LauPregnancyDetails> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauPregnancyDetails lauPregnancyDetails = (LauPregnancyDetails)itr.next();
				log.info("itr.hasNext() -> " + lauPregnancyDetails.toString());
				this.pregnancyDetails	= null;
				this.pregnancyDetails	= lauPregnancyDetails;
				if(lauPregnancyDetails.getPregnancyDetailsId() == 0	)	{
						insert(template,reportId);
				}else {
					if(lauPregnancyDetails.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_PREGNANCY_DETAILS_PARAM_NAME + " not found in request");
		}
		
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long pregnancyDetailsId = CommonDAO.getPrimaryKey(template);
		this.pregnancyDetails.setPregnancyDetailsId(pregnancyDetailsId);
		if(this.pregnancyDetails.getReportId() == 0)	{
			this.pregnancyDetails.setReportId(reportId);	}
		log.info("Generated Primary Key for PREGNANCY_DETAILS_ID ->" + pregnancyDetailsId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauPregnancyDetailsDAO insert() ID -> " + id);
		
	}	

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauPregnancyDetailsDAO update() ID -> " + id);
		
	}
	
	private Object[] getParameters()
	{
		LauPregnancyDetails lauPregnancyDetails = this.pregnancyDetails; 
		return new Object[]{
				lauPregnancyDetails.getPregTestPerformed(),
				lauPregnancyDetails.getPregTestDetails(),
				lauPregnancyDetails.getPregExposureStatus(),
				lauPregnancyDetails.getEligibleForPregRegistry(),
				lauPregnancyDetails.getConsentToContactPt(),
				lauPregnancyDetails.getPastPregnancyCount(),
				lauPregnancyDetails.getPastPregnancyComments(),
				lauPregnancyDetails.getPastLiveBirthCount(),
				lauPregnancyDetails.getPastFullTermCount(),
				lauPregnancyDetails.getPastPrematureCount(),
				lauPregnancyDetails.getPastMultipleBirthCount(),
				lauPregnancyDetails.getPastMiscarriageCount(),
				lauPregnancyDetails.getPastEctopicCount(),
				lauPregnancyDetails.getPastAbortionCount(),
				lauPregnancyDetails.getFirstDayLastMenses(),
				lauPregnancyDetails.getExpectedDeliveryDate(),
				lauPregnancyDetails.getPregDurationExposure(),
				lauPregnancyDetails.getPregDurationExposureUnits(),
				lauPregnancyDetails.getPregOutcome(),
				lauPregnancyDetails.getPregOutcomeDate(),
				lauPregnancyDetails.getPregOutcomeComments(),
				lauPregnancyDetails.getPregTermAtOutcome(),
				lauPregnancyDetails.getPregTermAtOutcomeUnits(),
				lauPregnancyDetails.getElectiveTermReason(),
				lauPregnancyDetails.getMultipleBirthFlag(),
				lauPregnancyDetails.getMultipleBirthDetials(),
				lauPregnancyDetails.getPregComplications(),
				userId,
				dt,
				lauPregnancyDetails.getPastPregOutcomeComments(),				
				lauPregnancyDetails.getPaternalExposureFlag(),
				lauPregnancyDetails.getPregComplicationsFlag(),
				lauPregnancyDetails.getPartnerName(),
				lauPregnancyDetails.getPartnerInitials(),
				lauPregnancyDetails.getPartnerDob(),
				lauPregnancyDetails.getPartnerContactDetails(),
				lauPregnancyDetails.getGestAgeAtFirstDose(),
				lauPregnancyDetails.getGestAgeAtFirstDoseUnits(),
				lauPregnancyDetails.getGestAgeAtLastDose(),
				lauPregnancyDetails.getGestAgeAtLastDoseUnits(),
				lauPregnancyDetails.getGestAgeAtEvent(),
				lauPregnancyDetails.getGestAgeAtEventUnits(),				
				lauPregnancyDetails.getReportId(),
				lauPregnancyDetails.getPregnancyDetailsId()				
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauPregnancyDetails>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauPregnancyDetails.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/PREGNANCY_DETAILS_ID", "pregnancyDetailsId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_TEST_PERFORMED", "pregTestPerformed" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_TEST_DETAILS", "pregTestDetails" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_EXPOSURE_STATUS", "pregExposureStatus" );
		digester.addBeanPropertySetter( mainXmlTag+"/ELIGIBLE_FOR_PREG_REGISTRY", "eligibleForPregRegistry" );		
		digester.addBeanPropertySetter( mainXmlTag+"/CONSENT_TO_CONTACT_PT", "consentToContactPt" );
		digester.addBeanPropertySetter( mainXmlTag+"/PAST_PREGNANCY_COUNT", "pastPregnancyCount" );
		digester.addBeanPropertySetter( mainXmlTag+"/PAST_PREGNANCY_COMMENTS", "pastPregnancyComments" );
		digester.addBeanPropertySetter( mainXmlTag+"/PAST_LIVE_BIRTH_COUNT", "pastLiveBirthCount" );
		digester.addBeanPropertySetter( mainXmlTag+"/PAST_FULL_TERM_COUNT", "pastFullTermCount" );
		digester.addBeanPropertySetter( mainXmlTag+"/PAST_PREMATURE_COUNT", "pastPrematureCount" );		
		digester.addBeanPropertySetter( mainXmlTag+"/PAST_MULTIPLE_BIRTH_COUNT", "pastMultipleBirthCount" );		
		digester.addBeanPropertySetter( mainXmlTag+"/PAST_MISCARRIAGE_COUNT", "pastMiscarriageCount" );
		digester.addBeanPropertySetter( mainXmlTag+"/PAST_ECTOPIC_COUNT", "pastEctopicCount" );
		digester.addBeanPropertySetter( mainXmlTag+"/PAST_ABORTION_COUNT", "pastAbortionCount" );
		digester.addBeanPropertySetter( mainXmlTag+"/FIRST_DAY_LAST_MENSES", "firstDayLastMenses" );
		digester.addBeanPropertySetter( mainXmlTag+"/EXPECTED_DELIVERY_DATE", "expectedDeliveryDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_DURATION_EXPOSURE", "pregDurationExposure" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_TERM_AT_OUTCOME_UNITS", "pregDurationExposureUnits" );		
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_OUTCOME", "pregOutcome" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_OUTCOME_DATE", "pregOutcomeDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_OUTCOME_COMMENTS", "pregOutcomeComments" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_TERM_AT_OUTCOME", "pregTermAtOutcome" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_TERM_AT_OUTCOME_UNITS", "pregTermAtOutcomeUnits" );	
		digester.addBeanPropertySetter( mainXmlTag+"/ELECTIVE_TERM_REASON", "electiveTermReason" );
		digester.addBeanPropertySetter( mainXmlTag+"/MULTIPLE_BIRTH_FLAG", "multipleBirthFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/MULTIPLE_BIRTH_DETIALS", "multipleBirthDetials" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_COMPLICATIONS", "pregComplications" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/PAST_PREG_OUTCOME_COMMENTS", "pastPregOutcomeComments" );		
		digester.addBeanPropertySetter( mainXmlTag+"/PATERNAL_EXPOSURE_FLAG", "paternalExposureFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/PREG_COMPLICATIONS_FLAG", "pregComplicationsFlag" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_NAME", "partnerName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_INITIALS", "partnerInitials" );
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_DOB", "partnerDob" );	
		digester.addBeanPropertySetter( mainXmlTag+"/PARTNER_CONTACT_DETAILS", "partnerContactDetails" );
		digester.addBeanPropertySetter( mainXmlTag+"/GEST_AGE_AT_FIRST_DOSE", "gestAgeAtFirstDose" );
		digester.addBeanPropertySetter( mainXmlTag+"/GEST_AGE_AT_FIRST_DOSE_UNITS", "gestAgeAtFirstDoseUnits" );
		digester.addBeanPropertySetter( mainXmlTag+"/GEST_AGE_AT_LAST_DOSE", "gestAgeAtLastDose" );
		digester.addBeanPropertySetter( mainXmlTag+"/GEST_AGE_AT_LAST_DOSE_UNITS", "gestAgeAtLastDoseUnits" );
		digester.addBeanPropertySetter( mainXmlTag+"/GEST_AGE_AT_EVENT", "gestAgeAtEvent" );
		digester.addBeanPropertySetter( mainXmlTag+"/GEST_AGE_AT_EVENT_UNITS", "gestAgeAtEventUnits" );
		
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
						
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));		
		
	}
	
	public void addXmlData(LauPregnancyDetails lauPregnancyDetails) {
		reports.add(lauPregnancyDetails);
		log.info(lauPregnancyDetails.toString());
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_PREGNANCY_DETAILS SET PREG_TEST_PERFORMED=?," +
		"PREG_TEST_DETAILS=?,PREG_EXPOSURE_STATUS=?,ELIGIBLE_FOR_PREG_REGISTRY=?,CONSENT_TO_CONTACT_PT=?,PAST_PREGNANCY_COUNT=?," +
		"PAST_PREGNANCY_COMMENTS=?,PAST_LIVE_BIRTH_COUNT=?,PAST_FULL_TERM_COUNT=?,PAST_PREMATURE_COUNT=?,PAST_MULTIPLE_BIRTH_COUNT=?," +
		"PAST_MISCARRIAGE_COUNT=?,PAST_ECTOPIC_COUNT=?,PAST_ABORTION_COUNT=?,FIRST_DAY_LAST_MENSES=?,EXPECTED_DELIVERY_DATE=?," +
		"PREG_DURATION_EXPOSURE=?,PREG_DURATION_EXPOSURE_UNITS=?,PREG_OUTCOME=?,PREG_OUTCOME_DATE=?,PREG_OUTCOME_COMMENTS=?," +
		"PREG_TERM_AT_OUTCOME=?,PREG_TERM_AT_OUTCOME_UNITS=?,ELECTIVE_TERM_REASON=?,MULTIPLE_BIRTH_FLAG=?,MULTIPLE_BIRTH_DETIALS=?," +
		"PREG_COMPLICATIONS=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?,PAST_PREG_OUTCOME_COMMENTS=?," +
		"PATERNAL_EXPOSURE_FLAG=?,PREG_COMPLICATIONS_FLAG=?,PARTNER_NAME=?,PARTNER_INITIALS=?,PARTNER_DOB=?,PARTNER_CONTACT_DETAILS=?," +
		"GEST_AGE_AT_FIRST_DOSE=?,GEST_AGE_AT_FIRST_DOSE_UNITS=?,GEST_AGE_AT_LAST_DOSE=?,GEST_AGE_AT_LAST_DOSE_UNITS=?," +
		"GEST_AGE_AT_EVENT=?,GEST_AGE_AT_EVENT_UNITS=? WHERE REPORT_ID=? AND PREGNANCY_DETAILS_ID=? ";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PREGNANCY_DETAILS (PREG_TEST_PERFORMED,PREG_TEST_DETAILS," +
		"PREG_EXPOSURE_STATUS,ELIGIBLE_FOR_PREG_REGISTRY,CONSENT_TO_CONTACT_PT,PAST_PREGNANCY_COUNT,PAST_PREGNANCY_COMMENTS," +
		"PAST_LIVE_BIRTH_COUNT,PAST_FULL_TERM_COUNT,PAST_PREMATURE_COUNT,PAST_MULTIPLE_BIRTH_COUNT,PAST_MISCARRIAGE_COUNT," +
		"PAST_ECTOPIC_COUNT,PAST_ABORTION_COUNT,FIRST_DAY_LAST_MENSES,EXPECTED_DELIVERY_DATE,PREG_DURATION_EXPOSURE," +
		"PREG_DURATION_EXPOSURE_UNITS,PREG_OUTCOME,PREG_OUTCOME_DATE,PREG_OUTCOME_COMMENTS,PREG_TERM_AT_OUTCOME," +
		"PREG_TERM_AT_OUTCOME_UNITS,ELECTIVE_TERM_REASON,MULTIPLE_BIRTH_FLAG,MULTIPLE_BIRTH_DETIALS,PREG_COMPLICATIONS," +
		"UPDATE_USER_ID,UPDATE_TIMESTAMP,PAST_PREG_OUTCOME_COMMENTS,PATERNAL_EXPOSURE_FLAG,PREG_COMPLICATIONS_FLAG,PARTNER_NAME," +
		"PARTNER_INITIALS,PARTNER_DOB,PARTNER_CONTACT_DETAILS,GEST_AGE_AT_FIRST_DOSE,GEST_AGE_AT_FIRST_DOSE_UNITS,GEST_AGE_AT_LAST_DOSE," +
		"GEST_AGE_AT_LAST_DOSE_UNITS,GEST_AGE_AT_EVENT,GEST_AGE_AT_EVENT_UNITS,REPORT_ID,PREGNANCY_DETAILS_ID) VALUES " +
		"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_PREGNANCY_DETAILS WHERE PREGNANCY_DETAILS_ID = ?",
				new Object[]{this.pregnancyDetails.getPregnancyDetailsId()});
		log.info("LauPregnancyDetailsSetDAO delete() ID -> " + id);		
	}
	
	/**
	 	 
	 	LAU_PREGNANCY_DETAILS
	 
PREGNANCY_DETAILS_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		PREG_TEST_PERFORMED VARCHAR2(50),
		PREG_TEST_DETAILS VARCHAR2(100),
		PREG_EXPOSURE_STATUS VARCHAR2(50),
		ELIGIBLE_FOR_PREG_REGISTRY VARCHAR2(50),
		CONSENT_TO_CONTACT_PT VARCHAR2(50),
		PAST_PREGNANCY_COUNT NUMBER(2 , 0),
		PAST_PREGNANCY_COMMENTS VARCHAR2(4000),
		PAST_PREG_OUTCOME_COMMENTS VARCHAR2(4000),
		PAST_LIVE_BIRTH_COUNT NUMBER(2 , 0),
		PAST_FULL_TERM_COUNT NUMBER(2 , 0),
		PAST_PREMATURE_COUNT NUMBER(2 , 0),
		PAST_MULTIPLE_BIRTH_COUNT NUMBER(2 , 0),
		PAST_MISCARRIAGE_COUNT NUMBER(2 , 0),
		PAST_ECTOPIC_COUNT NUMBER(2 , 0),
		PAST_ABORTION_COUNT NUMBER(2 , 0),
		FIRST_DAY_LAST_MENSES VARCHAR2(8),
		EXPECTED_DELIVERY_DATE VARCHAR2(8),
		PREG_DURATION_EXPOSURE NUMBER(5 , 0),
		PREG_DURATION_EXPOSURE_UNITS VARCHAR2(50),
		PREG_OUTCOME VARCHAR2(50),
		PREG_OUTCOME_DATE VARCHAR2(8),
		PREG_OUTCOME_COMMENTS VARCHAR2(4000),
		PREG_TERM_AT_OUTCOME NUMBER(5 , 0),
		PREG_TERM_AT_OUTCOME_UNITS VARCHAR2(50),
		ELECTIVE_TERM_REASON VARCHAR2(4000),
		MULTIPLE_BIRTH_FLAG VARCHAR2(50),
		MULTIPLE_BIRTH_DETIALS VARCHAR2(4000),
		PREG_COMPLICATIONS_FLAG VARCHAR2(5),
		PREG_COMPLICATIONS VARCHAR2(4000),
		PATERNAL_EXPOSURE_FLAG VARCHAR2(5),
		PARTNER_NAME VARCHAR2(500),
		PARTNER_INITIALS VARCHAR2(100),
		PARTNER_DOB VARCHAR2(8),
		PARTNER_CONTACT_DETAILS VARCHAR2(4000),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP null NOT NULL,
		GEST_AGE_AT_FIRST_DOSE NUMBER(5 , 0),
		GEST_AGE_AT_FIRST_DOSE_UNITS VARCHAR2(50),
		GEST_AGE_AT_LAST_DOSE NUMBER(5 , 0),
		GEST_AGE_AT_LAST_DOSE_UNITS VARCHAR2(50),
		GEST_AGE_AT_EVENT NUMBER(5 , 0),
		GEST_AGE_AT_EVENT_UNITS VARCHAR2(50)
	 
	 */

}
