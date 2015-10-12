package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauReportSource implements Serializable {
	
	private static Logger log	= Logger.getLogger(LauReportSource.class);
	private static final long serialVersionUID = 1L;
	
	private long repSourceID;
	private String repSourceType = "";
	private String otherSourceDesc = "";
	private long reportId = 0;	
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	private String INTERNAL_LINK_ID="";
	
	public LauReportSource(){
		super();
	}
	
	
	public long getRepSourceID() {
		return repSourceID;
	}


	public void setRepSourceID(long repSourceID) {
		this.repSourceID = repSourceID;
	}


	public String getRepSourceType() {
		return repSourceType;
	}






	public void setRepSourceType(String repSourceType) {
		this.repSourceType = repSourceType;
	}






	public String getOtherSourceDesc() {
		return otherSourceDesc;
	}






	public void setOtherSourceDesc(String otherSourceDesc) {
		this.otherSourceDesc = otherSourceDesc;
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


	public String toString(){
		StringBuffer str = new StringBuffer(LauReportSource.class.getName());
		
		str.append("[");
		str.append("repsourceID=");
		str.append(repSourceID);
		str.append(", repSourceType=");
		str.append(repSourceType);
		str.append(",otherSourceDesc=");
		str.append(otherSourceDesc);
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
	
