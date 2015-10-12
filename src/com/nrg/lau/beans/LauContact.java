package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.nrg.lau.commons.IReportsConstants;
import com.nrg.lau.dao.LauContactSetDAO;


public class LauContact implements Serializable {

	private static Logger log	= Logger.getLogger(LauContact.class);
	private long contactId = 0;
	private BigDecimal displayNumber = IReportsConstants.ZERO;
	private String displayNumber2 = "";
	private String contactType = "";
	private String contactMethod = "";
	private String contactDate = "";
	private String contactTitle = "";
	private String contactFirstName = "";
	private String contactMiddleName = "";
	private String contactLastName = "";
	private String contactAddress1 = "";
	private String contactAddress2 = "";
	private String contactAddress3 = "";
	private String contactCity = "";
	private String contactState = "";
	private String contactPostalCode = "";
	private String contactCountry = "";
	private String contactPhoneNumber = "";
	private String contactFaxNumber = "";
	private String contactEmail = "";
	private String contactIntermediary = "";
	private String contactOccupation = "";
	private String contactSpecialty = "";
	private String contactComment = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long reportId = 0;
	private long transactionType = -1;

	private String contactReason = "";
	private String reporterIsPatient = "";
	private String reporterType = "";


	private String contactIsReporter = "";
	private String reporterSourceType = "";
	private String relationToPatient = "";
	private String literatureReference = "";
	private String CONTACT_MOBILE_NUMBER= "";
	
	private String CONTACT_CONFIDENTIAL_FLAG="";
	private String CONTACT_DEPARTMENT="";
	private String CONTACT_PHONE_CC="";
	private String CONTACT_PHONE_EXT="";
	private String CONTACT_FAX_CC="";
	private String CONTACT_FAX_EXT="";
	private String CONTACT_MOBILE_CC="";
	private String CONTACT_MOBILE_EXT="";
	private String OTHER_OCCUPATION_DESC="";
	private String CUSTOM_TEXT_01="";
	private String CUSTOM_TEXT_02="";
	private String CUSTOM_TEXT_03="";
	private String REPORT_SENT_TO_FDA = "";
	
	private String activityId  = "";
	private String INTERNAL_LINK_ID="";
	private static final long serialVersionUID = 1L;

	public LauContact() {
		super();
	}

	public long getContactId() {
		return this.contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public BigDecimal getDisplayNumber() {
		return this.displayNumber;
	}

	public void setDisplayNumber(BigDecimal displayNumber) {
		this.displayNumber = displayNumber;
	}

	public String getDisplayNumber2() {
		return this.displayNumber2;
	}

	public void setDisplayNumber2(String displayNumber2) {
		if (displayNumber2.trim().length() > 0) {
			this.displayNumber = new BigDecimal(displayNumber2);
		} else
			this.displayNumber = IReportsConstants.ZERO;
	}

	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContactMethod() {
		return this.contactMethod;
	}

	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}

	public String getContactDate() {
		return this.contactDate;
	}

	public void setContactDate(String contactDate) {
		this.contactDate = contactDate;
	}

