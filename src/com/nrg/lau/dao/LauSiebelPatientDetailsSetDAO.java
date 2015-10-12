package com.nrg.lau.dao;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauSiebelPatientDetails;
import com.nrg.lau.commons.Constants;
import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;

public class LauSiebelPatientDetailsSetDAO {
	
	private static Logger log	= Logger.getLogger(LauSiebelPatientDetailsSetDAO.class);
	private String sMarket = "";
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	public String save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, SiebelDataBean dataBean,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		if(request.getParameter(Constants.SIEBEL_REQ_USER_ID) != null && request.getParameter(Constants.SIEBEL_REQ_ACTIVITY_ID) != null &&
				request.getParameter(Constants.SIEBEL_REQ_CUST_NO) != null && request.getParameter(Constants.SIEBEL_REQ_CON_ID) != null &&
				reportId != 0)	{
			getSiebelData(dataBean,template,reportId,
					request.getParameter(Constants.SIEBEL_REQ_CON_ID));
		}else {
			log.info("LauSiebelPatientDetailsSetDAO -> Siebel request parameters not available!");
		}
		return sMarket;
	}
	
	private LauSiebelPatientDetails setSiebelbean(SiebelBusComp busComp) throws Exception{
		
		LauSiebelPatientDetails siebelPatientDetails = new LauSiebelPatientDetails();
		
		siebelPatientDetails.setMarketBIIB(busComp.getFieldValue("Market BIIB"));
		siebelPatientDetails.setPtTitle(busComp.getFieldValue("M/M"));
		siebelPatientDetails.setPtFirstName(busComp.getFieldValue("First Name"));
		siebelPatientDetails.setPtMiddleName(busComp.getFieldValue("Middle Name"));
		siebelPatientDetails.setPtLastName(busComp.getFieldValue("Last Name"));
		siebelPatientDetails.setPtSex(busComp.getFieldValue("M/F"));
		siebelPatientDetails.setPtAddress1(busComp.getFieldValue("Street Address BIIB"));
		siebelPatientDetails.setPtAddress2(busComp.getFieldValue("Street Address 2 BIIB"));
		siebelPatientDetails.setPtCity(busComp.getFieldValue("City BIIB"));
		siebelPatientDetails.setPtState(busComp.getFieldValue("State BIIB"));
		siebelPatientDetails.setPtPostalCode(busComp.getFieldValue("Postal Code BIIB"));
		siebelPatientDetails.setPtCountry(busComp.getFieldValue("Country BIIB"));
		siebelPatientDetails.setPtPhoneNumber(busComp.getFieldValue("PhoneNumber BIIB"));
		siebelPatientDetails.setPtEmail(busComp.getFieldValue("Email Address"));
		siebelPatientDetails.setPtDOB(busComp.getFieldValue("Birth Date"));
		siebelPatientDetails.setPtIdentifier1(busComp.getFieldValue("Touch Id SVF BIIB"));
		siebelPatientDetails.setPtIndicator1(busComp.getFieldValue("Tygris Status BIIB"));
		siebelPatientDetails.setPtIndicator2(busComp.getFieldValue("Tygris Status BIIB"));
		siebelPatientDetails.setPtStudyEnrollmentStatus(busComp.getFieldValue("Tygris Status BIIB"));
		siebelPatientDetails.setPtStudyEnrollmentDate(busComp.getFieldValue("Tygris Status Date BIIB"));
		siebelPatientDetails.setPtStudyWithdrawnDate(busComp.getFieldValue("Tygris Status Date BIIB"));	
		this.sMarket = busComp.getFieldValue("Market BIIB");	
		return siebelPatientDetails;
	}
	
	private void getSiebelData(SiebelDataBean dataBean, SimpleJdbcTemplate template, 
			long reportId,  String contactId) throws Exception{
		
		LauSiebelPatientDetails patientDetails = null;
		SiebelBusObject busObject	= dataBean.getBusObject("Contact");
		SiebelBusComp busComp 		= busObject.getBusComp("Contact"); 
		busComp.setViewMode(3);
		busComp.clearToQuery();
		busComp.activateField("Id");
		busComp.activateField("M/M");
		busComp.activateField("First Name");
		busComp.activateField("Middle Name");
		busComp.activateField("Last Name");
		busComp.activateField("M/F");
		busComp.activateField("Street Address BIIB");
		busComp.activateField("Street Address 2 BIIB");
		busComp.activateField("City BIIB");
		busComp.activateField("State BIIB");
		busComp.activateField("Postal Code BIIB");
		busComp.activateField("Country BIIB");
		busComp.activateField("PhoneNumber BIIB");
		busComp.activateField("Email Address");
		busComp.activateField("Birth Date");
		busComp.activateField("Touch Id SVF BIIB");
		busComp.activateField("Tygris Status BIIB");
		busComp.activateField("Tygris Status Date BIIB");
		busComp.activateField("Market BIIB");	
		//busComp.setSearchSpec("ContactId from the URL", contactId);
		busComp.setSearchSpec("Id", contactId);
		busComp.setSortSpec("Created(DESCENDING)");
		busComp.executeQuery(true);
		if (busComp.firstRecord()) {
			patientDetails = setSiebelbean(busComp);
			log.info("getSiebelData() - LauSiebelPatientDetails -> " + patientDetails.toString());
			insert(template, reportId,patientDetails);
			while (busComp.nextRecord()){
				patientDetails = setSiebelbean(busComp);
				log.info("getSiebelData() - LauSiebelPatientDetails -> " + patientDetails.toString());
				insert(template, reportId,patientDetails);
			}
		}
	}
	
	public void insert(SimpleJdbcTemplate template, long reportId, 
			LauSiebelPatientDetails patientDetails) throws Exception {
		
		int id = 0;
		long patientDetailsId = CommonDAO.getPrimaryKey(template);
		
		LauSiebelPatientDetails lauSiebelPatientDetails = patientDetails;
		lauSiebelPatientDetails.setPatientDetailsId(patientDetailsId);
		lauSiebelPatientDetails.setReportId(reportId);
		
		log.info("Generated Primary Key for PATIENT_DETAILS_ID ->" + patientDetailsId);
		id = template.update(SQL_INSERT_STRING,getParameters(lauSiebelPatientDetails));
		log.info("LauSiebelPatientDetailsSetDAO insert() ID -> " + id);	
	}
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_PATIENT_DETAILS (PT_TITLE,PT_FIRST_NAME,PT_MIDDLE_NAME,PT_LAST_NAME,PT_SEX," +
			"PT_ADDRESS1,PT_ADDRESS2,PT_CITY,PT_STATE,PT_POSTAL_CODE,PT_COUNTRY,PT_PHONE_NUMBER,PT_EMAIL,PT_DOB,PATIENT_IDENTIFIER2,PATIENT_INDICATOR1," +
			"PATIENT_INDICATOR2,STUDY_ENROLLMENT_STATUS,STUDY_ENROLLMENT_DATE,STUDY_WITHDRAWN_DATE,UPDATE_TIMESTAMP,UPDATE_USER_ID,REPORT_ID,PATIENT_DETAILS_ID)" +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private Object[] getParameters(LauSiebelPatientDetails siebelPatientDetails) {
		return new Object[]{
			siebelPatientDetails.getPtTitle(),
			siebelPatientDetails.getPtFirstName(),
			siebelPatientDetails.getPtMiddleName(),
			siebelPatientDetails.getPtLastName(),
			siebelPatientDetails.getPtSex(),
			siebelPatientDetails.getPtAddress1(),
			siebelPatientDetails.getPtAddress2(),
			siebelPatientDetails.getPtCity(),
			siebelPatientDetails.getPtState(),
			siebelPatientDetails.getPtPostalCode(),
			siebelPatientDetails.getPtCountry(),
			siebelPatientDetails.getPtPhoneNumber(),
			siebelPatientDetails.getPtEmail(),
			siebelPatientDetails.getPtDOB(),
			siebelPatientDetails.getPtIdentifier1(),
			siebelPatientDetails.getPtIndicator1(),
			siebelPatientDetails.getPtIndicator2(),
			siebelPatientDetails.getPtStudyEnrollmentStatus(),
			siebelPatientDetails.getPtStudyEnrollmentDate(),
			siebelPatientDetails.getPtStudyWithdrawnDate(),
			dt,
			userId,
			siebelPatientDetails.getReportId(),
			siebelPatientDetails.getPatientDetailsId()			
		};
	}
}
