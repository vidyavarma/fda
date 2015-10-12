package com.nrg.lau.beans;

import java.io.Serializable;

public class LauNarrativeText implements Serializable {

	private long narrativeTextId = 0;
	private String patientDetailsId = "";
	private String narrativeTextType = "";
	private String textUpdatedFlag = "";
	private String narrativeText = "";
	private String langCode = "";
	private String prodID = "";
	private String contentFlag = "";
	private String updateUserId = "";
	private long logicalRecordId = 0;	
	private Object updateTimeStamp;
	private long reportId = 0;
	private long transactionType = -1;
	private String INTERNAL_LINK_ID="";
	private String PATIENT_INTERNAL_LINK_ID="";
	private String PRODUCT_INTERNAL_LINK_ID="";
	private String DISPLAY_NUMBER="";

	private static final long serialVersionUID = 1L;

	public long getNarrativeTextId() {
		return this.narrativeTextId;
	}

	public void setNarrativeTextId(long narrativeTextId) {
		this.narrativeTextId = narrativeTextId;
	}
	
	public String getPatientDetailsId() {
		return patientDetailsId;
	}

	public void setPatientDetailsId(String patientDetailsId) {
		this.patientDetailsId = patientDetailsId;
	}

	
	public String getNarrativeTextType() {
		return this.narrativeTextType;
	}

	public void setNarrativeTextType(String narrativeTextType) {
		this.narrativeTextType = narrativeTextType;
	}

	public String getTextUpdatedFlag() {
		return this.textUpdatedFlag;
	}

	public void setTextUpdatedFlag(String textUpdatedFlag) {
		this.textUpdatedFlag = textUpdatedFlag;
	}

	public String getNarrativeText() {
		return this.narrativeText;
	}

	public void setNarrativeText(String narrativeText) {
		this.narrativeText = narrativeText;
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
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauNarrativeText.class.getName());
		str.append("[");
		str.append("narrativeTextId=");
		str.append(narrativeTextId);
		//str.append("patientDetailsId=");
		str.append(", patientDetailsId=");
		str.append(patientDetailsId);
		str.append(", narrativeTextType=");
		str.append(narrativeTextType);
		str.append(", textUpdatedFlag=");
		str.append(textUpdatedFlag);
		str.append(", narrativeText=");
		str.append(narrativeText);
		str.append(", langCode=");
		str.append(langCode);
		str.append(", prodID=");
		str.append(prodID);
		str.append(", contentFlag=");
		str.append(contentFlag);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", INTERNAL_LINK_ID=");
		str.append(INTERNAL_LINK_ID);
		str.append(", DISPLAY_NUMBER=");
		str.append(DISPLAY_NUMBER);
		str.append("]");
		return str.toString();
	}

	public String getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}

	public void setDISPLAY_NUMBER(String dISPLAY_NUMBER) {
		DISPLAY_NUMBER = dISPLAY_NUMBER;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getProdID() {
		return prodID;
	}

	public void setProdID(String prodID) {
		this.prodID = prodID;
	}

	public String getContentFlag() {
		return contentFlag;
	}

	public void setContentFlag(String contentFlag) {
		this.contentFlag = contentFlag;
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

	public long getLogicalRecordId() {
		return logicalRecordId;
	}

	public void setLogicalRecordId(long logicalRecordId) {
		this.logicalRecordId = logicalRecordId;
	}

	public String getPATIENT_INTERNAL_LINK_ID() {
		return PATIENT_INTERNAL_LINK_ID;
	}

	public void setPATIENT_INTERNAL_LINK_ID(String pATIENT_INTERNAL_LINK_ID) {
		PATIENT_INTERNAL_LINK_ID = pATIENT_INTERNAL_LINK_ID;
	}

	public String getPRODUCT_INTERNAL_LINK_ID() {
		return PRODUCT_INTERNAL_LINK_ID;
	}

	public void setPRODUCT_INTERNAL_LINK_ID(String pRODUCT_INTERNAL_LINK_ID) {
		PRODUCT_INTERNAL_LINK_ID = pRODUCT_INTERNAL_LINK_ID;
	}

	
	
	

}
