package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauRemedialAction implements Serializable {
	
	private static Logger log	= Logger.getLogger(LauRemedialAction.class);
	private static final long serialVersionUID = 1L;
	
	private long remedialActionID;
	private String remedialActionType = "";
	private String remedialActionTypeDesc = "";
	private String otherActionDesc = "";
	private long reportId = 0;	
	private long displayNum = 0;	
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	private String INTERNAL_LINK_ID="";
	private String mxInternalID = "";
	
	public LauRemedialAction(){
		super();
	}
	
	

	public long getReportId() {
		return reportId;
	}


	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	
	public long getRemedialActionID() {
		return remedialActionID;
	}



	public void setRemedialActionID(long remedialActionID) {
		this.remedialActionID = remedialActionID;
	}



	public String getRemedialActionType() {
		return remedialActionType;
	}



	public void setRemedialActionType(String remedialActionType) {
		this.remedialActionType = remedialActionType;
	}



	public String getRemedialActionTypeDesc() {
		return remedialActionTypeDesc;
	}



	public void setRemedialActionTypeDesc(String remedialActionTypeDesc) {
		this.remedialActionTypeDesc = remedialActionTypeDesc;
	}



	public String getOtherActionDesc() {
		return otherActionDesc;
	}



	public void setOtherActionDesc(String otherActionDesc) {
		this.otherActionDesc = otherActionDesc;
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




	public long getDisplayNum() {
		return displayNum;
	}



	public void setDisplayNum(long displayNum) {
		this.displayNum = displayNum;
	}



	public String getMxInternalID() {
		return mxInternalID;
	}



	public void setMxInternalID(String mxInternalID) {
		this.mxInternalID = mxInternalID;
	}



	public String toString(){
		StringBuffer str = new StringBuffer(LauRemedialAction.class.getName());
		
		str.append("[");
		str.append("remedialActionID=");
		str.append(remedialActionID);
		str.append(", remedialActionType=");
		str.append(remedialActionType);
		str.append(",remedialActionTypeDesc=");
		str.append(remedialActionTypeDesc);
		str.append(", otherActionDesc=");
		str.append(otherActionDesc);
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
	
