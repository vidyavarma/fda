package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauEvaluationCode implements Serializable {
	
	private static Logger log	= Logger.getLogger(LauEvaluationCode.class);
	private static final long serialVersionUID = 1L;
	
	private long evalCodeID;
	private String evalCodeType = "";
	private String codeSource = "";
	private String evalCode = "";
	private long reportId = 0;	
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	private String INTERNAL_LINK_ID="";
	
	public LauEvaluationCode(){
		super();
	}
	
	
	

	public long getEvalCodeID() {
		return evalCodeID;
	}

	public long getReportId() {
		return reportId;
	}




	public void setReportId(long reportId) {
		this.reportId = reportId;
	}



	public void setEvalCodeID(long evalCodeID) {
		this.evalCodeID = evalCodeID;
	}




	public String getEvalCodeType() {
		return evalCodeType;
	}




	public void setEvalCodeType(String evalCodeType) {
		this.evalCodeType = evalCodeType;
	}




	public String getCodeSource() {
		return codeSource;
	}




	public void setCodeSource(String codeSource) {
		this.codeSource = codeSource;
	}




	public String getEvalCode() {
		return evalCode;
	}




	public void setEvalCode(String evalCode) {
		this.evalCode = evalCode;
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




	public String toString(){
		StringBuffer str = new StringBuffer(LauEvaluationCode.class.getName());
		
		str.append("[");
		str.append("evalCodeID=");
		str.append(evalCodeID);
		str.append(", evalCodeType=");
		str.append(evalCodeType);
		str.append(",codeSource=");
		str.append(codeSource);
		str.append(", evalCode=");
		str.append(evalCode);
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
}
	
