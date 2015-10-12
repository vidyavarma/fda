package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauRunAdhocQuery implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger log	= Logger.getLogger(LauRunAdhocQuery.class);
	
	private String GROUP_ID = "";
	private String GROUP_NAME = "";
	private String LEVEL = "";
	private String OPERATOR = "";
	private String TYPE = "";
	private String DB_FIELD_NAME = "";
	private String DISPLAY_NUMBER = "";
	private String FIELD_ID = "";
	private String SELECTED = "";
	private String VALUE = "";
	private String VALUE2 = "";
	private String PARENTGROUP= "";
	
	public String getGROUP_ID() {
		return GROUP_ID;
	}
	public void setGROUP_ID(String gROUP_ID) {
		GROUP_ID = gROUP_ID;
	}
	public String getGROUP_NAME() {
		return GROUP_NAME;
	}
	public void setGROUP_NAME(String gROUP_NAME) {
		GROUP_NAME = gROUP_NAME;
	}
	public String getLEVEL() {
		return LEVEL;
	}
	public void setLEVEL(String lEVEL) {
		LEVEL = lEVEL;
	}
	public String getOPERATOR() {
		return OPERATOR;
	}
	public void setOPERATOR(String oPERATOR) {
		OPERATOR = oPERATOR;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getDB_FIELD_NAME() {
		return DB_FIELD_NAME;
	}
	public void setDB_FIELD_NAME(String dB_FIELD_NAME) {
		DB_FIELD_NAME = dB_FIELD_NAME;
	}
	public String getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}
	public void setDISPLAY_NUMBER(String dISPLAY_NUMBER) {
		DISPLAY_NUMBER = dISPLAY_NUMBER;
	}
	public String getFIELD_ID() {
		return FIELD_ID;
	}
	public void setFIELD_ID(String fIELD_ID) {
		FIELD_ID = fIELD_ID;
	}
	public String getSELECTED() {
		return SELECTED;
	}
	public void setSELECTED(String sELECTED) {
		SELECTED = sELECTED;
	}
	public String getVALUE() {
		return VALUE;
	}
	public void setVALUE(String vALUE) {
		VALUE = vALUE;
	}
	public String getVALUE2() {
		return VALUE2;
	}
	public void setVALUE2(String vALUE2) {
		VALUE2 = vALUE2;
	}
	public String getPARENTGROUP() {
		return PARENTGROUP;
	}
	public void setPARENTGROUP(String pARENTGROUP) {
		PARENTGROUP = pARENTGROUP;
	}

}
