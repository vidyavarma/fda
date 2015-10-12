package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nrg.lau.commons.IReportsConstants;

@SuppressWarnings("unused")
public class LauPregnancyHistory implements Serializable {

	private static final long serialVersionUID = 532033990555823943L;
	
	private long pregnancyHistoryId = 0;
	private BigDecimal displayNumber = IReportsConstants.ZERO;
	private String displayNumber2 = "";
	private String pregnancyHistoryTerm = "";
	private String pregnancyHistoryComment = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long reportId = 0;
	private long transactionType = -1;
	
	public long getPregnancyHistoryId() {
		return this.pregnancyHistoryId;
	}

	public void setPregnancyHistoryId(long pregnancyHistoryId) {
		this.pregnancyHistoryId = pregnancyHistoryId;
	}

	public BigDecimal getDisplayNumber() {
		return this.displayNumber;
	}

	public void setDisplayNumber(BigDecimal displayNumber) {
		this.displayNumber = displayNumber;
	}

	public String getPregnancyHistoryTerm() {
		return this.pregnancyHistoryTerm;
	}

	public void setPregnancyHistoryTerm(String pregnancyHistoryTerm) {
		this.pregnancyHistoryTerm = pregnancyHistoryTerm;
	}

	public String getPregnancyHistoryComment() {
		return this.pregnancyHistoryComment;
	}

	public void setPregnancyHistoryComment(String pregnancyHistoryComment) {
		this.pregnancyHistoryComment = pregnancyHistoryComment;
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
		StringBuffer str = new StringBuffer(LauPregnancyHistory.class.getName());
		str.append("[");
		str.append("pregnancyHistoryId=");
		str.append(pregnancyHistoryId);
		str.append(", displayNumber=");
		str.append(displayNumber);
		str.append(", pregnancyHistoryTerm=");
		str.append(pregnancyHistoryTerm);
		str.append(", pregnancyHistoryComment=");
		str.append(pregnancyHistoryComment);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append("]");
		return str.toString();
	}

	public void setDisplayNumber2(String displayNumber2) {
		if(displayNumber2.trim().length() > 0)	{
			this.displayNumber = new BigDecimal(displayNumber2);
		}else this.displayNumber = IReportsConstants.ZERO;
	}
	
	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}

}
