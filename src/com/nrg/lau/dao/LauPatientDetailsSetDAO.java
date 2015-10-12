package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauPatientDetails;
import com.nrg.lau.commons.IReportPatientDAO;
import com.nrg.lau.commons.IReportsConstants;
import com.sun.java.util.collections.Set;

public class LauPatientDetailsSetDAO implements IReportPatientDAO<LauPatientDetails>{
	
	private static Logger log	= Logger.getLogger(LauPatientDetailsSetDAO.class);
	private Vector<LauPatientDetails> reports		= null;
	private LauPatientDetails patientDetails 		= null;	
	private HashMap<String, String> patientMap 	= null;
	private HashMap<Long, List<Long>> patientDeleteMap 	= null;
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	
	public HashMap<String, String> save(HttpServletRequest request, SimpleJdbcTemplate template,	
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception {		
		userId = user;
		dt = dstamp;
		HashMap<String, String> newPatientMap = new HashMap<String, String>();
		patientMap = new HashMap<String, String>();

		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_PATIENT_DETAILS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_PATIENT_DETAILS_PARAM_NAME).length() > 0) {
			
			log.info("LauPatientDetailsDAO save() LAU_PATIENT_DETAILS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_PATIENT_DETAILS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_PATIENT_DETAILS_PARAM_NAME));
			Iterator<LauPatientDetails> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT			
			while(itr.hasNext())	{					
				LauPatientDetails lauPatientDetails = (LauPatientDetails)itr.next();				
				this.patientDetails	= null;
				this.patientDetails	= lauPatientDetails;
				if(lauPatientDetails.getPatientDetailsId() == 0	)	{					
					patientMap = insert(template,reportId);
				}else {
					if(lauPatientDetails.getTransactionType() == IReportsConstants.deleteFlag)	{
						//delete(template);
					}else	update(template);
				}
				newPatientMap.putAll(patientMap);
			}//end of while(itr.hasNext())
			/*for (Map.Entry entry : newPatientMap.entrySet()) {
				log.info("key,val newPatientMap----> " + entry.getKey()	+ "," + entry.getValue());
			}*/
			
		}	else	{
			log.info(IReportsConstants.LAU_PATIENT_DETAILS_PARAM_NAME + " not found in request");
		}	
		
		//return patientMap;
		return newPatientMap;
	}
		
