package com.nrg.lau.beans;

import java.io.Serializable;

public class LauActivityLog implements Serializable {

	private String updateUserId = "";
	private Object updateTimeStamp;
	private long reportId = 0;
	private long activityLogId = 0;

	private String activityType  = "";
	private String reportUpdateReasonCode  = "";
	private String reportUpdateReasonDesc  = "";
	private long transactionType = -1;

	private static final long serialVersionUID = 1L;



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
	/**
	 * @return the attachmentId
	 */
	public long getActivityLogId() {
		return activityLogId;
	}

	/**
	 * @param attachmentId the attachmentId to set
	 * @param activityLogId 
	 */
	public void setActivityLogId( long activityLogId) {
		this.activityLogId = activityLogId;
	}

	/**
	 * @return the activityType
	 */
	public String getActivityType() {
		return activityType;
	}

	/**
	 * @param activityType the activityType to set
	 */
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return the reportUpdateReasonCode
	 */
	public String getReportUpdateReasonCode() {
		return reportUpdateReasonCode;
	}

	/**
	 * @param reportUpdateReasonCode the reportUpdateReasonCode to set
	 */
	public void setReportUpdateReasonCode (String reportUpdateReasonCode) {
		this.reportUpdateReasonCode = reportUpdateReasonCode;
	}

	/**
	 * @return the reportUpdateReasonDesc
	 */
	public String getReportUpdateReasonDesc() {
		return reportUpdateReasonDesc;
	}

	/**
	 * @param reportUpdateReasonDesc the reportUpdateReasonDesc to set
	 */
	public void setReportUpdateReasonDesc(String reportUpdateReasonDesc) {
		this.reportUpdateReasonDesc = reportUpdateReasonDesc;
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauNarrativeText.class.getName());
		str.append("[");
		str.append("activityLogId=");
		str.append(activityLogId);
		str.append(", displayNumber=");
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", activityType=");
		str.append(activityType);
		str.append(", reportUpdateReasonCode =");
		str.append(reportUpdateReasonCode );
		str.append(", reportUpdateReasonDesc =");
		str.append(reportUpdateReasonDesc );
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
/**
 "LAU_REPORT_ACTIVITY_LOG"
  (
    "ACTIVITY_LOG_ID"           NUMBER NOT NULL ENABLE,
    "REPORT_ID"                 NUMBER NOT NULL ENABLE,
    "ACTIVITY_TYPE"             VARCHAR2(50 BYTE) NOT NULL ENABLE,
    "REPORT_UPDATE_REASON_CODE" VARCHAR2(50 BYTE),
    "REPORT_UPDATE_REASON_DESC" VARCHAR2(500 BYTE),
    "UPDATE_USER_ID"            VARCHAR2(300 BYTE) NOT NULL ENABLE,
    "UPDATE_TIMESTAMP" TIMESTAMP (0)**/
