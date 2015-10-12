package com.nrg.lau.dao;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauSiebelReporterDetails;
import com.nrg.lau.commons.Constants;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelPropertySet;
import com.siebel.data.SiebelService;

public class LauSiebelReporterDetailsSetDAO {

	private static Logger log = Logger
			.getLogger(LauSiebelReporterDetailsSetDAO.class);
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	private  long displayNumber = 1;
	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, SiebelDataBean dataBean,String user,java.sql.Timestamp dstamp) throws Exception {
		userId = user;
		dt = dstamp;
		if (request.getParameter(Constants.SIEBEL_REQ_USER_ID) != null
				&& request.getParameter(Constants.SIEBEL_REQ_ACTIVITY_ID) != null
				&& request.getParameter(Constants.SIEBEL_REQ_CUST_NO) != null
				&& request.getParameter(Constants.SIEBEL_REQ_CON_ID) != null
				&& reportId != 0) {
			
			try {
				getSiebelData(dataBean, template, reportId,  request.getParameter(Constants.SIEBEL_REQ_CON_ID));
			}catch(Exception e) {
				log.info("Error: LauSiebelReporterDetailsSetDAO data not saved due to -> " + e.getMessage());
				log.error(e);
			}
		} else {
			log
					.info("LauSiebelReporterDetailsSetDAO -> Siebel request parameters not available!");
		}
	}

	private LauSiebelReporterDetails setSiebelbean(SiebelPropertySet oOutput)
			throws Exception {
		log.info("1111...");
		LauSiebelReporterDetails reporterDetails = new LauSiebelReporterDetails();

	/*	String add1 = oOutput.getProperty("Street Address BIIB");
		String lname = oOutput.getProperty("Last Name");
		String fname = oOutput.getProperty("First Name");
		String add2 = oOutput.getProperty("Street Address 2 BIIB");
		String city = oOutput.getProperty("City BIIB");
		String state = oOutput.getProperty("State BIIB");
		String zip = oOutput.getProperty("Postal Code BIIB");
		String phone = oOutput.getProperty("PhoneNumber BIIB");
		String fax = oOutput.getProperty("FaxNumber BIIB");
		log.info("details:" + lname + ":" + fname + ":" + add1 + ":"
				+ add2 + ":phone:" + phone + ":" + fax + ":" + zip
				+ ":" + state + ":" + city);*/
		//////
		
		
		
		reporterDetails.setReporterAddress1(oOutput.getProperty("Street Address BIIB"));
		reporterDetails.setReporterTitle(oOutput.getProperty("M/M"));
		reporterDetails.setReporterFirstName(oOutput.getProperty("First Name"));
		reporterDetails.setReporterMiddleName(oOutput.getProperty("Middle Name"));
		reporterDetails.setReporterLastName(oOutput.getProperty("Last Name"));
		reporterDetails.setReporterAddress1(oOutput.getProperty("Street Address BIIB"));
		reporterDetails.setReporterAddress2(oOutput.getProperty("Street Address 2 BIIB"));
		log.info("3333...");
		reporterDetails.setReporterCity(oOutput.getProperty("City BIIB"));
		reporterDetails.setReporterState(oOutput.getProperty("State BIIB"));
		reporterDetails.setReporterPostalCode(oOutput.getProperty("Postal Code BIIB"));
		reporterDetails.setReporterCountry(oOutput.getProperty("Country BIIB"));
		reporterDetails.setReporterPhoneNumber(oOutput.getProperty("PhoneNumber BIIB"));
		reporterDetails.setReporterFaxNumber(oOutput.getProperty("FaxNumber BIIB"));
		reporterDetails
				.setReporterEmail(oOutput.getProperty("Email Address"));
		reporterDetails.setReporterSpecialty(oOutput.getProperty("Primary Specialty"));
		reporterDetails.setContactType("PRESCRIBER");
		log.info("444...");
		return reporterDetails;
	}

	private void getSiebelData(SiebelDataBean dataBean,
			SimpleJdbcTemplate template, long reportId,
			String contactId) throws Exception {
		log.info("Contact Id in report details:" + contactId);
		LauSiebelReporterDetails lauReporterDetails = null;
		SiebelBusObject busObject = dataBean.getBusObject("Contact");
		SiebelPropertySet oInputs = null;
		SiebelPropertySet oOutput = null;

		oInputs = dataBean.newPropertySet();
		oOutput = dataBean.newPropertySet();
		oInputs.setProperty("BusComp", "Party Relationship To");
		oInputs.setProperty("BusObj", "Contact");
		oInputs.setProperty("QueryFields", "Party Id, Relationship Type");
		oInputs
				.setProperty(
						"ValueFields",
						"Related Party Id,Relationship Type,Related Party Display Name,Primary Specialty");

		oInputs.setProperty("Party Id", contactId);
		oInputs.setProperty("Relationship Type", "Prescribing Physician");

		SiebelService obusSrvcs = dataBean
				.getService("Inbound E-mail Database Operations");
		obusSrvcs.invokeMethod("FindRecord", oInputs, oOutput);

		log.info("contact id:" + contactId);
		if (oOutput.getProperty("ErrorCode") == "7143546") {
			// MsgBox "No Physician Exists For This Patient"
			log.info("error in ");
		} else {
			if (oOutput.getProperty("Id") != "") {
				String sRelPartyId = oOutput.getProperty("Related Party Id");
				log.info("party Id:" + sRelPartyId);
				String sName = oOutput
						.getProperty("Related Party Display Name");

				log.info("name:" + sName);
				String speciality = oOutput.getProperty("Primary Specialty");

				/*
				 * Set oBS = Nothing Set oInputs = Nothing Set oOutput = Nothing
				 */
				// Query Contact BC to get the Primary Address
				oInputs.setProperty("BusComp", "Contact");
				oInputs.setProperty("BusObj", "Contact");
				oInputs.setProperty("QueryFields", "Id");
				oInputs
						.setProperty(
								"ValueFields",
								"Last Name,First Name,M/M,Middle Name, Street Address BIIB,Street Address 2 BIIB,City BIIB,State BIIB,Postal Code BIIB,PhoneNumber BIIB," +
								"FaxNumber BIIB,Country BIIB,Primary Specialty,Email Address");
				oInputs.setProperty("Id", sRelPartyId);
				obusSrvcs.invokeMethod("FindRecord", oInputs, oOutput);
				lauReporterDetails  = setSiebelbean(oOutput);
				log.info("getSiebelData() - LauSiebelLauReporterDetails  -> " + lauReporterDetails .toString());
				insert(template, reportId,lauReporterDetails );
				
				
			} else
				log.info("erroor xxxx");
		}

	}

	private final String SQL_INSERT_STRING = "INSERT INTO LAU_CONTACT (CONTACT_TITLE,CONTACT_FIRST_NAME,CONTACT_MIDDLE_NAME,"
			+ "CONTACT_LAST_NAME,CONTACT_ADDRESS1,CONTACT_CITY,CONTACT_STATE,CONTACT_POSTAL_CODE,CONTACT_COUNTRY,CONTACT_PHONE_NUMBER"
			+ ",CONTACT_FAX_NUMBER,CONTACT_EMAIL,CONTACT_SPECIALTY,CONTACT_TYPE,REPORT_ID,CONTACT_ID,UPDATE_TIMESTAMP,UPDATE_USER_ID,DISPLAY_NUMBER) VALUES ("
			+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public void insert(SimpleJdbcTemplate template, long reportId,
			LauSiebelReporterDetails lauReporterDetails)
			throws Exception {

		int id = 0;
		long reporterId = CommonDAO.getPrimaryKey(template);

		LauSiebelReporterDetails reporterDetails = lauReporterDetails;
		reporterDetails.setReporterDetailsId(reporterId);
		reporterDetails.setReportId(reportId);

		log.info("Generated Primary Key for REPORTER_DETAILS_ID ->"
				+ reporterId);

		id = template.update(SQL_INSERT_STRING, new Object[] {
				reporterDetails.getReporterTitle(),
				reporterDetails.getReporterFirstName(),
				reporterDetails.getReporterMiddleName(),
				reporterDetails.getReporterLastName(),
				reporterDetails.getReporterAddress1(),
				reporterDetails.getReporterCity(),
				reporterDetails.getReporterState(),
				reporterDetails.getReporterPostalCode(),
				reporterDetails.getReporterCountry(),
				reporterDetails.getReporterPhoneNumber(),
				reporterDetails.getReporterFaxNumber(),
				reporterDetails.getReporterEmail(),
				reporterDetails.getReporterSpecialty(),
				reporterDetails.getContactType(),
				reporterDetails.getReportId(),
				reporterDetails.getReporterDetailsId(), dt, userId,
				displayNumber++});
		log.info("LauSiebelReporterDetailsSetDAO insert() ID -> " + id);
	}

}