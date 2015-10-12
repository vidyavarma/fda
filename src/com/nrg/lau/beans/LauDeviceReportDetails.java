package com.nrg.lau.beans;

import java.io.Serializable;

public class LauDeviceReportDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long DEVICE_REPORT_ID = 0;
	private long REPORT_ID = 0;
	private String EVENT_LOCATION = "";
	private String EVENT_LOCATION_OTHER = "";
	private String REPORT_SENT_TO_FDA = "";
	private String REPORT_SENT_TO_FDA_DATE = "";
	private String REPORT_SENT_TO_MFG = "";
	private String REPORT_SENT_TO_MFG_DATE = "";
	private String REPORT_RECIPIENT_CODE = "";
	private String REPORT_SENT_TO_USER_FACILITY = "";
	private String REPORT_SENT_TO_DIST = "";
	private String DISTRIBUTOR_AWARE_DATE = "";
	private String DISTRIBUTOR_REPORT_NUMBER = "";
	private String DISTRIBUTOR_REPORT_TYPE = "";
	private String DISTRIBUTOR_FOLLOWUP_NO = "";
	private String DISTRIBUTOR_REPORT_DATE = "";
	private String DEV_REPORT_TYPE_DEATH = "";
	private String DEV_REPORT_TYPE_INJURY = "";
	private String DEV_REPORT_TYPE_MALFUNCTION = "";
	private String DEV_REPORT_TYPE_OTHER = "";
	private String DEV_REPORT_TYPE_OTHER_SPECIFY = "";
	private String NUMBER_EXPOSED = "";
	private String NUMBER_ADVERSELY_AFFECTED = "";
	private String NUMBER_INVOLVED_UNEXPOSED = "";
	private String NUMBER_POTENTIALLY_EXPOSED = "";
	private String AFFECTED_EMPLOYEE_FLAG = "";
	private String AFFECTED_OPERATOR_FLAG = "";
	private String CUSTOM_TEXT_01 = "";
	private String CUSTOM_TEXT_02 = "";
	private String CUSTOM_TEXT_03 = "";
	private String CUSTOM_TEXT_04 = "";
	private String CUSTOM_TEXT_05 = "";
	private String CUSTOM_TEXT_06 = "";
	private String CUSTOM_TEXT_07 = "";
	private String CUSTOM_TEXT_08 = "";
	private String CUSTOM_TEXT_09 = "";
	private String CUSTOM_TEXT_10 = "";
	private String CUSTOM_DATE_01 = "";
	private String CUSTOM_DATE_02 = "";
	private String CUSTOM_DATE_03 = "";
	private String CUSTOM_DATE_04 = "";
	private String CUSTOM_DATE_05 = "";
	private String CUSTOM_COMMENT_01 = "";
	private String CUSTOM_COMMENT_02 = "";
	private String UPDATE_USER_ID = "";
	private Object UPDATE_TIMESTAMP;
	private long transactionType = -1;
	private String INTERNAL_LINK_ID="";
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauReports.class.getName());
		str.append("[");
		str.append("DEVICE_REPORT_ID=");
		str.append(DEVICE_REPORT_ID);
		str.append(", REPORT_ID=");
		str.append(REPORT_ID);
		str.append(", EVENT_LOCATION=");
		str.append(EVENT_LOCATION);
		str.append(", EVENT_LOCATION_OTHER=");
		str.append(EVENT_LOCATION_OTHER);
		str.append(", REPORT_SENT_TO_FDA=");
		str.append(REPORT_SENT_TO_FDA);
		str.append(", REPORT_SENT_TO_FDA_DATE=");
		str.append(REPORT_SENT_TO_FDA_DATE);
		str.append(", REPORT_SENT_TO_MFG=");
		str.append(REPORT_SENT_TO_MFG);
		str.append(", REPORT_SENT_TO_MFG_DATE=");
		str.append(REPORT_SENT_TO_MFG_DATE);
		str.append(", REPORT_RECIPIENT_CODE=");
		str.append(REPORT_RECIPIENT_CODE);
		str.append(", REPORT_SENT_TO_USER_FACILITY=");
		str.append(REPORT_SENT_TO_USER_FACILITY);
		str.append(", REPORT_SENT_TO_DIST=");
		str.append(REPORT_SENT_TO_DIST);
		str.append(", DISTRIBUTOR_AWARE_DATE=");
		str.append(DISTRIBUTOR_AWARE_DATE);
		str.append(", DISTRIBUTOR_REPORT_NUMBER=");
		str.append(DISTRIBUTOR_REPORT_NUMBER);
		str.append(", DISTRIBUTOR_REPORT_TYPE=");
		str.append(DISTRIBUTOR_REPORT_TYPE);
		str.append(", DISTRIBUTOR_FOLLOWUP_NO=");
		str.append(DISTRIBUTOR_FOLLOWUP_NO);
		str.append(", DISTRIBUTOR_REPORT_DATE=");
		str.append(DISTRIBUTOR_REPORT_DATE);
		str.append(", DEV_REPORT_TYPE_DEATH=");
		str.append(DEV_REPORT_TYPE_DEATH);
		str.append(DEV_REPORT_TYPE_INJURY);
		str.append(", DEV_REPORT_TYPE_INJURY=");
		str.append(DEV_REPORT_TYPE_MALFUNCTION);
		str.append(", DEV_REPORT_TYPE_MALFUNCTION=");
		str.append(DEV_REPORT_TYPE_OTHER);
		str.append(", DEV_REPORT_TYPE_OTHER=");
		str.append(DEV_REPORT_TYPE_OTHER_SPECIFY);
		str.append(", DEV_REPORT_TYPE_OTHER_SPECIFY=");
		str.append(", NUMBER_EXPOSED=");
		str.append(NUMBER_EXPOSED);
		str.append(", NUMBER_ADVERSELY_AFFECTED=");
		str.append(NUMBER_ADVERSELY_AFFECTED);
		str.append(", NUMBER_INVOLVED_UNEXPOSED=");
		str.append(NUMBER_INVOLVED_UNEXPOSED);
		str.append(", NUMBER_POTENTIALLY_EXPOSED=");
		str.append(NUMBER_POTENTIALLY_EXPOSED);
		str.append(", AFFECTED_EMPLOYEE_FLAG=");
		str.append(AFFECTED_EMPLOYEE_FLAG);
		str.append(", AFFECTED_OPERATOR_FLAG=");
		str.append(AFFECTED_OPERATOR_FLAG);
		str.append(CUSTOM_TEXT_01);
		str.append(", CUSTOM_TEXT_01=");
		str.append(CUSTOM_TEXT_02);
		str.append(", CUSTOM_TEXT_02=");
		str.append(CUSTOM_TEXT_03);
		str.append(", CUSTOM_TEXT_03=");
		str.append(CUSTOM_TEXT_04);
		str.append(", CUSTOM_TEXT_04=");
		str.append(CUSTOM_TEXT_05);
		str.append(", CUSTOM_TEXT_05=");
		str.append(CUSTOM_TEXT_06);
		str.append(", CUSTOM_TEXT_06=");
		str.append(CUSTOM_TEXT_07);
		str.append(", CUSTOM_TEXT_07=");
		str.append(CUSTOM_TEXT_08);
		str.append(", CUSTOM_TEXT_08=");
		str.append(CUSTOM_TEXT_09);
		str.append(", CUSTOM_TEXT_09=");
		str.append(CUSTOM_TEXT_10);
		str.append(", CUSTOM_TEXT_10=");
		str.append(CUSTOM_DATE_01);
		str.append(", CUSTOM_DATE_01=");
		str.append(CUSTOM_DATE_02);
		str.append(", CUSTOM_DATE_02=");
		str.append(CUSTOM_DATE_03);
		str.append(", CUSTOM_DATE_03=");
		str.append(CUSTOM_DATE_04);
		str.append(", CUSTOM_DATE_04=");
		str.append(CUSTOM_DATE_05);
		str.append(", CUSTOM_DATE_05=");
		str.append(CUSTOM_COMMENT_01);
		str.append(", CUSTOM_COMMENT_01=");
		str.append(CUSTOM_COMMENT_02);
		str.append(", CUSTOM_COMMENT_02=");
		str.append(", UPDATE_USER_ID=");
		str.append(UPDATE_USER_ID);
		str.append(", UPDATE_TIMESTAMP=");
		str.append(UPDATE_TIMESTAMP);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append("]");
		return str.toString();
	}
	
	public long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	public long getDEVICE_REPORT_ID() {
		return DEVICE_REPORT_ID;
	}
	public void setDEVICE_REPORT_ID(long dEVICE_REPORT_ID) {
		DEVICE_REPORT_ID = dEVICE_REPORT_ID;
	}
	public long getREPORT_ID() {
		return REPORT_ID;
	}
	public void setREPORT_ID(long rEPORT_ID) {
		REPORT_ID = rEPORT_ID;
	}
	public String getEVENT_LOCATION() {
		return EVENT_LOCATION;
	}
	public void setEVENT_LOCATION(String eVENT_LOCATION) {
		EVENT_LOCATION = eVENT_LOCATION;
	}
	public String getEVENT_LOCATION_OTHER() {
		return EVENT_LOCATION_OTHER;
	}
	public void setEVENT_LOCATION_OTHER(String eVENT_LOCATION_OTHER) {
		EVENT_LOCATION_OTHER = eVENT_LOCATION_OTHER;
	}
	public String getREPORT_SENT_TO_FDA() {
		return REPORT_SENT_TO_FDA;
	}
	public void setREPORT_SENT_TO_FDA(String rEPORT_SENT_TO_FDA) {
		REPORT_SENT_TO_FDA = rEPORT_SENT_TO_FDA;
	}
	public String getREPORT_SENT_TO_FDA_DATE() {
		return REPORT_SENT_TO_FDA_DATE;
	}
	public void setREPORT_SENT_TO_FDA_DATE(String rEPORT_SENT_TO_FDA_DATE) {
		REPORT_SENT_TO_FDA_DATE = rEPORT_SENT_TO_FDA_DATE;
	}
	public String getREPORT_SENT_TO_MFG() {
		return REPORT_SENT_TO_MFG;
	}
	public void setREPORT_SENT_TO_MFG(String rEPORT_SENT_TO_MFG) {
		REPORT_SENT_TO_MFG = rEPORT_SENT_TO_MFG;
	}
	public String getREPORT_SENT_TO_MFG_DATE() {
		return REPORT_SENT_TO_MFG_DATE;
	}
	public void setREPORT_SENT_TO_MFG_DATE(String rEPORT_SENT_TO_MFG_DATE) {
		REPORT_SENT_TO_MFG_DATE = rEPORT_SENT_TO_MFG_DATE;
	}
	public String getREPORT_RECIPIENT_CODE() {
		return REPORT_RECIPIENT_CODE;
	}
	public void setREPORT_RECIPIENT_CODE(String rEPORT_RECIPIENT_CODE) {
		REPORT_RECIPIENT_CODE = rEPORT_RECIPIENT_CODE;
	}
	public String getREPORT_SENT_TO_USER_FACILITY() {
		return REPORT_SENT_TO_USER_FACILITY;
	}
	public void setREPORT_SENT_TO_USER_FACILITY(String rEPORT_SENT_TO_USER_FACILITY) {
		REPORT_SENT_TO_USER_FACILITY = rEPORT_SENT_TO_USER_FACILITY;
	}
	public String getREPORT_SENT_TO_DIST() {
		return REPORT_SENT_TO_DIST;
	}
	public void setREPORT_SENT_TO_DIST(String rEPORT_SENT_TO_DIST) {
		REPORT_SENT_TO_DIST = rEPORT_SENT_TO_DIST;
	}
	public String getDISTRIBUTOR_AWARE_DATE() {
		return DISTRIBUTOR_AWARE_DATE;
	}
	public void setDISTRIBUTOR_AWARE_DATE(String dISTRIBUTOR_AWARE_DATE) {
		DISTRIBUTOR_AWARE_DATE = dISTRIBUTOR_AWARE_DATE;
	}
	public String getDISTRIBUTOR_REPORT_NUMBER() {
		return DISTRIBUTOR_REPORT_NUMBER;
	}
	public void setDISTRIBUTOR_REPORT_NUMBER(String dISTRIBUTOR_REPORT_NUMBER) {
		DISTRIBUTOR_REPORT_NUMBER = dISTRIBUTOR_REPORT_NUMBER;
	}
	public String getDISTRIBUTOR_REPORT_TYPE() {
		return DISTRIBUTOR_REPORT_TYPE;
	}
	public void setDISTRIBUTOR_REPORT_TYPE(String dISTRIBUTOR_REPORT_TYPE) {
		DISTRIBUTOR_REPORT_TYPE = dISTRIBUTOR_REPORT_TYPE;
	}
	public String getDISTRIBUTOR_FOLLOWUP_NO() {
		return DISTRIBUTOR_FOLLOWUP_NO;
	}
	public void setDISTRIBUTOR_FOLLOWUP_NO(String dISTRIBUTOR_FOLLOWUP_NO) {
		DISTRIBUTOR_FOLLOWUP_NO = dISTRIBUTOR_FOLLOWUP_NO;
	}
	public String getDISTRIBUTOR_REPORT_DATE() {
		return DISTRIBUTOR_REPORT_DATE;
	}
	public void setDISTRIBUTOR_REPORT_DATE(String dISTRIBUTOR_REPORT_DATE) {
		DISTRIBUTOR_REPORT_DATE = dISTRIBUTOR_REPORT_DATE;
	}
	public String getDEV_REPORT_TYPE_DEATH() {
		return DEV_REPORT_TYPE_DEATH;
	}
	public void setDEV_REPORT_TYPE_DEATH(String dEV_REPORT_TYPE_DEATH) {
		DEV_REPORT_TYPE_DEATH = dEV_REPORT_TYPE_DEATH;
	}
	public String getDEV_REPORT_TYPE_INJURY() {
		return DEV_REPORT_TYPE_INJURY;
	}
	public void setDEV_REPORT_TYPE_INJURY(String dEV_REPORT_TYPE_INJURY) {
		DEV_REPORT_TYPE_INJURY = dEV_REPORT_TYPE_INJURY;
	}
	public String getDEV_REPORT_TYPE_MALFUNCTION() {
		return DEV_REPORT_TYPE_MALFUNCTION;
	}
	public void setDEV_REPORT_TYPE_MALFUNCTION(String dEV_REPORT_TYPE_MALFUNCTION) {
		DEV_REPORT_TYPE_MALFUNCTION = dEV_REPORT_TYPE_MALFUNCTION;
	}
	public String getDEV_REPORT_TYPE_OTHER() {
		return DEV_REPORT_TYPE_OTHER;
	}
	public void setDEV_REPORT_TYPE_OTHER(String dEV_REPORT_TYPE_OTHER) {
		DEV_REPORT_TYPE_OTHER = dEV_REPORT_TYPE_OTHER;
	}
	public String getDEV_REPORT_TYPE_OTHER_SPECIFY() {
		return DEV_REPORT_TYPE_OTHER_SPECIFY;
	}
	public void setDEV_REPORT_TYPE_OTHER_SPECIFY(
			String dEV_REPORT_TYPE_OTHER_SPECIFY) {
		DEV_REPORT_TYPE_OTHER_SPECIFY = dEV_REPORT_TYPE_OTHER_SPECIFY;
	}
	public String getNUMBER_EXPOSED() {
		return NUMBER_EXPOSED;
	}
	public void setNUMBER_EXPOSED(String nUMBER_EXPOSED) {
		NUMBER_EXPOSED = nUMBER_EXPOSED;
	}
	public String getNUMBER_ADVERSELY_AFFECTED() {
		return NUMBER_ADVERSELY_AFFECTED;
	}
	public void setNUMBER_ADVERSELY_AFFECTED(String nUMBER_ADVERSELY_AFFECTED) {
		NUMBER_ADVERSELY_AFFECTED = nUMBER_ADVERSELY_AFFECTED;
	}
	public String getNUMBER_INVOLVED_UNEXPOSED() {
		return NUMBER_INVOLVED_UNEXPOSED;
	}
	public void setNUMBER_INVOLVED_UNEXPOSED(String nUMBER_INVOLVED_UNEXPOSED) {
		NUMBER_INVOLVED_UNEXPOSED = nUMBER_INVOLVED_UNEXPOSED;
	}
	public String getNUMBER_POTENTIALLY_EXPOSED() {
		return NUMBER_POTENTIALLY_EXPOSED;
	}
	public void setNUMBER_POTENTIALLY_EXPOSED(String nUMBER_POTENTIALLY_EXPOSED) {
		NUMBER_POTENTIALLY_EXPOSED = nUMBER_POTENTIALLY_EXPOSED;
	}
	public String getAFFECTED_EMPLOYEE_FLAG() {
		return AFFECTED_EMPLOYEE_FLAG;
	}

	public void setAFFECTED_EMPLOYEE_FLAG(String aFFECTED_EMPLOYEE_FLAG) {
		AFFECTED_EMPLOYEE_FLAG = aFFECTED_EMPLOYEE_FLAG;
	}

	public String getAFFECTED_OPERATOR_FLAG() {
		return AFFECTED_OPERATOR_FLAG;
	}

	public void setAFFECTED_OPERATOR_FLAG(String aFFECTED_OPERATOR_FLAG) {
		AFFECTED_OPERATOR_FLAG = aFFECTED_OPERATOR_FLAG;
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
	public String getCUSTOM_TEXT_04() {
		return CUSTOM_TEXT_04;
	}
	public void setCUSTOM_TEXT_04(String cUSTOM_TEXT_04) {
		CUSTOM_TEXT_04 = cUSTOM_TEXT_04;
	}
	public String getCUSTOM_TEXT_05() {
		return CUSTOM_TEXT_05;
	}
	public void setCUSTOM_TEXT_05(String cUSTOM_TEXT_05) {
		CUSTOM_TEXT_05 = cUSTOM_TEXT_05;
	}
	public String getCUSTOM_TEXT_06() {
		return CUSTOM_TEXT_06;
	}
	public void setCUSTOM_TEXT_06(String cUSTOM_TEXT_06) {
		CUSTOM_TEXT_06 = cUSTOM_TEXT_06;
	}
	public String getCUSTOM_TEXT_07() {
		return CUSTOM_TEXT_07;
	}
	public void setCUSTOM_TEXT_07(String cUSTOM_TEXT_07) {
		CUSTOM_TEXT_07 = cUSTOM_TEXT_07;
	}
	public String getCUSTOM_TEXT_08() {
		return CUSTOM_TEXT_08;
	}
	public void setCUSTOM_TEXT_08(String cUSTOM_TEXT_08) {
		CUSTOM_TEXT_08 = cUSTOM_TEXT_08;
	}
	public String getCUSTOM_TEXT_09() {
		return CUSTOM_TEXT_09;
	}
	public void setCUSTOM_TEXT_09(String cUSTOM_TEXT_09) {
		CUSTOM_TEXT_09 = cUSTOM_TEXT_09;
	}
	public String getCUSTOM_TEXT_10() {
		return CUSTOM_TEXT_10;
	}
	public void setCUSTOM_TEXT_10(String cUSTOM_TEXT_10) {
		CUSTOM_TEXT_10 = cUSTOM_TEXT_10;
	}
	public String getCUSTOM_DATE_01() {
		return CUSTOM_DATE_01;
	}
	public void setCUSTOM_DATE_01(String cUSTOM_DATE_01) {
		CUSTOM_DATE_01 = cUSTOM_DATE_01;
	}
	public String getCUSTOM_DATE_02() {
		return CUSTOM_DATE_02;
	}
	public void setCUSTOM_DATE_02(String cUSTOM_DATE_02) {
		CUSTOM_DATE_02 = cUSTOM_DATE_02;
	}
	public String getCUSTOM_DATE_03() {
		return CUSTOM_DATE_03;
	}
	public void setCUSTOM_DATE_03(String cUSTOM_DATE_03) {
		CUSTOM_DATE_03 = cUSTOM_DATE_03;
	}
	public String getCUSTOM_DATE_04() {
		return CUSTOM_DATE_04;
	}
	public void setCUSTOM_DATE_04(String cUSTOM_DATE_04) {
		CUSTOM_DATE_04 = cUSTOM_DATE_04;
	}
	public String getCUSTOM_DATE_05() {
		return CUSTOM_DATE_05;
	}
	public void setCUSTOM_DATE_05(String cUSTOM_DATE_05) {
		CUSTOM_DATE_05 = cUSTOM_DATE_05;
	}
	public String getCUSTOM_COMMENT_01() {
		return CUSTOM_COMMENT_01;
	}
	public void setCUSTOM_COMMENT_01(String cUSTOM_COMMENT_01) {
		CUSTOM_COMMENT_01 = cUSTOM_COMMENT_01;
	}
	public String getCUSTOM_COMMENT_02() {
		return CUSTOM_COMMENT_02;
	}
	public void setCUSTOM_COMMENT_02(String cUSTOM_COMMENT_02) {
		CUSTOM_COMMENT_02 = cUSTOM_COMMENT_02;
	}
	public String getUPDATE_USER_ID() {
		return UPDATE_USER_ID;
	}
	public void setUPDATE_USER_ID(String uPDATE_USER_ID) {
		UPDATE_USER_ID = uPDATE_USER_ID;
	}
	public Object getUPDATE_TIMESTAMP() {
		return UPDATE_TIMESTAMP;
	}
	public void setUPDATE_TIMESTAMP(Object uPDATE_TIMESTAMP) {
		UPDATE_TIMESTAMP = uPDATE_TIMESTAMP;
	}
	public String getINTERNAL_LINK_ID() {
		return INTERNAL_LINK_ID;
	}
	public void setINTERNAL_LINK_ID(String iNTERNAL_LINK_ID) {
		INTERNAL_LINK_ID = iNTERNAL_LINK_ID;
	}	
	

}
