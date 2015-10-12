package com.nrg.lau.beans;

import java.io.Serializable;

public class LauReportActivities implements Serializable{

	private static final long serialVersionUID = 1L;

	private long ACTIVITY_ID = 0;
	private long REPORT_ID = 0;
	private long DISPLAY_NUMBER = 0;
	private long ATTACHMENT_ID = 0;
	private long CONTACT_ID = 0;
	private String DIRECTION = "";
	private String ATTACHMENTS = "";
	private String CORPORATE_RECEIVED_DATE = "";
	private String LOCAL_RECEIVED_DATE = "";
	private String PARTNER_RECEIVED_DATE = "";
	private String ACTIVITY_ACTION_DATE = "";
	private String ACTIVITY_DATE = "";
	private String ACTIVITY_TYPE = "";
	private String ACTIVITY_REASON = "";
	private String ACTIVITY_DESCRIPTION = "";
	private String PRIORITY = "";
	private String ASSIGNED_TO_USER = "";
	private String ASSIGNED_TO_GROUP = "";
	private String DUE_DATE = "";
	private String ACTIVITY_STATUS = "";
	private String COMPLETION_DATE = "";
	private String UPDATE_USER_ID = "";
	private String LAU_REPORT_ID = "";
	private Object UPDATE_TIMESTAMP;
	private long transactionType = -1;
	private  long attachmentTransactionType = -1;
	private String PROMOTE_ACTIVITY = "";
	private String CUSTOM_COMMENT_01 = "";
	private String CUSTOM_COMMENT_02 = "";
	