	public HashMap<Long, List<Long>> saveDelete(HttpServletRequest request, SimpleJdbcTemplate template,	
			long reportId,String user,java.sql.Timestamp dstamp) throws Exception 
			{		
		userId = user;
		dt = dstamp;
		patientDeleteMap = new HashMap<Long, List<Long>>();

		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_PATIENT_DETAILS_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_PATIENT_DETAILS_PARAM_NAME).length() > 0) 
		{
			
			log.info("LauPatientDetailsDAO saveDelete() LAU_PATIENT_DETAILS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_PATIENT_DETAILS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_PATIENT_DETAILS_PARAM_NAME));
			Iterator<LauPatientDetails> itr = this.reports.iterator();
			
			while(itr.hasNext())
			{				
				LauPatientDetails lauPatientDetails = (LauPatientDetails)itr.next();				
				this.patientDetails	= null;
				this.patientDetails	= lauPatientDetails;
				
				if(lauPatientDetails.getTransactionType() == IReportsConstants.deleteFlag)	{
					log.info("Patient delete section");
					List<Long> list = new ArrayList<Long>();
					list.add(lauPatientDetails.getTransactionType());
					list.add(lauPatientDetails.getPatientDetailsId());
					list.add(lauPatientDetails.getReportId());
					//productMap.put(lauProducts.getProductName()+lauProducts.getProductType()+lauProducts.getDisplayNumber(), list);
					patientDeleteMap.put(lauPatientDetails.getPatientDetailsId(), list);
					log.info("List: "+list);
				}
			}
		}
		else
		{
			log.info(IReportsConstants.LAU_PATIENT_DETAILS_PARAM_NAME + " not found in request");
		}	
		log.info("before returning, Map Size: "+patientDeleteMap.size());
		return patientDeleteMap;
	}	
	
	public HashMap<String, String> insert(SimpleJdbcTemplate template,long reportId) throws Exception {
		log.info("calling insert------>");
		int id = 0;
		String patientId = "";
		patientMap = new HashMap<String, String>();
		long patientDetailsId = CommonDAO.getPrimaryKey(template);
		this.patientDetails.setPatientDetailsId(patientDetailsId);		
		patientId = Long.toString(this.patientDetails.getPatientDetailsId());			
		patientMap.put(this.patientDetails.getINTERNAL_LINK_ID(), patientId);		
		if(this.patientDetails.getReportId() == 0)	{
			this.patientDetails.setReportId(reportId);	}		
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template));		
		return patientMap;
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauPatientDetailsDAO update() ID -> " + id);			
		
	}
	
	private Object[] getParameters()
	{
		LauPatientDetails lauPatientDetails = this.patientDetails;
		return new Object[]{
			lauPatientDetails.getDISPLAY_NUMBER(),
			lauPatientDetails.getPtSex(),
			lauPatientDetails.getPtDob(),
			lauPatientDetails.getPtAge(),
			lauPatientDetails.getPtAgeUnits(),
			lauPatientDetails.getPtAgeGroup(),
			lauPatientDetails.getPtTitle(),
			lauPatientDetails.getPtFirstName(),
			lauPatientDetails.getPtMiddleName(),
			lauPatientDetails.getPtLastName(),
			lauPatientDetails.getPtInitials(),
			lauPatientDetails.getPtAddress1(),
			lauPatientDetails.getPtAddress2(),
			lauPatientDetails.getPtAddress3(),
			lauPatientDetails.getPtCity(),
			lauPatientDetails.getPtState(),
			lauPatientDetails.getPtPostalCode(),
			lauPatientDetails.getPtCountry(),
			lauPatientDetails.getPtPhoneNumber(),
			lauPatientDetails.getPtFaxNumber(),
			lauPatientDetails.getPtEmail(),
			lauPatientDetails.getDeathDate(),
			lauPatientDetails.getCauseOfDeath(),
			lauPatientDetails.getAutopsyPerformed(),
			lauPatientDetails.getStudyId(),
			lauPatientDetails.getSTUDY_CENTER_ID(),
			lauPatientDetails.getStudyPatientId(),
			lauPatientDetails.getStudyEnrollmentStatus(),
			lauPatientDetails.getStudyEnrollmentDate(),
			lauPatientDetails.getStudyWithdrawnDate(),
			lauPatientDetails.getPatientIdentifier1(),
			lauPatientDetails.getPatientIdentifier2(),
			lauPatientDetails.getPatientIdentifier3(),
			lauPatientDetails.getPatientIndicator1(),
			lauPatientDetails.getPatientIndicator2(),
			lauPatientDetails.getPatientIndicator3(),
			lauPatientDetails.getConsentToContactHcp(),
			lauPatientDetails.getConsentToUsePersonalDtls(),
			userId,
			dt,
			lauPatientDetails.getPregnancyStatus(),
			lauPatientDetails.getHcpContactDetailsAvailable(),
			lauPatientDetails.getPT_WEIGHT(),
			lauPatientDetails.getPT_WEIGHT_UNITS(),
			lauPatientDetails.getPATIENT_DIED_FLAG(),
			lauPatientDetails.getLIFE_THREATENING_FLAG(),
			lauPatientDetails.getHOSPITALIZATION_FLAG(),
			lauPatientDetails.getHOSPITALIZATION_NATURE(),
			lauPatientDetails.getHOSPITALIZED_FROM_DATE(),
			lauPatientDetails.getHOSPITALIZED_TO_DATE(),
			lauPatientDetails.getREQUIRED_INTERVENTION(),
			lauPatientDetails.getDISABILITY_FLAG(),
			lauPatientDetails.getCONGENITAL_ANOMALY_FLAG(),
			lauPatientDetails.getMEDICALLY_IMPORTANT_EVENT(),
			lauPatientDetails.getOTHER_SERIOUSNESS_DESC(),	
			lauPatientDetails.getCUSTOM_COMMENT_01(),
			lauPatientDetails.getCUSTOM_COMMENT_02(),
			lauPatientDetails.getReportId(),
			lauPatientDetails.getPatientDetailsId()
			
		};
	}
	
	private Object[] getInsertParameters(SimpleJdbcTemplate template)
	{
		LauPatientDetails lauPatientDetails = this.patientDetails;
		return new Object[]{
			CommonDAO.getNextDisplayNumber(template, lauPatientDetails.getReportId(), "LAU_PATIENT_DETAILS",0),
			lauPatientDetails.getPtSex(),
			lauPatientDetails.getPtDob(),
			lauPatientDetails.getPtAge(),
			lauPatientDetails.getPtAgeUnits(),
			lauPatientDetails.getPtAgeGroup(),
			lauPatientDetails.getPtTitle(),
			lauPatientDetails.getPtFirstName(),
			lauPatientDetails.getPtMiddleName(),
			lauPatientDetails.getPtLastName(),
			lauPatientDetails.getPtInitials(),
			lauPatientDetails.getPtAddress1(),
			lauPatientDetails.getPtAddress2(),
			lauPatientDetails.getPtAddress3(),
			lauPatientDetails.getPtCity(),
			lauPatientDetails.getPtState(),
			lauPatientDetails.getPtPostalCode(),
			lauPatientDetails.getPtCountry(),
			lauPatientDetails.getPtPhoneNumber(),
			lauPatientDetails.getPtFaxNumber(),
			lauPatientDetails.getPtEmail(),
			lauPatientDetails.getDeathDate(),
			lauPatientDetails.getCauseOfDeath(),
			lauPatientDetails.getAutopsyPerformed(),
			lauPatientDetails.getStudyId(),
			lauPatientDetails.getSTUDY_CENTER_ID(),
			lauPatientDetails.getStudyPatientId(),
			lauPatientDetails.getStudyEnrollmentStatus(),
			lauPatientDetails.getStudyEnrollmentDate(),
			lauPatientDetails.getStudyWithdrawnDate(),
			lauPatientDetails.getPatientIdentifier1(),
			lauPatientDetails.getPatientIdentifier2(),
			lauPatientDetails.getPatientIdentifier3(),
			lauPatientDetails.getPatientIndicator1(),
			lauPatientDetails.getPatientIndicator2(),
			lauPatientDetails.getPatientIndicator3(),
			lauPatientDetails.getConsentToContactHcp(),
			lauPatientDetails.getConsentToUsePersonalDtls(),
			userId,
			dt,
			lauPatientDetails.getPregnancyStatus(),
			lauPatientDetails.getHcpContactDetailsAvailable(),
			lauPatientDetails.getPT_WEIGHT(),
			lauPatientDetails.getPT_WEIGHT_UNITS(),
			lauPatientDetails.getPATIENT_DIED_FLAG(),
			lauPatientDetails.getLIFE_THREATENING_FLAG(),
			lauPatientDetails.getHOSPITALIZATION_FLAG(),
			lauPatientDetails.getHOSPITALIZATION_NATURE(),
			lauPatientDetails.getHOSPITALIZED_FROM_DATE(),
			lauPatientDetails.getHOSPITALIZED_TO_DATE(),
			lauPatientDetails.getREQUIRED_INTERVENTION(),
			lauPatientDetails.getDISABILITY_FLAG(),
			lauPatientDetails.getCONGENITAL_ANOMALY_FLAG(),
			lauPatientDetails.getMEDICALLY_IMPORTANT_EVENT(),
			lauPatientDetails.getOTHER_SERIOUSNESS_DESC(),	
			lauPatientDetails.getCUSTOM_COMMENT_01(),
			lauPatientDetails.getCUSTOM_COMMENT_02(),
			lauPatientDetails.getReportId(),
			lauPatientDetails.getPatientDetailsId()
			
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauPatientDetails>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauPatientDetails.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "DISPLAY_NUMBER" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_DETAILS_ID", "patientDetailsId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_SEX", "ptSex" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_DOB", "ptDob" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_AGE", "ptAge" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_AGE_UNITS", "ptAgeUnits" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_AGE_GROUP", "ptAgeGroup" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_TITLE", "ptTitle" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_FIRST_NAME", "ptFirstName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_MIDDLE_NAME", "ptMiddleName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_LAST_NAME", "ptLastName" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_INITIALS", "ptInitials" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_ADDRESS1", "ptAddress1" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_ADDRESS2", "ptAddress2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_ADDRESS3", "ptAddress3" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_CITY", "ptCity" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_STATE", "ptState" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_POSTAL_CODE", "ptPostalCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_COUNTRY", "ptCountry" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_PHONE_NUMBER", "ptPhoneNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_FAX_NUMBER", "ptFaxNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_EMAIL", "ptEmail" );
		digester.addBeanPropertySetter( mainXmlTag+"/DEATH_DATE", "deathDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/CAUSE_OF_DEATH", "causeOfDeath" );
		digester.addBeanPropertySetter( mainXmlTag+"/AUTOPSY_PERFORMED", "autopsyPerformed" );
		digester.addBeanPropertySetter( mainXmlTag+"/STUDY_ID", "studyId" );
		digester.addBeanPropertySetter( mainXmlTag+"/STUDY_CENTER_ID", "STUDY_CENTER_ID" );
		
		digester.addBeanPropertySetter( mainXmlTag+"/STUDY_PATIENT_ID", "studyPatientId" );
		digester.addBeanPropertySetter( mainXmlTag+"/STUDY_ENROLLMENT_STATUS", "studyEnrollmentStatus" );
		digester.addBeanPropertySetter( mainXmlTag+"/STUDY_ENROLLMENT_DATE", "studyEnrollmentDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/STUDY_WITHDRAWN_DATE", "studyWithdrawnDate" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_IDENTIFIER1", "patientIdentifier1" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_IDENTIFIER2", "patientIdentifier2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_IDENTIFIER3", "patientIdentifier3" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_INDICATOR1", "patientIndicator1" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_INDICATOR2", "patientIndicator2" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_INDICATOR3", "patientIndicator3" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONSENT_TO_CONTACT_HCP", "consentToContactHcp" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONSENT_TO_USE_PERSONAL_DTLS", "consentToUsePersonalDtls" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_PREGNANCY_STATUS", "pregnancyStatus" );
		digester.addBeanPropertySetter( mainXmlTag+"/HCP_CONTACT_DETAILS_AVAILABLE", "hcpContactDetailsAvailable" );
		
		digester.addBeanPropertySetter( mainXmlTag+"/PT_WEIGHT", "PT_WEIGHT" );
		digester.addBeanPropertySetter( mainXmlTag+"/PT_WEIGHT_UNITS", "PT_WEIGHT_UNITS" );
		digester.addBeanPropertySetter( mainXmlTag+"/PATIENT_DIED_FLAG", "PATIENT_DIED_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/LIFE_THREATENING_FLAG", "LIFE_THREATENING_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/HOSPITALIZATION_FLAG", "HOSPITALIZATION_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/HOSPITALIZATION_NATURE", "HOSPITALIZATION_NATURE" );
		digester.addBeanPropertySetter( mainXmlTag+"/HOSPITALIZED_FROM_DATE", "HOSPITALIZED_FROM_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/HOSPITALIZED_TO_DATE", "HOSPITALIZED_TO_DATE" );
		digester.addBeanPropertySetter( mainXmlTag+"/REQUIRED_INTERVENTION", "REQUIRED_INTERVENTION" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISABILITY_FLAG", "DISABILITY_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONGENITAL_ANOMALY_FLAG", "CONGENITAL_ANOMALY_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/MEDICALLY_IMPORTANT_EVENT", "MEDICALLY_IMPORTANT_EVENT" );
		digester.addBeanPropertySetter( mainXmlTag+"/OTHER_SERIOUSNESS_DESC", "OTHER_SERIOUSNESS_DESC" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_COMMENT_01", "CUSTOM_COMMENT_01");
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_COMMENT_02", "CUSTOM_COMMENT_02");
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
		
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
		
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauPatientDetails lauPatientDetails) {
		reports.add(lauPatientDetails);
		log.info(lauPatientDetails.toString());
	}	
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_PATIENT_DETAILS SET DISPLAY_NUMBER=?,PT_SEX=?,PT_DOB=?,PT_AGE=?,PT_AGE_UNITS=?," +
		"PT_AGE_GROUP=?,PT_TITLE=?,PT_FIRST_NAME=?,PT_MIDDLE_NAME=?,PT_LAST_NAME=?,PT_INITIALS=?,PT_ADDRESS1=?,PT_ADDRESS2=?," +
		"PT_ADDRESS3=?,PT_CITY=?,PT_STATE=?,PT_POSTAL_CODE=?,PT_COUNTRY=?,PT_PHONE_NUMBER=?,PT_FAX_NUMBER=?,PT_EMAIL=?," +
		"DEATH_DATE=?,CAUSE_OF_DEATH=?,AUTOPSY_PERFORMED=?,STUDY_ID=?,STUDY_CENTER_ID=?,STUDY_PATIENT_ID=?,STUDY_ENROLLMENT_STATUS=?,STUDY_ENROLLMENT_DATE=?," +
		"STUDY_WITHDRAWN_DATE=?,PATIENT_IDENTIFIER1=?,PATIENT_IDENTIFIER2=?,PATIENT_IDENTIFIER3=?,PATIENT_INDICATOR1=?,PATIENT_INDICATOR2=?," +
		"PATIENT_INDICATOR3=?,CONSENT_TO_CONTACT_HCP=?,CONSENT_TO_USE_PERSONAL_DTLS=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?,PT_PREGNANCY_STATUS=?,HCP_CONTACT_DETAILS_AVAILABLE=?," +
		"PT_WEIGHT=?,PT_WEIGHT_UNITS=?,PATIENT_DIED_FLAG=?,LIFE_THREATENING_FLAG=?,HOSPITALIZATION_FLAG=?,HOSPITALIZATION_NATURE=?,HOSPITALIZED_FROM_DATE=?,HOSPITALIZED_TO_DATE=?,"+
		"REQUIRED_INTERVENTION=?,DISABILITY_FLAG=?,CONGENITAL_ANOMALY_FLAG=?,MEDICALLY_IMPORTANT_EVENT=?,OTHER_SERIOUSNESS_DESC=?,CUSTOM_COMMENT_01=?,CUSTOM_COMMENT_02=? WHERE " +
		"REPORT_ID=? AND PATIENT_DETAILS_ID=?";
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PATIENT_DETAILS (DISPLAY_NUMBER,PT_SEX,PT_DOB,PT_AGE,PT_AGE_UNITS," +
		"PT_AGE_GROUP,PT_TITLE,PT_FIRST_NAME,PT_MIDDLE_NAME,PT_LAST_NAME,PT_INITIALS,PT_ADDRESS1,PT_ADDRESS2," +
		"PT_ADDRESS3,PT_CITY,PT_STATE,PT_POSTAL_CODE,PT_COUNTRY,PT_PHONE_NUMBER,PT_FAX_NUMBER,PT_EMAIL," +
		"DEATH_DATE,CAUSE_OF_DEATH,AUTOPSY_PERFORMED,STUDY_ID,STUDY_CENTER_ID,STUDY_PATIENT_ID,STUDY_ENROLLMENT_STATUS,STUDY_ENROLLMENT_DATE," +
		"STUDY_WITHDRAWN_DATE,PATIENT_IDENTIFIER1,PATIENT_IDENTIFIER2,PATIENT_IDENTIFIER3,PATIENT_INDICATOR1,PATIENT_INDICATOR2," +
		"PATIENT_INDICATOR3,CONSENT_TO_CONTACT_HCP,CONSENT_TO_USE_PERSONAL_DTLS,UPDATE_USER_ID,UPDATE_TIMESTAMP,PT_PREGNANCY_STATUS,HCP_CONTACT_DETAILS_AVAILABLE," +
		"PT_WEIGHT,PT_WEIGHT_UNITS,PATIENT_DIED_FLAG,LIFE_THREATENING_FLAG,HOSPITALIZATION_FLAG,HOSPITALIZATION_NATURE,HOSPITALIZED_FROM_DATE,HOSPITALIZED_TO_DATE," +
		"REQUIRED_INTERVENTION,DISABILITY_FLAG,CONGENITAL_ANOMALY_FLAG,MEDICALLY_IMPORTANT_EVENT,OTHER_SERIOUSNESS_DESC,CUSTOM_COMMENT_01,CUSTOM_COMMENT_02,REPORT_ID,PATIENT_DETAILS_ID) VALUES" +
		"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_PATIENT_DETAILS WHERE PATIENT_DETAILS_ID = ?",
				new Object[]{this.patientDetails.getPatientDetailsId()});
		log.info("LauPatientDetailsDAO delete() ID -> " + id);	
		
	}
	
	
	/**		
		LAU_PATIENT_DETAILS
		
		PATIENT_DETAILS_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		PT_SEX VARCHAR2(50),
		PT_PREGNANCY_STATUS VARCHAR2(50),
		PT_DOB VARCHAR2(8),
		PT_AGE NUMBER(5 , 2),
		PT_AGE_UNITS VARCHAR2(50),
		PT_AGE_GROUP VARCHAR2(50),
		PT_TITLE VARCHAR2(300),
		PT_FIRST_NAME VARCHAR2(300),
		PT_MIDDLE_NAME VARCHAR2(300),
		PT_LAST_NAME VARCHAR2(300),
		PT_INITIALS VARCHAR2(100),
		PT_ADDRESS1 VARCHAR2(100),
		PT_ADDRESS2 VARCHAR2(100),
		PT_ADDRESS3 VARCHAR2(100),
		PT_CITY VARCHAR2(100),
		PT_STATE VARCHAR2(50),
		PT_POSTAL_CODE VARCHAR2(100),
		PT_COUNTRY VARCHAR2(50),
		PT_PHONE_NUMBER VARCHAR2(100),
		PT_FAX_NUMBER VARCHAR2(100),
		PT_EMAIL VARCHAR2(100),
		DEATH_DATE VARCHAR2(8),
		CAUSE_OF_DEATH VARCHAR2(500),
		AUTOPSY_PERFORMED VARCHAR2(50),
		STUDY_ID VARCHAR2(100),
		STUDY_PATIENT_ID VARCHAR2(100),
		STUDY_ENROLLMENT_STATUS VARCHAR2(50),
		STUDY_ENROLLMENT_DATE VARCHAR2(8),
		STUDY_WITHDRAWN_DATE VARCHAR2(8),
		PATIENT_IDENTIFIER1 VARCHAR2(100),
		PATIENT_IDENTIFIER2 VARCHAR2(100),
		PATIENT_IDENTIFIER3 VARCHAR2(100),
		PATIENT_IDENTIFIER4 VARCHAR2(100),
		PATIENT_INDICATOR1 VARCHAR2(50),
		PATIENT_INDICATOR2 VARCHAR2(50),
		PATIENT_INDICATOR3 VARCHAR2(50),
		PATIENT_INDICATOR4 VARCHAR2(50),
		CONSENT_TO_CONTACT_HCP VARCHAR2(5),
		HCP_CONTACT_DETAILS_AVAILABLE VARCHAR2(5),
		PT_WEIGHT,
		PT_WEIGHT_UNITS,
		PATIENT_DIED_FLAG,
		LIFE_THREATENING_FLAG,
		HOSPITALIZATION_NATURE,
		HOSPITALIZED_FROM_DATE,
		HOSPITALIZED_TO_DATE,
		REQUIRED_INTERVENTION,
		DISABILITY_FLAG,
		CONGENITAL_ANOMALY_FLAG,
		MEDICALLY_IMPORTANT_EVENT,
		OTHER_SERIOUSNESS_DESC,
		CONSENT_TO_USE_PERSONAL_DTLS VARCHAR2(5),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP null NOT NULL
	**/
}