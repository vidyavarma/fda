package com.nrg.lau.beans;

import java.io.Serializable;

public class LauRelatedReports implements Serializable {

	private long relatedReportId = 0;
	private String relatedReport = "";
	private String relationReason = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long reportId = 0;
	private long transactionType = -1;

	private static final long serialVersionUID = 1L;

	public long getRelatedReportId() {
		return this.relatedReportId;
	}

	public void setRelatedReportId(long relatedReportId) {
		this.relatedReportId = relatedReportId;
	}

	public String getRelatedReport() {
		return this.relatedReport;
	}

	public void setRelatedReport(String relatedReport) {
		this.relatedReport = relatedReport;
	}

	public String getRelationReason() {
		return this.relationReason;
	}

	public void setRelationReason(String relationReason) {
		this.relationReason = relationReason;
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
		StringBuffer str = new StringBuffer(LauRelatedReports.class.getName());
		str.append("[");
		str.append("relatedReportId=");
		str.append(relatedReportId);
		str.append(", relatedReport=");
		str.append(relatedReport);
		str.append(", relationReason=");
		str.append(relationReason);
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