	public long getACTIVITY_ID() {
		return ACTIVITY_ID;
	}
	public void setACTIVITY_ID(long aCTIVITYID) {
		ACTIVITY_ID = aCTIVITYID;
	}
	public long getREPORT_ID() {
		return REPORT_ID;
	}
	public void setREPORT_ID(long rEPORTID) {
		REPORT_ID = rEPORTID;
	}
	public long getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}
	public void setDISPLAY_NUMBER(long dISPLAYNUMBER) {
		DISPLAY_NUMBER = dISPLAYNUMBER;
	}
	public String getACTIVITY_ACTION_DATE() {
		return ACTIVITY_ACTION_DATE;
	}
	public void setACTIVITY_ACTION_DATE(String aCTIVITY_ACTION_DATE) {
		ACTIVITY_ACTION_DATE = aCTIVITY_ACTION_DATE;
	}
	public long getATTACHMENT_ID() {
		return ATTACHMENT_ID;
	}
	public void setATTACHMENT_ID(long aTTACHMENTID) {
		ATTACHMENT_ID = aTTACHMENTID;
	}
	public long getCONTACT_ID() {
		return CONTACT_ID;
	}
	public void setCONTACT_ID(long cONTACTID) {
		CONTACT_ID = cONTACTID;
	}
	public String getDIRECTION() {
		return DIRECTION;
	}
	public void setDIRECTION(String dIRECTION) {
		DIRECTION = dIRECTION;
	}
	public String getCORPORATE_RECEIVED_DATE() {
		return CORPORATE_RECEIVED_DATE;
	}
	public void setCORPORATE_RECEIVED_DATE(String cORPORATERECEIVEDDATE) {
		CORPORATE_RECEIVED_DATE = cORPORATERECEIVEDDATE;
	}
	public String getLOCAL_RECEIVED_DATE() {
		return LOCAL_RECEIVED_DATE;
	}
	public void setLOCAL_RECEIVED_DATE(String lOCALRECEIVEDDATE) {
		LOCAL_RECEIVED_DATE = lOCALRECEIVEDDATE;
	}
	public String getPARTNER_RECEIVED_DATE() {
		return PARTNER_RECEIVED_DATE;
	}
	public void setPARTNER_RECEIVED_DATE(String pARTNERRECEIVEDDATE) {
		PARTNER_RECEIVED_DATE = pARTNERRECEIVEDDATE;
	}
	public String getACTIVITY_DATE() {
		return ACTIVITY_DATE;
	}
	public void setACTIVITY_DATE(String aCTIVITYDATE) {
		ACTIVITY_DATE = aCTIVITYDATE;
	}
	public String getACTIVITY_TYPE() {
		return ACTIVITY_TYPE;
	}
	public void setACTIVITY_TYPE(String aCTIVITYTYPE) {
		ACTIVITY_TYPE = aCTIVITYTYPE;
	}
	public String getACTIVITY_REASON() {
		return ACTIVITY_REASON;
	}
	public void setACTIVITY_REASON(String aCTIVITYREASON) {
		ACTIVITY_REASON = aCTIVITYREASON;
	}
	public String getACTIVITY_DESCRIPTION() {
		return ACTIVITY_DESCRIPTION;
	}
	public void setACTIVITY_DESCRIPTION(String aCTIVITYDESCRIPTION) {
		ACTIVITY_DESCRIPTION = aCTIVITYDESCRIPTION;
	}
	public String getPRIORITY() {
		return PRIORITY;
	}
	public void setPRIORITY(String pRIORITY) {
		PRIORITY = pRIORITY;
	}
	public String getPROMOTE_ACTIVITY() {
		return PROMOTE_ACTIVITY;
	}
	public void setPROMOTE_ACTIVITY(String pROMOTE_ACTIVITY) {
		PROMOTE_ACTIVITY = pROMOTE_ACTIVITY;
	}
	public String getASSIGNED_TO_USER() {
		return ASSIGNED_TO_USER;
	}
	public void setASSIGNED_TO_USER(String aSSIGNEDTOUSER) {
		ASSIGNED_TO_USER = aSSIGNEDTOUSER;
	}
	public String getASSIGNED_TO_GROUP() {
		return ASSIGNED_TO_GROUP;
	}
	public void setASSIGNED_TO_GROUP(String aSSIGNEDTOGROUP) {
		ASSIGNED_TO_GROUP = aSSIGNEDTOGROUP;
	}
	public String getDUE_DATE() {
		return DUE_DATE;
	}
	public void setDUE_DATE(String dUEDATE) {
		DUE_DATE = dUEDATE;
	}
	public String getACTIVITY_STATUS() {
		return ACTIVITY_STATUS;
	}
	public void setACTIVITY_STATUS(String aCTIVITYSTATUS) {
		ACTIVITY_STATUS = aCTIVITYSTATUS;
	}
	public String getCOMPLETION_DATE() {
		return COMPLETION_DATE;
	}
	public void setCOMPLETION_DATE(String cOMPLETIONDATE) {
		COMPLETION_DATE = cOMPLETIONDATE;
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

	public String getATTACHMENTS() {
		return ATTACHMENTS;
	}
	public void setATTACHMENTS(String aTTACHMENTS) {
		ATTACHMENTS = aTTACHMENTS;
	}
	public String toString() {
		StringBuffer str = new StringBuffer(LauReportActivities.class.getName());
		str.append("[");
		str.append(", ACTIVITY_ID=");
		str.append(ACTIVITY_ID);
		str.append(", REPORT_ID=");
		str.append(REPORT_ID);
		str.append(", DISPLAY_NUMBER=");
		str.append(DISPLAY_NUMBER);
		str.append(", ATTACHMENT_ID=");
		str.append(ATTACHMENT_ID);
		str.append(", CONTACT_ID=");
		str.append(CONTACT_ID);
		str.append(", DIRECTION=");
		str.append(DIRECTION);
		str.append(", CORPORATE_RECEIVED_DATE=");
		str.append(CORPORATE_RECEIVED_DATE);
		str.append(", LOCAL_RECEIVED_DATE=");
		str.append(LOCAL_RECEIVED_DATE);
		str.append(", PARTNER_RECEIVED_DATE=");
		str.append(PARTNER_RECEIVED_DATE);
		str.append(", ACTIVITY_DATE=");
		str.append(ACTIVITY_DATE);
		str.append(", ACTIVITY_TYPE=");
		str.append(ACTIVITY_TYPE);
		str.append(", ACTIVITY_REASON=");
		str.append(ACTIVITY_REASON);
		str.append(", ACTIVITY_DESCRIPTION=");
		str.append(ACTIVITY_DESCRIPTION);
		str.append(", PRIORITY=");
		str.append(PRIORITY);
		str.append(", ASSIGNED_TO_USER=");
		str.append(ASSIGNED_TO_USER);
		str.append(", ASSIGNED_TO_GROUP=");
		str.append(ASSIGNED_TO_GROUP);
		str.append(", DUE_DATE=");
		str.append(DUE_DATE);
		str.append(", ACTIVITY_STATUS=");
		str.append(ACTIVITY_STATUS);
		str.append(", COMPLETION_DATE=");
		str.append(COMPLETION_DATE);
		str.append(", ACTIVITY_ACTION_DATE=");
		str.append(ACTIVITY_ACTION_DATE);
		str.append(", UPDATE_USER_ID=");
		str.append(UPDATE_USER_ID);
		str.append(", UPDATE_TIMESTAMP=");
		str.append(UPDATE_TIMESTAMP);
		str.append(", ATTACHMENTS=");
		str.append(ATTACHMENTS);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", LAU_REPORT_ID=");
		str.append(LAU_REPORT_ID);
		str.append(", CUSTOM_COMMENT_01=");
		str.append(CUSTOM_COMMENT_01);
		str.append(", CUSTOM_COMMENT_02=");
		str.append(CUSTOM_COMMENT_02);
		str.append("]");
		return str.toString();
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
	public long getTransactionType() {
		return transactionType;
	}
	public long getAttachmentTransactionType() {
		return attachmentTransactionType;
	}
	public void setAttachmentTransactionType(long attachmentTransactionType) {
		this.attachmentTransactionType = attachmentTransactionType;
	}
	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	public String getLAU_REPORT_ID() {
		return LAU_REPORT_ID;
	}
	public void setLAU_REPORT_ID(String lAUREPORTID) {
		LAU_REPORT_ID = lAUREPORTID;
	}

}
