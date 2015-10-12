package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nrg.lau.commons.IReportsConstants;

public class LauMedicalHistory implements Serializable {

	private long medicalHistoryId = 0;
	private String medicalHistoryVerbatim = "";
	private BigDecimal displayNumber = IReportsConstants.ZERO;
	private String displayNumber2 = "";
	private String onsetDate = "";
	private String ongoingFlag = "";
	private String endDate = "";
	private String updateUserId = "";
	private Object updateTimeStamp;	
	private long reportId = 0;
	private long transactionType = -1;

	private static final long serialVersionUID = 1L;

	public long getMedicalHistoryId() {
		return this.medicalHistoryId;
	}

	public void setMedicalHistoryId(long medicalHistoryId) {
		this.medicalHistoryId = medicalHistoryId;
	}

	public String getMedicalHistoryVerbatim() {
		return this.medicalHistoryVerbatim;
	}

	public void setMedicalHistoryVerbatim(String medicalHistoryVerbatim) {
		this.medicalHistoryVerbatim = medicalHistoryVerbatim;
	}

	public BigDecimal getDisplayNumber() {
		return this.displayNumber;
	}
	
	public void setDisplayNumber(BigDecimal displayNumber) {
		this.displayNumber = displayNumber;
	}
	
	public String getDisplayNumber2() {
		return this.displayNumber2;
	}

	public void setDisplayNumber2(String displayNumber2) {
		if(displayNumber2.trim().length() > 0)	{
			this.displayNumber = new BigDecimal(displayNumber2);
		}else this.displayNumber = IReportsConstants.ZERO;
	}

	public String getOnsetDate() {
		return this.onsetDate;
	}

	public void setOnsetDate(String onsetDate) {
		this.onsetDate = onsetDate;
	}

	public String getOngoingFlag() {
		return this.ongoingFlag;
	}

	public void setOngoingFlag(String ongoingFlag) {
		this.ongoingFlag = ongoingFlag;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
		StringBuffer str = new StringBuffer(LauMedicalHistory.class.getName());
		str.append("[");
		str.append("medicalHistoryId=");
		str.append(medicalHistoryId);
		str.append(", medicalHistoryVerbatim=");
		str.append(medicalHistoryVerbatim);
		str.append(", displayNumber=");
		str.append(displayNumber);
		str.append(", onsetDate=");
		str.append(onsetDate);
		str.append(", ongoingFlag=");
		str.append(ongoingFlag);
		str.append(", endDate=");
		str.append(endDate);
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
	
	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}

}
