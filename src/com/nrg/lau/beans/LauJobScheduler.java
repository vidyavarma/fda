package com.nrg.lau.beans;

import java.io.Serializable;

public class LauJobScheduler implements Serializable{

	private static final long serialVersionUID = 1L;
	private String JOB_ID = "";
	private String JOB_NAME = "";
	private String THREAD_ID = "";	
	private String STATUS = "";
	private String JOB_DESCRIPTION = "";
	private String UPDATE_USER_ID = "";
	private String JOB_COMMENT = "";
	private Object END_TIME;
	private Object START_TIME;
	private Object UPDATE_TIMESTAMP;
	
	// for handling output from LAU_E2B_EXCHANGE_HISTORY
	private String XML_CONTENT_CLOB;
	private String E2B_MESSAGE_TYPE;
	private String E2B_XML_FILE_NAME;
	private String E2B_PARTNER_ID;
	private String E2B_MESSAGENUMB;
	private long E2B_EXCHANGE_ID;
	
	public String getXML_CONTENT_CLOB() {
		return XML_CONTENT_CLOB;
	}
	public void setXML_CONTENT_CLOB(String xML_CONTENT_CLOB) {
		XML_CONTENT_CLOB = xML_CONTENT_CLOB;
	}
	public String getE2B_MESSAGE_TYPE() {
		return E2B_MESSAGE_TYPE;
	}
	public void setE2B_MESSAGE_TYPE(String e2b_MESSAGE_TYPE) {
		E2B_MESSAGE_TYPE = e2b_MESSAGE_TYPE;
	}
	public String getE2B_XML_FILE_NAME() {
		return E2B_XML_FILE_NAME;
	}
	public void setE2B_XML_FILE_NAME(String e2b_XML_FILE_NAME) {
		E2B_XML_FILE_NAME = e2b_XML_FILE_NAME;
	}
	public long getE2B_EXCHANGE_ID() {
		return E2B_EXCHANGE_ID;
	}
	public void setE2B_EXCHANGE_ID(long e2b_EXCHANGE_ID) {
		E2B_EXCHANGE_ID = e2b_EXCHANGE_ID;
	}
	public String getTHREAD_ID() {
		return THREAD_ID;
	}
	public void setTHREAD_ID(String tHREAD_ID) {
		THREAD_ID = tHREAD_ID;
	}	
	public String getJOB_ID() {
		return JOB_ID;
	}
	public void setJOB_ID(String jOB_ID) {
		JOB_ID = jOB_ID;
	}
	public String getJOB_NAME() {
		return JOB_NAME;
	}
	public void setJOB_NAME(String jOB_NAME) {
		JOB_NAME = jOB_NAME;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getJOB_DESCRIPTION() {
		return JOB_DESCRIPTION;
	}
	public void setJOB_DESCRIPTION(String jOB_DESCRIPTION) {
		JOB_DESCRIPTION = jOB_DESCRIPTION;
	}
	public String getUPDATE_USER_ID() {
		return UPDATE_USER_ID;
	}
	public void setUPDATE_USER_ID(String uPDATE_USER_ID) {
		UPDATE_USER_ID = uPDATE_USER_ID;
	}
	public String getJOB_COMMENT() {
		return JOB_COMMENT;
	}
	public void setJOB_COMMENT(String jOB_COMMENT) {
		JOB_COMMENT = jOB_COMMENT;
	}
	public Object getEND_TIME() {
		return END_TIME;
	}
	public void setEND_TIME(Object eND_TIME) {
		END_TIME = eND_TIME;
	}
	public Object getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(Object sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	public Object getUPDATE_TIMESTAMP() {
		return UPDATE_TIMESTAMP;
	}
	public void setUPDATE_TIMESTAMP(Object uPDATE_TIMESTAMP) {
		UPDATE_TIMESTAMP = uPDATE_TIMESTAMP;
	}
	public String getE2B_PARTNER_ID() {
		return E2B_PARTNER_ID;
	}
	public void setE2B_PARTNER_ID(String e2B_PARTNER_ID) {
		E2B_PARTNER_ID = e2B_PARTNER_ID;
	}
	public String getE2B_MESSAGENUMB() {
		return E2B_MESSAGENUMB;
	}
	public void setE2B_MESSAGENUMB(String e2B_MESSAGENUMB) {
		E2B_MESSAGENUMB = e2B_MESSAGENUMB;
	}
	
	
	
}
