package com.nrg.lau.dao;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauReporterDetails;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public class LauReporterDetailsSetDAO implements IReportChildSetDAO<LauReporterDetails> {

	private static Logger log	= Logger.getLogger(LauReporterDetailsSetDAO.class);
	private Vector<LauReporterDetails> reports	= null;
	private LauReporterDetails reporterDetails 	= null;	
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
		if(request.getParameter(IReportsConstants.LAU_REPORTER_DETAILS_PARAM_NAME) != null &&
				request.getParameter(IReportsConstants.LAU_REPORTER_DETAILS_PARAM_NAME).length() > 0) {
			
			log.info("LauReporterDetailsDAO save() LAU_RELATED_REPORTS_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_REPORTER_DETAILS_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_REPORTER_DETAILS_PARAM_NAME));
			Iterator<LauReporterDetails> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauReporterDetails lauReporterDetails = (LauReporterDetails)itr.next();
				log.info("itr.hasNext() -> " + lauReporterDetails.toString());
				this.reporterDetails	= null;
				this.reporterDetails	= lauReporterDetails;
				if(lauReporterDetails.getReporterDetailsId() == 0)	{
						insert(template,reportId);
				}else  {
					if(lauReporterDetails.getTransactionType() == IReportsConstants.deleteFlag){
						delete(template);
					}else	update(template);				
				}				
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_REPORTER_DETAILS_PARAM_NAME + " not found in request");
		}
		
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long reporterDetailsId = CommonDAO.getPrimaryKey(template);
		this.reporterDetails.setReporterDetailsId(reporterDetailsId);
		if(this.reporterDetails.getReportId() == 0)	{
			this.reporterDetails.setReportId(reportId);	}
		log.info("Generated Primary Key for REPORTER_DETAILS_ID ->" + reporterDetailsId);
		id = template.update(SQL_INSERT_STRING,getParameters());
		log.info("LauReporterDetailsDAO insert() ID -> " + id);
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauReporterDetailsDAO update() ID -> " + id);
		
	}
	
	private Object[] getParameters()
	{
		LauReporterDetails lauReporterDetails = this.reporterDetails; 
		return new Object[]{
			lauReporterDetails.getReporterSourceType(),
			lauReporterDetails.getReporterType(),
			lauReporterDetails.getReporterIsPatient(),
			lauReporterDetails.getReporterContactDate(),
			lauReporterDetails.getReporterContactMethod(),
			lauReporterDetails.getReporterContactReason(),
			lauReporterDetails.getRelationshipToPatient(),
			lauReporterDetails.getReporterIsPrescriber(),
			lauReporterDetails.getReporterTitle(),
			lauReporterDetails.getReporterFirstName(),
			lauReporterDetails.getReporterMiddleName(),
			lauReporterDetails.getReporterLastName(),
			lauReporterDetails.getReporterAddress1(),
			lauReporterDetails.getReporterAddress2(),
			lauReporterDetails.getReporterAddress3(),
			lauReporterDetails.getReporterCity(),
			lauReporterDetails.getReporterState(),
			lauReporterDetails.getReporterPostalCode(),
			lauReporterDetails.getReporterCountry(),
			lauReporterDetails.getReporterPhoneNumber(),
			lauReporterDetails.getReporterFaxNumber(),
			lauReporterDetails.getReporterEmail(),
			lauReporterDetails.getReporterSpecialty(),
			lauReporterDetails.getReporterOccupation(),
			userId,
			dt,
			lauReporterDetails.getReporterTreatingMd(),
			lauReporterDetails.getReportId(),
			lauReporterDetails.getReporterDetailsId()
		};
	}
	
	
	public void createBeansFromXml(String xml) throws Exception {
		String mainXmlTag = "ROWSET/ROW";
		reports				= new Vector<LauReporterDetails>();
		Digester digester	= new Digester();
		digester.push(this);
		digester.addObjectCreate( mainXmlTag, LauReporterDetails.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_DETAILS_ID", "reporterDetailsId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_SOURCE_TYPE", "reporterSourceType" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_TYPE", "reporterType" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_IS_PATIENT", "reporterIsPatient" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_CONTACT_DATE", "reporterContactDate" );		
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_CONTACT_METHOD", "reporterContactMethod" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_CONTACT_REASON", "reporterContactReason" );
		digester.addBeanPropertySetter( mainXmlTag+"/RELATIONSHIP_TO_PATIENT", "relationshipToPatient" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_IS_PRESCRIBER", "reporterIsPrescriber" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_TITLE", "reporterTitle" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_FIRST_NAME", "reporterFirstName" );		
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_MIDDLE_NAME", "reporterMiddleName" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_LAST_NAME", "reporterLastName" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_ADDRESS1", "reporterAddress1" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_ADDRESS2", "reporterAddress2" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_ADDRESS3", "reporterAddress3" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_CITY", "reporterCity" );		
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_STATE", "reporterState" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_POSTAL_CODE", "reporterPostalCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_COUNTRY", "reporterCountry" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_PHONE_NUMBER", "reporterPhoneNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_FAX_NUMBER", "reporterFaxNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_EMAIL", "reporterEmail" );		
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_SPECIALTY", "reporterSpecialty" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_OCCUPATION", "reporterOccupation" );

		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_IS_TREATING_MD", "reporterTreatingMd" );
		
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
								
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );		
		digester.parse(new StringReader(xml));		
		
	}
	
	public void addXmlData(LauReporterDetails lauReporterDetails) {
		reports.add(lauReporterDetails);
		log.info(lauReporterDetails.toString());
	}
	
	//SQL Statements
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_REPORTER_DETAILS (REPORTER_SOURCE_TYPE,REPORTER_TYPE,REPORTER_IS_PATIENT," +
		"REPORTER_CONTACT_DATE,REPORTER_CONTACT_METHOD,REPORTER_CONTACT_REASON,RELATIONSHIP_TO_PATIENT,REPORTER_IS_PRESCRIBER," +
		"REPORTER_TITLE,REPORTER_FIRST_NAME,REPORTER_MIDDLE_NAME,REPORTER_LAST_NAME,REPORTER_ADDRESS1,REPORTER_ADDRESS2,REPORTER_ADDRESS3," +
		"REPORTER_CITY,REPORTER_STATE,REPORTER_POSTAL_CODE,REPORTER_COUNTRY,REPORTER_PHONE_NUMBER,REPORTER_FAX_NUMBER,REPORTER_EMAIL," +
		"REPORTER_SPECIALTY,REPORTER_OCCUPATION,UPDATE_USER_ID,UPDATE_TIMESTAMP,REPORTER_IS_TREATING_MD,REPORT_ID,REPORTER_DETAILS_ID) " +
		"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	

	private final String SQL_UPDATE_STRING = "UPDATE LAU_REPORTER_DETAILS SET REPORTER_SOURCE_TYPE=?,REPORTER_TYPE=?,REPORTER_IS_PATIENT=?," +
		"REPORTER_CONTACT_DATE=?,REPORTER_CONTACT_METHOD=?,REPORTER_CONTACT_REASON=?,RELATIONSHIP_TO_PATIENT=?,REPORTER_IS_PRESCRIBER=?," +
		"REPORTER_TITLE=?,REPORTER_FIRST_NAME=?,REPORTER_MIDDLE_NAME=?,REPORTER_LAST_NAME=?,REPORTER_ADDRESS1=?,REPORTER_ADDRESS2=?," +
		"REPORTER_ADDRESS3=?,REPORTER_CITY=?,REPORTER_STATE=?,REPORTER_POSTAL_CODE=?,REPORTER_COUNTRY=?,REPORTER_PHONE_NUMBER=?," +
		"REPORTER_FAX_NUMBER=?,REPORTER_EMAIL=?,REPORTER_SPECIALTY=?,REPORTER_OCCUPATION=?," +
		"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?, REPORTER_IS_TREATING_MD=? WHERE REPORT_ID=? AND REPORTER_DETAILS_ID=?";

	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		CommonDAO.setDbmsClientInfo(template,userId );
		id = template.update("DELETE FROM LAU_REPORTER_DETAILS WHERE REPORTER_DETAILS_ID = ?",
				new Object[]{this.reporterDetails.getReporterDetailsId()});
		log.info("LauReporterDetailsSetDAO delete() ID -> " + id);			
	}
	
	/**
	 
	 	LAU_REPORTER_DETAILS

		REPORTER_DETAILS_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		REPORTER_SOURCE_TYPE VARCHAR2(50),
		REPORTER_TYPE VARCHAR2(50),
		REPORTER_IS_PATIENT VARCHAR2(50),
		REPORTER_CONTACT_DATE VARCHAR2(8),
		REPORTER_CONTACT_METHOD VARCHAR2(50),
		REPORTER_CONTACT_REASON VARCHAR2(100),
		RELATIONSHIP_TO_PATIENT VARCHAR2(100),
		REPORTER_IS_PRESCRIBER VARCHAR2(5),
		REPORTER_IS_TREATING_MD VARCHAR2(5),
		REPORTER_TITLE VARCHAR2(300),
		REPORTER_FIRST_NAME VARCHAR2(300),
		REPORTER_MIDDLE_NAME VARCHAR2(300),
		REPORTER_LAST_NAME VARCHAR2(300),
		REPORTER_ADDRESS1 VARCHAR2(300),
		REPORTER_ADDRESS2 VARCHAR2(300),
		REPORTER_ADDRESS3 VARCHAR2(300),
		REPORTER_CITY VARCHAR2(300),
		REPORTER_STATE VARCHAR2(50),
		REPORTER_POSTAL_CODE VARCHAR2(100),
		REPORTER_COUNTRY VARCHAR2(50),
		REPORTER_PHONE_NUMBER VARCHAR2(100),
		REPORTER_FAX_NUMBER VARCHAR2(100),
		REPORTER_EMAIL VARCHAR2(300),
		REPORTER_SPECIALTY VARCHAR2(300),
		REPORTER_OCCUPATION VARCHAR2(300),
		HCP_CONTACT_DETAILS_AVAILABLE VARCHAR2(5),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP null NOT NULL
	

	 */

}