	public String getContactTitle() {
		return this.contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getContactFirstName() {
		return this.contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getContactMiddleName() {
		return this.contactMiddleName;
	}

	public void setContactMiddleName(String contactMiddleName) {
		this.contactMiddleName = contactMiddleName;
	}

	public String getContactIntermediary(){
		return this.contactIntermediary;
	}
	public void setContactIntermediary(String contactIntermediary){
		this.contactIntermediary = contactIntermediary;
	}
	public String getContactLastName() {
		return this.contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}
	
	public String getContactAddress1() {
		return this.contactAddress1;
	}

	public void setContactAddress1(String contactAddress1) {
		this.contactAddress1 = contactAddress1;
	}

	public String getContactAddress2() {
		return this.contactAddress2;
	}

	public void setContactAddress2(String contactAddress2) {
		this.contactAddress2 = contactAddress2;
		log.info("contact address 2:"+contactAddress2);
	}

	public String getContactAddress3() {
		return this.contactAddress3;
	}

	public void setContactAddress3(String contactAddress3) {
		this.contactAddress3 = contactAddress3;
	}

	public String getContactCity() {
		return this.contactCity;
	}

	public void setContactCity(String contactCity) {
		this.contactCity = contactCity;
	}

	public String getContactState() {
		return this.contactState;
	}

	public void setContactState(String contactState) {
		this.contactState = contactState;
	}

	public String getContactPostalCode() {
		return this.contactPostalCode;
	}

	public void setContactPostalCode(String contactPostalCode) {
		this.contactPostalCode = contactPostalCode;
	}

	public String getContactCountry() {
		return this.contactCountry;
	}

	public void setContactCountry(String contactCountry) {
		this.contactCountry = contactCountry;
	}

	public String getContactPhoneNumber() {
		return this.contactPhoneNumber;
	}

	public String getCONTACT_MOBILE_NUMBER() {
		return CONTACT_MOBILE_NUMBER;
	}

	public void setCONTACT_MOBILE_NUMBER(String cONTACT_MOBILE_NUMBER) {
		CONTACT_MOBILE_NUMBER = cONTACT_MOBILE_NUMBER;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public String getContactFaxNumber() {
		return this.contactFaxNumber;
	}

	public void setContactFaxNumber(String contactFaxNumber) {
		this.contactFaxNumber = contactFaxNumber;
	}

	public String getContactEmail() {
		return this.contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactOccupation() {
		return this.contactOccupation;
	}

	public void setContactOccupation(String contactOccupation) {
		this.contactOccupation = contactOccupation;
	}

	public String getContactSpecialty() {
		return this.contactSpecialty;
	}

	public void setContactSpecialty(String contactSpecialty) {
		this.contactSpecialty = contactSpecialty;
	}

	public String getContactComment() {
		return this.contactComment;
	}

	public void setContactComment(String contactComment) {
		this.contactComment = contactComment;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Object getUpdateTimeStamp() {
		return this.updateTimeStamp;
	}

	public void setUpdateTimeStamp(Object updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	public long getReportId() {
		return this.reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the contactReason
	 */
	public String getContactReason() {
		return contactReason;
	}

	/**
	 * @param contactReason
	 *            the contactReason to set
	 */
	public void setContactReason(String contactReason) {
		this.contactReason = contactReason;
	}

	/**
	 * @return the reporterIsPatient
	 */
	public String getReporterIsPatient() {
		return reporterIsPatient;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * @param reporterIsPatient
	 *            the reporterIsPatient to set
	 */
	public void setReporterIsPatient(String reporterIsPatient) {
		this.reporterIsPatient = reporterIsPatient;
	}

	/**
	 * @return the reporterType
	 */
	public String getReporterType() {
		return reporterType;
	}

	/**
	 * @param reporterType
	 *            the reporterType to set
	 */
	public void setReporterType(String reporterType) {
		this.reporterType = reporterType;
	}

	/**
	 * @return the contactIsReporter
	 */
	public String getContactIsReporter() {
		return contactIsReporter;
	}

	/**
	 * @param contactIsReporter the contactIsReporter to set
	 */
	public void setContactIsReporter(String contactIsReporter) {
		this.contactIsReporter = contactIsReporter;
	}

	/**
	 * @return the reporterSourceType
	 */
	public String getReporterSourceType() {
		return reporterSourceType;
	}

	/**
	 * @param reporterSourceType the reporterSourceType to set
	 */
	public void setReporterSourceType(String reporterSourceType) {
		this.reporterSourceType = reporterSourceType;
	}

   public String getLiteratureReference(){
	   return this.literatureReference;
   }
   public void setLiteratureReference(String literatureReference){
	   this.literatureReference = literatureReference;
   }
	public String getCONTACT_CONFIDENTIAL_FLAG() {
	return CONTACT_CONFIDENTIAL_FLAG;
}

public void setCONTACT_CONFIDENTIAL_FLAG(String cONTACT_CONFIDENTIAL_FLAG) {
	CONTACT_CONFIDENTIAL_FLAG = cONTACT_CONFIDENTIAL_FLAG;
}

public String getCONTACT_DEPARTMENT() {
	return CONTACT_DEPARTMENT;
}

public void setCONTACT_DEPARTMENT(String cONTACT_DEPARTMENT) {
	CONTACT_DEPARTMENT = cONTACT_DEPARTMENT;
}

public String getCONTACT_PHONE_CC() {
	return CONTACT_PHONE_CC;
}

public void setCONTACT_PHONE_CC(String cONTACT_PHONE_CC) {
	CONTACT_PHONE_CC = cONTACT_PHONE_CC;
}

public String getCONTACT_PHONE_EXT() {
	return CONTACT_PHONE_EXT;
}

public void setCONTACT_PHONE_EXT(String cONTACT_PHONE_EXT) {
	CONTACT_PHONE_EXT = cONTACT_PHONE_EXT;
}

public String getCONTACT_FAX_CC() {
	return CONTACT_FAX_CC;
}

public void setCONTACT_FAX_CC(String cONTACT_FAX_CC) {
	CONTACT_FAX_CC = cONTACT_FAX_CC;
}

public String getCONTACT_FAX_EXT() {
	return CONTACT_FAX_EXT;
}

public void setCONTACT_FAX_EXT(String cONTACT_FAX_EXT) {
	CONTACT_FAX_EXT = cONTACT_FAX_EXT;
}

public String getCONTACT_MOBILE_CC() {
	return CONTACT_MOBILE_CC;
}

public void setCONTACT_MOBILE_CC(String cONTACT_MOBILE_CC) {
	CONTACT_MOBILE_CC = cONTACT_MOBILE_CC;
}

public String getCONTACT_MOBILE_EXT() {
	return CONTACT_MOBILE_EXT;
}

public void setCONTACT_MOBILE_EXT(String cONTACT_MOBILE_EXT) {
	CONTACT_MOBILE_EXT = cONTACT_MOBILE_EXT;
}

public String getOTHER_OCCUPATION_DESC() {
	return OTHER_OCCUPATION_DESC;
}

public void setOTHER_OCCUPATION_DESC(String oTHER_OCCUPATION_DESC) {
	OTHER_OCCUPATION_DESC = oTHER_OCCUPATION_DESC;
}

public String getCUSTOM_TEXT_01() {
	return CUSTOM_TEXT_01;
}

public void setCUSTOM_TEXT_01(String cUSTOM_TEXT_01) {
	CUSTOM_TEXT_01 = cUSTOM_TEXT_01;
}

public String getCUSTOM_TEXT_02() {
	return CUSTOM_TEXT_02;
}

public void setCUSTOM_TEXT_02(String cUSTOM_TEXT_02) {
	CUSTOM_TEXT_02 = cUSTOM_TEXT_02;
}

public String getCUSTOM_TEXT_03() {
	return CUSTOM_TEXT_03;
}

public void setCUSTOM_TEXT_03(String cUSTOM_TEXT_03) {
	CUSTOM_TEXT_03 = cUSTOM_TEXT_03;
}

	/**
	 * @return the relationToPatient
	 */
	public String getRelationToPatient() {
		return relationToPatient;
	}

	/**
	 * @param relationToPatient the relationToPatient to set
	 */
	public void setRelationToPatient(String relationToPatient) {
		this.relationToPatient = relationToPatient;
	}

	public String toString() {
		StringBuffer str = new StringBuffer(LauContact.class.getName());
		str.append("[");
		str.append("contactId=");
		str.append(contactId);
		str.append(", displayNumber=");
		str.append(displayNumber);
		str.append(", contactType=");
		str.append(contactType);
		str.append(", contactMethod=");
		str.append(contactMethod);
		str.append(", contactDate=");
		str.append(contactDate);
		str.append(", contactorType=");
		str.append(contactTitle);
		str.append(", contactFirstName=");
		str.append(contactFirstName);
		str.append(", contactMiddleName=");
		str.append(contactMiddleName);
		str.append(", contactLastName=");
		str.append(contactLastName);
		str.append(", contactAddress1=");
		str.append(contactAddress1);
		str.append(", contactAddress2=");
		str.append(contactAddress2);
		str.append(", contactAddress3=");
		str.append(contactAddress3);
		str.append(", contactCity=");
		str.append(contactCity);
		str.append(", contactState=");
		str.append(contactState);
		str.append(", contactPostalCode=");
		str.append(contactPostalCode);
		str.append(", contactCountry=");
		str.append(contactCountry);
		str.append(", contactPhoneNumber=");
		str.append(contactPhoneNumber);
		str.append(", contactFaxNumber=");
		str.append(contactFaxNumber);
		str.append(", contactEmail=");
		str.append(contactEmail);
		str.append(", contactIntermediary=");
		str.append(contactIntermediary);
		str.append(", contactOccupation=");
		str.append(contactOccupation);
		str.append(", contactSpecialty=");
		str.append(contactSpecialty);
		str.append(", contactComment=");
		str.append(contactComment);
		str.append(", contactReasont=");
		str.append(contactReason);
		str.append(", reporterIsPatient=");
		str.append(reporterIsPatient);
		str.append(", reporterType=");
		str.append(reporterType);
		
		str.append(", contactIsReporter=");
		str.append(contactIsReporter);
		str.append(", reporterSourceType=");
		str.append(reporterSourceType);
		str.append(", relationToPatient=");
		str.append(relationToPatient);
		str.append(", literatureReference=");
		str.append(literatureReference);
		
		str.append(", CONTACT_CONFIDENTIAL_FLAG=");
		str.append(CONTACT_CONFIDENTIAL_FLAG);
		str.append(", CONTACT_DEPARTMENT=");
		str.append(CONTACT_DEPARTMENT);
		str.append(", CONTACT_PHONE_CC=");
		str.append(CONTACT_PHONE_CC);
		str.append(", CONTACT_PHONE_EXT=");
		str.append(CONTACT_PHONE_EXT);
		str.append(", CONTACT_FAX_CC=");
		str.append(CONTACT_FAX_CC);
		str.append(", CONTACT_FAX_EXT=");
		str.append(CONTACT_FAX_EXT);
		str.append(", CONTACT_MOBILE_CC=");
		str.append(CONTACT_MOBILE_CC);
		str.append(", CONTACT_MOBILE_NUMBER=");
		str.append(CONTACT_MOBILE_NUMBER);
		str.append(", CONTACT_MOBILE_EXT=");
		str.append(CONTACT_MOBILE_EXT);
		str.append(", OTHER_OCCUPATION_DESC=");
		str.append(OTHER_OCCUPATION_DESC);
		str.append(", CUSTOM_TEXT_01=");
		str.append(CUSTOM_TEXT_01);
		str.append(", CUSTOM_TEXT_02=");
		str.append(CUSTOM_TEXT_02);
		str.append(", CUSTOM_TEXT_03=");
		str.append(CUSTOM_TEXT_03);
				
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append("]");
		return str.toString();
	}

	public String getREPORT_SENT_TO_FDA() {
		return REPORT_SENT_TO_FDA;
	}

	public void setREPORT_SENT_TO_FDA(String rEPORT_SENT_TO_FDA) {
		REPORT_SENT_TO_FDA = rEPORT_SENT_TO_FDA;
	}

	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}

	public String getINTERNAL_LINK_ID() {
		return INTERNAL_LINK_ID;
	}

	public void setINTERNAL_LINK_ID(String iNTERNAL_LINK_ID) {
		INTERNAL_LINK_ID = iNTERNAL_LINK_ID;
	}
	
	
}
