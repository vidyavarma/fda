package com.nrg.lau.beans;

import java.io.Serializable;

public class LauMultipleSummaryDetails implements Serializable{

	private static final long serialVersionUID = 7678201508339757424L;
	private long REPORT_ID 					= 0;
	private long CONTACT_ID					= 0;
	private String CONTACT_LAST_NAME		= "";
	private String CONTACT_DISPLAY_NUMBER 	= "";
	private long PATIENT_DETAILS_ID 		= 0;
	private String PT_SEX 					= "";
	private String PT_INITIALS 				= "";
	private String PT_LAST_NAME 			= "";
	private String PT_DOB 					= "";
	private String COUNTRY_EVENT_OCCURRED 	= "";
	private long PRODUCT_ID 				= 0;
	private String PRODUCT_DISPLAY_NUMBER 	= "";
	private String PRODUCT_TYPE 	= "";
	private String PRODUCT_NAME 			= "";
	private String TRADE_NAME 				= "";
	private long EVENT_ID 					= 0;
	private String EVENT_VERBATIM 			= "";
	private String EVENT_DISPLAY_NUMBER 	= "";
	private String ONSET_DATE 				= "";
	private long TRANSACTION_TYPE 			= -1;
	private String UPDATE_USER_ID 			= "";
	private Object UPDATE_TIMESTAMP 		= "";
	private long PATIENT_TRANSACTION_TYPE			= -1;
	private long PRODUCT_TRANSACTION_TYPE			= -1;
	private long CONTACT_TRANSACTION_TYPE			= -1;
	private long EVENT_TRANSACTION_TYPE			= -1;
	private String CONTACT_IS_REPORTER 			= "";
	public long getREPORT_ID() {
		return REPORT_ID;
	}
	public void setREPORT_ID(long rEPORTID) {
		REPORT_ID = rEPORTID;
	}
	public long getPATIENT_DETAILS_ID() {
		return PATIENT_DETAILS_ID;
	}
	public void setPATIENT_DETAILS_ID(long pATIENTDETAILSID) {
		PATIENT_DETAILS_ID = pATIENTDETAILSID;
	}
	public String getPT_SEX() {
		return PT_SEX;
	}
	public void setPT_SEX(String pTSEX) {
		PT_SEX = pTSEX;
	}
	public String getPT_INITIALS() {
		return PT_INITIALS;
	}
	public void setPT_INITIALS(String pTINITIALS) {
		PT_INITIALS = pTINITIALS;
	}
	public String getPT_LAST_NAME() {
		return PT_LAST_NAME;
	}
	public void setPT_LAST_NAME(String pTLASTNAME) {
		PT_LAST_NAME = pTLASTNAME;
	}
	public String getPT_DOB() {
		return PT_DOB;
	}
	public void setPT_DOB(String pTDOB) {
		PT_DOB = pTDOB;
	}
	public String getCOUNTRY_EVENT_OCCURRED() {
		return COUNTRY_EVENT_OCCURRED;
	}
	public void setCOUNTRY_EVENT_OCCURRED(String cOUNTRYEVENTOCCURRED) {
		COUNTRY_EVENT_OCCURRED = cOUNTRYEVENTOCCURRED;
	}
	public long getPRODUCT_ID() {
		return PRODUCT_ID;
	}
	public void setPRODUCT_ID(long pRODUCTID) {
		PRODUCT_ID = pRODUCTID;
	}
	public String getPRODUCT_TYPE() {
		return PRODUCT_TYPE;
	}
	public void setPRODUCT_TYPE(String pRODUCT_TYPE) {
		PRODUCT_TYPE = pRODUCT_TYPE;
	}
	public String getPRODUCT_DISPLAY_NUMBER() {
		return PRODUCT_DISPLAY_NUMBER;
	}
	public void setPRODUCT_DISPLAY_NUMBER(String pRODUCTDISPLAYNUMBER) {
		PRODUCT_DISPLAY_NUMBER = pRODUCTDISPLAYNUMBER;
	}
	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}
	public void setPRODUCT_NAME(String pRODUCTNAME) {
		PRODUCT_NAME = pRODUCTNAME;
	}
	public String getTRADE_NAME() {
		return TRADE_NAME;
	}
	public void setTRADE_NAME(String tRADENAME) {
		TRADE_NAME = tRADENAME;
	}
	public long getEVENT_ID() {
		return EVENT_ID;
	}
	public void setEVENT_ID(long eVENTID) {
		EVENT_ID = eVENTID;
	}
	public String getEVENT_VERBATIM() {
		return EVENT_VERBATIM;
	}
	public void setEVENT_VERBATIM(String eVENTVERBATIM) {
		EVENT_VERBATIM = eVENTVERBATIM;
	}
	public String getEVENT_DISPLAY_NUMBER() {
		return EVENT_DISPLAY_NUMBER;
	}
	public String getCONTACT_IS_REPORTER() {
		return CONTACT_IS_REPORTER;
	}
	public void setCONTACT_IS_REPORTER(String cONTACT_IS_REPORTER) {
		CONTACT_IS_REPORTER = cONTACT_IS_REPORTER;
	}
	public void setEVENT_DISPLAY_NUMBER(String eVENTDISPLAYNUMBER) {
		EVENT_DISPLAY_NUMBER = eVENTDISPLAYNUMBER;
	}
	public String getONSET_DATE() {
		return ONSET_DATE;
	}
	public void setONSET_DATE(String oNSETDATE) {
		ONSET_DATE = oNSETDATE;
	}
	public long getTRANSACTION_TYPE() {
		return TRANSACTION_TYPE;
	}
	public void setTRANSACTION_TYPE(long tRANSACTIONTYPE) {
		TRANSACTION_TYPE = tRANSACTIONTYPE;
	}
	public long getPATIENT_TRANSACTION_TYPE() {
		return PATIENT_TRANSACTION_TYPE;
	}
	public void setPATIENT_TRANSACTION_TYPE(long pATIENT_TRANSACTION_TYPE) {
		PATIENT_TRANSACTION_TYPE = pATIENT_TRANSACTION_TYPE;
	}
	public long getPRODUCT_TRANSACTION_TYPE() {
		return PRODUCT_TRANSACTION_TYPE;
	}
	public void setPRODUCT_TRANSACTION_TYPE(long pRODUCT_TRANSACTION_TYPE) {
		PRODUCT_TRANSACTION_TYPE = pRODUCT_TRANSACTION_TYPE;
	}
	public long getCONTACT_TRANSACTION_TYPE() {
		return CONTACT_TRANSACTION_TYPE;
	}
	public void setCONTACT_TRANSACTION_TYPE(long cONTACT_TRANSACTION_TYPE) {
		CONTACT_TRANSACTION_TYPE = cONTACT_TRANSACTION_TYPE;
	}
	public long getEVENT_TRANSACTION_TYPE() {
		return EVENT_TRANSACTION_TYPE;
	}
	public void setEVENT_TRANSACTION_TYPE(long eVENT_TRANSACTION_TYPE) {
		EVENT_TRANSACTION_TYPE = eVENT_TRANSACTION_TYPE;
	}
	public String getUPDATE_USER_ID() {
		return UPDATE_USER_ID;
	}
	public void setUPDATE_USER_ID(String uPDATEUSERID) {
		UPDATE_USER_ID = uPDATEUSERID;
	}
	public Object getUPDATE_TIMESTAMP() {
		return UPDATE_TIMESTAMP;
	}
	public void setUPDATE_TIMESTAMP(Object uPDATETIMESTAMP) {
		UPDATE_TIMESTAMP = uPDATETIMESTAMP;
	}
	public long getCONTACT_ID() {
		return CONTACT_ID;
	}
	public void setCONTACT_ID(long cONTACTID) {
		CONTACT_ID = cONTACTID;
	}
	public String getCONTACT_LAST_NAME() {
		return CONTACT_LAST_NAME;
	}
	public void setCONTACT_LAST_NAME(String cONTACTLASTNAME) {
		CONTACT_LAST_NAME = cONTACTLASTNAME;
	}
	public String getCONTACT_DISPLAY_NUMBER() {
		return CONTACT_DISPLAY_NUMBER;
	}
	public void setCONTACT_DISPLAY_NUMBER(String cONTACTDISPLAYNUMBER) {
		CONTACT_DISPLAY_NUMBER = cONTACTDISPLAYNUMBER;
	}
}
