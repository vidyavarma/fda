package com.nrg.lau.dao;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.nrg.lau.beans.LauContact;
import com.nrg.lau.commons.IReportChildSetDAO;
import com.nrg.lau.commons.IReportsConstants;

public abstract class LauPersonSetDAO implements IReportChildSetDAO<LauContact> {

	private static Logger log	= Logger.getLogger(LauContactSetDAO.class);
	private Vector<LauContact> reports		= null;
	private LauContact contact 	= null;	
	private java.sql.Timestamp dt  = null; 
	private String userId	= "";
	private long contactID	= 0;
	
	protected void finalize() throws Throwable {		
		this.reports.removeAllElements();
		this.reports = null;
		super.finalize();
	}
	
	
	public long save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId,String user,java.sql.Timestamp dstamp,long activityId) throws Exception {
		userId = user;
		dt = dstamp;
		//Check to make sure that XML is there in Request.
		if(request.getParameter(IReportsConstants.LAU_CONTACT_PARAM_NAME) != null && 
				request.getParameter(IReportsConstants.LAU_CONTACT_PARAM_NAME).length() > 0 ) {
			
			log.info("LauContactDAO save() LAU_CONTACT_PARAM_NAME -> " 
					+ request.getParameter(IReportsConstants.LAU_CONTACT_PARAM_NAME));
			
			//Create LauReports beans from XML Request
			createBeansFromXml(request.getParameter(IReportsConstants.LAU_CONTACT_PARAM_NAME));
			Iterator<LauContact> itr = this.reports.iterator();
			
			//Report Id is 0 it's UPDATE else a new INSERT
			while(itr.hasNext())	{
				LauContact lauContact = (LauContact)itr.next();
				log.info("itr.hasNext() -> " + lauContact.toString());
				this.contact	= null;
				this.contact	= lauContact;
				if(lauContact.getActivityId().equals("-999999")) {
					lauContact.setActivityId(String.valueOf(activityId));
					log.info("lauReportActivities.setActivityId -> " + lauContact.getActivityId());
				}
				
				if(lauContact.getContactId() == 0)	{
						insert(template,reportId);
				}else  {
					if(lauContact.getTransactionType() == IReportsConstants.deleteFlag)	{
						delete(template);
					}else	update(template);				
				}
				contactID = lauContact.getContactId();
			}//end of while(itr.hasNext())
		}	else	{
			log.info(IReportsConstants.LAU_CONTACT_PARAM_NAME + " not found in request");
		}
		return contactID;		
	}
	
	
	public void insert(SimpleJdbcTemplate template, long reportId)
			throws Exception {
		int id = 0;
		long contactId = CommonDAO.getPrimaryKey(template);
		this.contact.setContactId(contactId);
		if(this.contact.getReportId() == 0)	{
			this.contact.setReportId(reportId);	}
		log.info("Generated Primary Key for CONTACT_ID ->" + contactId);
		id = template.update(SQL_INSERT_STRING,getInsertParameters(template));
		log.info("LauContactDAO insert() ID -> " + id);
		if(this.contact.getActivityId().length() > 0)// if assigned to an activity
		{  
			id = template.update(SQL_UPDATE_ACTIVITY,new Object[] { this.contact.getContactId(), 
					userId, dt, this.contact.getReportId(),this.contact.getActivityId()});
			log.info("ACTIVITY UPDATE () ID -> " + id);
		}
		
	}

	
	public void update(SimpleJdbcTemplate template) throws Exception {
		log.info("Entered Update");
		int id = 0;
		id = template.update(SQL_UPDATE_STRING,getParameters());
		log.info("LauContactDAO update() ID -> " + id);	
		
	}
	
	private Object[] getParameters()
	{
		LauContact lauContact = this.contact; 
		return new Object[]{
			  lauContact.getDisplayNumber(),
			  lauContact.getContactType(),
			  lauContact.getContactMethod(),
			  lauContact.getContactDate(),
			  lauContact.getContactTitle(),
			  lauContact.getContactFirstName(),
			  lauContact.getContactMiddleName(),
			  lauContact.getContactLastName(),
			  lauContact.getContactAddress1(),
			  lauContact.getContactAddress2(),
			  lauContact.getContactAddress3(),
			  lauContact.getContactCity(),
			  lauContact.getContactState(),
			  lauContact.getContactPostalCode(),
			  lauContact.getContactCountry(),
			  lauContact.getContactPhoneNumber(),
			  lauContact.getCONTACT_MOBILE_NUMBER(),
			  lauContact.getContactFaxNumber(),
			  lauContact.getContactEmail(),
			  lauContact.getContactIntermediary(),
			  lauContact.getContactOccupation(),
			  lauContact.getContactSpecialty(),
			  lauContact.getContactComment(),
			  lauContact.getContactReason(),
			  lauContact.getReporterIsPatient(),
			  lauContact.getReporterType(),
			  lauContact.getContactIsReporter(),
			  lauContact.getReporterSourceType(),
			  lauContact.getRelationToPatient(),
			  lauContact.getLiteratureReference(),
			  lauContact.getREPORT_SENT_TO_FDA(),
			  userId,
			  dt,
			  lauContact.getCONTACT_CONFIDENTIAL_FLAG(),
			  lauContact.getCONTACT_DEPARTMENT(),
			  lauContact.getCONTACT_FAX_CC(),
			  lauContact.getCONTACT_FAX_EXT(),
			  lauContact.getCONTACT_PHONE_CC(),
			  lauContact.getCONTACT_PHONE_EXT(),
			  lauContact.getCONTACT_MOBILE_CC(),
			  lauContact.getCONTACT_MOBILE_EXT(),
			  lauContact.getOTHER_OCCUPATION_DESC(),
			  lauContact.getCUSTOM_TEXT_01(),
			  lauContact.getCUSTOM_TEXT_02(),
			  lauContact.getCUSTOM_TEXT_03(),
			  lauContact.getReportId(),
			  lauContact.getContactId()

		};
	}
	private Object[] getInsertParameters(SimpleJdbcTemplate template)
	{
		LauContact lauContact = this.contact; 
		return new Object[]{
			CommonDAO.getNextDisplayNumber(template, lauContact.getReportId(), "LAU_CONTACT",0),
			  lauContact.getContactType(),
			  lauContact.getContactMethod(),
			  lauContact.getContactDate(),
			  lauContact.getContactTitle(),
			  lauContact.getContactFirstName(),
			  lauContact.getContactMiddleName(),
			  lauContact.getContactLastName(),
			  lauContact.getContactAddress1(),
			  lauContact.getContactAddress2(),
			  lauContact.getContactAddress3(),
			  lauContact.getContactCity(),
			  lauContact.getContactState(),
			  lauContact.getContactPostalCode(),
			  lauContact.getContactCountry(),
			  lauContact.getContactPhoneNumber(),
			  lauContact.getCONTACT_MOBILE_NUMBER(),
			  lauContact.getContactFaxNumber(),
			  lauContact.getContactEmail(),
			  lauContact.getContactIntermediary(),
			  lauContact.getContactOccupation(),
			  lauContact.getContactSpecialty(),
			  lauContact.getContactComment(),
			  lauContact.getContactReason(),
			  lauContact.getReporterIsPatient(),
			  lauContact.getReporterType(),
			  lauContact.getContactIsReporter(),
			  lauContact.getReporterSourceType(),
			  lauContact.getRelationToPatient(),
			  lauContact.getLiteratureReference(),
			  lauContact.getREPORT_SENT_TO_FDA(),
			  userId,
			  dt,
			  lauContact.getCONTACT_CONFIDENTIAL_FLAG(),
			  lauContact.getCONTACT_DEPARTMENT(),
			  lauContact.getCONTACT_FAX_CC(),
			  lauContact.getCONTACT_FAX_EXT(),
			  lauContact.getCONTACT_PHONE_CC(),
			  lauContact.getCONTACT_PHONE_EXT(),
			  lauContact.getCONTACT_MOBILE_CC(),
			  lauContact.getCONTACT_MOBILE_EXT(),
			  lauContact.getOTHER_OCCUPATION_DESC(),
			  lauContact.getCUSTOM_TEXT_01(),
			  lauContact.getCUSTOM_TEXT_02(),
			  lauContact.getCUSTOM_TEXT_03(),
			  lauContact.getReportId(),
			  lauContact.getContactId()

		};
	}	
	
	public void createBeansFromXml(String xml) throws Exception {
		
		reports				= new Vector<LauContact>();
		Digester digester	= new Digester();
		digester.push(this);
		String mainXmlTag = "ROWSET/ROW";
		digester.addObjectCreate( mainXmlTag, LauContact.class );
		
		//Explicitly specify property
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_ID", "contactId" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_ID", "reportId" );
		digester.addBeanPropertySetter( mainXmlTag+"/DISPLAY_NUMBER", "displayNumber2" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_TYPE", "contactType" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_METHOD", "contactMethod" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_DATE", "contactDate" );	
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_TITLE", "contactTitle" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_FIRST_NAME", "contactFirstName" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_MIDDLE_NAME", "contactMiddleName" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_LAST_NAME", "contactLastName" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_ADDRESS1", "contactAddress1" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_ADDRESS2", "contactAddress2" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_ADDRESS3", "contactAddress3" );		
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_CITY", "contactCity" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_STATE", "contactState" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_POSTAL_CODE", "contactPostalCode" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_COUNTRY", "contactCountry" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_PHONE_NUMBER", "contactPhoneNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_FAX_NUMBER", "contactFaxNumber" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_EMAIL", "contactEmail" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_ORGANIZATION", "contactIntermediary" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_OCCUPATION", "contactOccupation" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_SPECIALTY", "contactSpecialty" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_COMMENT", "contactComment" );
		digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_USER_ID", "updateUserId" );
		digester.addBeanPropertySetter( mainXmlTag+"/TRANSACTION_TYPE", "transactionType" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_MOBILE_NUMBER", "CONTACT_MOBILE_NUMBER" );
		
		// Modified on 03-May-2010 - S.Abraham
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_REASON", "contactReason" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_IS_PATIENT", "reporterIsPatient" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_TYPE", "reporterType" );
		
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_IS_REPORTER", "contactIsReporter" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORTER_SOURCE_TYPE", "reporterSourceType" );
		digester.addBeanPropertySetter( mainXmlTag+"/RELATIONSHIP_TO_PATIENT", "relationToPatient" );
		digester.addBeanPropertySetter( mainXmlTag+"/LITERATURE_REFERENCE", "literatureReference" );
		
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_CONFIDENTIAL_FLAG", "CONTACT_CONFIDENTIAL_FLAG" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_DEPARTMENT", "CONTACT_DEPARTMENT" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_PHONE_CC", "CONTACT_PHONE_CC" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_PHONE_EXT", "CONTACT_PHONE_EXT" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_FAX_CC", "CONTACT_FAX_CC" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_FAX_EXT", "CONTACT_FAX_EXT" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_MOBILE_CC", "CONTACT_MOBILE_CC" );
		digester.addBeanPropertySetter( mainXmlTag+"/CONTACT_MOBILE_EXT", "CONTACT_MOBILE_EXT" );
		digester.addBeanPropertySetter( mainXmlTag+"/OTHER_OCCUPATION_DESC", "OTHER_OCCUPATION_DESC" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_01", "CUSTOM_TEXT_01" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_02", "CUSTOM_TEXT_02" );
		digester.addBeanPropertySetter( mainXmlTag+"/CUSTOM_TEXT_03", "CUSTOM_TEXT_03" );
		digester.addBeanPropertySetter( mainXmlTag+"/REPORT_SENT_TO_FDA", "REPORT_SENT_TO_FDA" );
		digester.addBeanPropertySetter( mainXmlTag+"/ACTIVITY_ID", "activityId" );
		digester.addBeanPropertySetter( mainXmlTag+"/INTERNAL_LINK_ID", "INTERNAL_LINK_ID");
		
		//digester.addBeanPropertySetter( mainXmlTag+"/UPDATE_TIMESTAMP", "updateTimeStamp" );
				
		//Move to next LauReports
		digester.addSetNext( mainXmlTag, "addXmlData" );	
		digester.parse(new StringReader(xml));
		
	}
	
	public void addXmlData(LauContact lauContact) {
		reports.add(lauContact);
		
	}
	
	//SQL Statements	
	private final String SQL_UPDATE_STRING = "UPDATE LAU_CONTACT SET DISPLAY_NUMBER=?,CONTACT_TYPE=?,CONTACT_METHOD=?," +
		"CONTACT_DATE=?,CONTACT_TITLE=?,CONTACT_FIRST_NAME=?,CONTACT_MIDDLE_NAME=?,CONTACT_LAST_NAME=?," +
		"CONTACT_ADDRESS1=?,CONTACT_ADDRESS2=?,CONTACT_ADDRESS3=?,CONTACT_CITY=?,CONTACT_STATE=?,CONTACT_POSTAL_CODE=?," +
		"CONTACT_COUNTRY=?,CONTACT_PHONE_NUMBER=?,CONTACT_MOBILE_NUMBER=?,CONTACT_FAX_NUMBER=?,CONTACT_EMAIL=?,CONTACT_ORGANIZATION=?,CONTACT_OCCUPATION=?," +
		"CONTACT_SPECIALTY=?,CONTACT_COMMENT=?,CONTACT_REASON=?,REPORTER_IS_PATIENT=?,REPORTER_TYPE=?,CONTACT_IS_REPORTER = ?, "+
		"REPORTER_SOURCE_TYPE = ?, RELATIONSHIP_TO_PATIENT = ?,LITERATURE_REFERENCE=?,REPORT_SENT_TO_FDA=?,UPDATE_USER_ID=?,UPDATE_TIMESTAMP=?, "+
		"CONTACT_CONFIDENTIAL_FLAG=?,CONTACT_DEPARTMENT=?,CONTACT_FAX_CC=?,CONTACT_FAX_EXT=?,CONTACT_PHONE_CC=?,CONTACT_PHONE_EXT=?, "+
		"CONTACT_MOBILE_CC=?,CONTACT_MOBILE_EXT=?,OTHER_OCCUPATION_DESC=?,CUSTOM_TEXT_01=?,CUSTOM_TEXT_02=?,CUSTOM_TEXT_03=? WHERE REPORT_ID=? AND CONTACT_ID=?";	
	
	private final String SQL_INSERT_STRING = "INSERT INTO LAU_CONTACT(DISPLAY_NUMBER,CONTACT_TYPE,CONTACT_METHOD," +
		"CONTACT_DATE,CONTACT_TITLE,CONTACT_FIRST_NAME,CONTACT_MIDDLE_NAME,CONTACT_LAST_NAME," +
		"CONTACT_ADDRESS1,CONTACT_ADDRESS2,CONTACT_ADDRESS3,CONTACT_CITY,CONTACT_STATE,CONTACT_POSTAL_CODE," +
		"CONTACT_COUNTRY,CONTACT_PHONE_NUMBER,CONTACT_MOBILE_NUMBER,CONTACT_FAX_NUMBER,CONTACT_EMAIL,CONTACT_ORGANIZATION,CONTACT_OCCUPATION," +
		"CONTACT_SPECIALTY,CONTACT_COMMENT,CONTACT_REASON,REPORTER_IS_PATIENT,REPORTER_TYPE,"+
		" CONTACT_IS_REPORTER,REPORTER_SOURCE_TYPE, RELATIONSHIP_TO_PATIENT,LITERATURE_REFERENCE,REPORT_SENT_TO_FDA,UPDATE_USER_ID,UPDATE_TIMESTAMP,CONTACT_CONFIDENTIAL_FLAG,CONTACT_DEPARTMENT,CONTACT_FAX_CC,CONTACT_FAX_EXT,CONTACT_PHONE_CC,CONTACT_PHONE_EXT, "+
		"CONTACT_MOBILE_CC,CONTACT_MOBILE_EXT,OTHER_OCCUPATION_DESC,CUSTOM_TEXT_01,CUSTOM_TEXT_02,CUSTOM_TEXT_03,REPORT_ID,CONTACT_ID) " +
		"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final String SQL_UPDATE_ACTIVITY = "UPDATE LAU_REPORT_ACTIVITIES SET CONTACT_ID=?," +
			"UPDATE_USER_ID=?,UPDATE_TIMESTAMP=? WHERE REPORT_ID=? AND ACTIVITY_ID=?" ;
	
	public void delete(SimpleJdbcTemplate template) throws Exception {
		int id = 0;
		id = template.update("DELETE FROM LAU_CONTACT WHERE CONTACT_ID = ?",
				new Object[]{this.contact.getContactId()});
		CommonDAO.setReseqenceDisplayOrder(template, this.contact.getReportId(), userId, dt, 
				"LAU_CONTACT", "DISPLAY_NUMBER", 1, null,null,1);
		log.info("LauContactSetDAO delete() ID -> " + id);		
	}

	public void save(HttpServletRequest request, SimpleJdbcTemplate template,
			long reportId, String user, Timestamp dstamp) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 	LAU_CONTACT
	 	
		CONTACT_ID NUMBER(22 , 0) NOT NULL,
		REPORT_ID NUMBER(22 , 0) NOT NULL,
		DISPLAY_NUMBER NUMBER(22 , 0),
		CONTACT_TYPE VARCHAR2(50),
		CONTACT_METHOD VARCHAR2(50),
		CONTACT_REASON VARCHAR2(100),
		CONTACT_DATE VARCHAR2(8),
		CONTACT_TITLE VARCHAR2(100),
		CONTACT_FIRST_NAME VARCHAR2(100),
		CONTACT_MIDDLE_NAME VARCHAR2(100),
		CONTACT_LAST_NAME VARCHAR2(100),
		CONTACT_ADDRESS1 VARCHAR2(100),
		CONTACT_ADDRESS2 VARCHAR2(100),
		CONTACT_ADDRESS3 VARCHAR2(100),
		CONTACT_CITY VARCHAR2(100),
		CONTACT_STATE VARCHAR2(50),
		CONTACT_POSTAL_CODE VARCHAR2(100),
		CONTACT_COUNTRY VARCHAR2(50),
		CONTACT_PHONE_NUMBER VARCHAR2(100),
		CONTACT_FAX_NUMBER VARCHAR2(100),
		CONTACT_EMAIL VARCHAR2(300),
		CONTACT_OCCUPATION VARCHAR2(300),
		CONTACT_SPECIALTY VARCHAR2(300),
		CONTACT_COMMENT CLOB,
		CONTACT_IS_REPORTER VARCHAR2(5),
		REPORTER_SOURCE_TYPE VARCHAR2(50),
		REPORTER_TYPE VARCHAR2(50),
		REPORTER_IS_PATIENT VARCHAR2(5),
		RELATIONSHIP_TO_PATIENT VARCHAR2(300),
		UPDATE_USER_ID VARCHAR2(300) NOT NULL,
		UPDATE_TIMESTAMP null NOT NULL
		
	 */
}
