package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauPatientProbCode implements Serializable {
	
	private static Logger log	= Logger.getLogger(LauPatientProbCode.class);
	private static final long serialVersionUID = 1L;
	
	private long patientProbCodeID = 0;
	private String patientDetailsID = "";
	private long displayNumber = 0;
	private String codeSource = "";
	private String probCodeType = "";
	private String probCodeValue = "";
	private long reportId = 0;	
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	private String INTERNAL_LINK_ID="";
	
	public LauPatientProbCode(){
		super();
	}
	public String toString(){
		StringBuffer str = new StringBuffer(LauPatientProbCode.class.getName());
		
		str.append("[");
		str.append("patientProbCodeID=");
		str.append(patientProbCodeID);
		str.append(", patientDetailsID=");
		str.append(patientDetailsID);
		str.append(",displayNumber=");
		str.append(displayNumber);
		str.append(", codeSource=");
		str.append(codeSource);
		str.append(", probCodeType=");
		str.append(probCodeType);
		str.append(", probCodeValue=");
		str.append(probCodeValue);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append("]");
		return str.toString();
	}
	public long getPatientProbCodeID() {
		return patientProbCodeID;
	}
	public void setPatientProbCodeID(long patientProbCodeID) {
		this.patientProbCodeID = patientProbCodeID;
	}
	public String getPatientDetailsID() {
		return patientDetailsID;
	}
	public void setPatientDetailsID(String patientDetailsID) {
		this.patientDetailsID = patientDetailsID;
	}
	public long getDisplayNumber() {
		return displayNumber;
	}
	public void setDisplayNumber(long displayNumber) {
		this.displayNumber = displayNumber;
	}
	public String getCodeSource() {
		return codeSource;
	}
	public void setCodeSource(String codeSource) {
		this.codeSource = codeSource;
	}
	public String getProbCodeType() {
		return probCodeType;
	}
	public void setProbCodeType(String probCodeType) {
		this.probCodeType = probCodeType;
	}
	public String getProbCodeValue() {
		return probCodeValue;
	}
	public void setProbCodeValue(String probCodeValue) {
		this.probCodeValue = probCodeValue;
	}
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Object getUpdateTimeStamp() {
		return updateTimeStamp;
	}
	public void setUpdateTimeStamp(Object updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
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
	
