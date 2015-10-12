package com.nrg.lau.beans;

import java.io.Serializable;

public class LauReports implements Serializable {

	private static final long serialVersionUID = 1227433398900769451L;
	
	private long reportId = 0;
	private long transactionType = -1;
	private String lauReportId = "";
	private String externalSourceSystemId = "";
	private String reportStatus = "";
	private String adverseEventFlag = "";
	private String productComplaintFlag = "";
	private String pregnancyReportFlag = "";
	private String reportInitialOrFollowup = "";
	private String reportOriginatorType = "";
	private String reportContactDate = "";
	private String reportProcessedDate = "";
	private String reportReviewStatus = "";
	private String reportAlert = "";
	private String reportAlertComment = "";
	private String countryEventOccurred = "";
	private String reportCreateDate = "";
	private String reportCreateUserId = "";
	private String reportCreateUserGroup = "";
	private Object reportReservedDate;
	private String reportReservedBy = "";
	private String reportClosedDate = "";
	private String routeToUserGroup = "";
	private String routeToUser = "";
	private String demotionCandidate = "";
	private String demotionReason = "";
	private String demotionReasonDetails = "";
	private String promotedReportOwner = "";
	private String promotedAeCaseId = "";
	private String promotedPcCaseId = "";
	private String otherCaseId = "";
	private String otherCaseIdType = "";
	private String followUpToCaseId = "";
	private String reportComments = "";
	private String updateUserId = "";
	private String pendingDataQuery = "";
	private Object updateTimeStamp;
	
	private String DEVICE_PRODUCT_COMPLAINT_FLAG = "";
	private String RUMOR_FLAG = "";
	private String REPORT_SOURCE_TYPE = "";
	private String SUBMITTED_REPORT_TYPE = "";
	private String DATE_OF_EVENT = "";
	private String REPORT_RECEIVED_DATE = "";
	private String FOLLOW_UP_TYPE = "";
	private String SUBMITTED_FOLLOW_UP_NUMBER = "";
		
	public String getDEVICE_PRODUCT_COMPLAINT_FLAG() {
		return DEVICE_PRODUCT_COMPLAINT_FLAG;
	}

	public void setDEVICE_PRODUCT_COMPLAINT_FLAG(String dEVICEPRODUCTCOMPLAINTFLAG) {
		DEVICE_PRODUCT_COMPLAINT_FLAG = dEVICEPRODUCTCOMPLAINTFLAG;
	}

	public String getRUMOR_FLAG() {
		return RUMOR_FLAG;
	}

	public void setRUMOR_FLAG(String rUMORFLAG) {
		RUMOR_FLAG = rUMORFLAG;
	}

	public String getREPORT_SOURCE_TYPE() {
		return REPORT_SOURCE_TYPE;
	}

	public void setREPORT_SOURCE_TYPE(String rEPORTSOURCETYPE) {
		REPORT_SOURCE_TYPE = rEPORTSOURCETYPE;
	}

	public long getReportId() {
		return this.reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getLauReportId() {
		return this.lauReportId;
	}

	public void setLauReportId(String lauReportId) {
		this.lauReportId = lauReportId;
	}

	public String getExternalSourceSystemId() {
		return this.externalSourceSystemId;
	}

	public void setExternalSourceSystemId(String externalSourceSystemId) {
		this.externalSourceSystemId = externalSourceSystemId;
	}

	public String getReportStatus() {
		return this.reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getAdverseEventFlag() {
		return this.adverseEventFlag;
	}

	public void setAdverseEventFlag(String adverseEventFlag) {
		this.adverseEventFlag = adverseEventFlag;
	}

	public String getProductComplaintFlag() {
		return this.productComplaintFlag;
	}

	public void setProductComplaintFlag(String productComplaintFlag) {
		this.productComplaintFlag = productComplaintFlag;
	}

	public String getPregnancyReportFlag() {
		return this.pregnancyReportFlag;
	}

	public void setPregnancyReportFlag(String pregnancyReportFlag) {
		this.pregnancyReportFlag = pregnancyReportFlag;
	}

	public String getReportInitialOrFollowup() {
		return this.reportInitialOrFollowup;
	}

	public void setReportInitialOrFollowup(String reportInitialOrFollowup) {
		this.reportInitialOrFollowup = reportInitialOrFollowup;
	}

	public String getReportOriginatorType() {
		return this.reportOriginatorType;
	}

	public void setReportOriginatorType(String reportOriginatorType) {
		this.reportOriginatorType = reportOriginatorType;
	}

	public String getReportContactDate() {
		return this.reportContactDate;
	}

	public void setReportContactDate(String reportContactDate) {
		this.reportContactDate = reportContactDate;
	}

	public String getReportProcessedDate() {
		return this.reportProcessedDate;
	}

	public void setReportProcessedDate(String reportProcessedDate) {
		this.reportProcessedDate = reportProcessedDate;
	}

	public String getReportReviewStatus() {
		return this.reportReviewStatus;
	}

	public void setReportReviewStatus(String reportReviewStatus) {
		this.reportReviewStatus = reportReviewStatus;
	}

	public String getReportAlert() {
		return this.reportAlert;
	}

	public void setReportAlert(String reportAlert) {
		this.reportAlert = reportAlert;
	}

	public String getReportAlertComment() {
		return this.reportAlertComment;
	}

	public void setReportAlertComment(String reportAlertComment) {
		this.reportAlertComment = reportAlertComment;
	}

	public String getCountryEventOccurred() {
		return this.countryEventOccurred;
	}

	public void setCountryEventOccurred(String countryEventOccurred) {
		this.countryEventOccurred = countryEventOccurred;
	}

	public String getReportCreateDate() {
		return this.reportCreateDate;
	}

	public void setReportCreateDate(String reportCreateDate) {
		this.reportCreateDate = reportCreateDate;
	}

	public String getReportCreateUserId() {
		return this.reportCreateUserId;
	}

	public void setReportCreateUserId(String reportCreateUserId) {
		this.reportCreateUserId = reportCreateUserId;
	}

	public String getReportCreateUserGroup() {
		return this.reportCreateUserGroup;
	}

	public void setReportCreateUserGroup(String reportCreateUserGroup) {
		this.reportCreateUserGroup = reportCreateUserGroup;
	}

	public Object getReportReservedDate() {
		return this.reportReservedDate;
	}

	public void setReportReservedDate(Object reportReservedDate) {
		this.reportReservedDate = reportReservedDate;
	}

	public String getReportReservedBy() {
		return this.reportReservedBy;
	}

	public void setReportReservedBy(String reportReservedBy) {
		this.reportReservedBy = reportReservedBy;
	}

	public String getReportClosedDate() {
		return this.reportClosedDate;
	}

	public void setReportClosedDate(String reportClosedDate) {
		this.reportClosedDate = reportClosedDate;
	}

	public String getRouteToUserGroup() {
		return this.routeToUserGroup;
	}

	public void setRouteToUserGroup(String routeToUserGroup) {
		this.routeToUserGroup = routeToUserGroup;
	}

	public String getRouteToUser() {
		return this.routeToUser;
	}

	public void setRouteToUser(String routeToUser) {
		this.routeToUser = routeToUser;
	}

	public String getDemotionCandidate() {
		return this.demotionCandidate;
	}

	public void setDemotionCandidate(String demotionCandidate) {
		this.demotionCandidate = demotionCandidate;
	}

	public String getDemotionReason() {
		return this.demotionReason;
	}

	public void setDemotionReason(String demotionReason) {
		this.demotionReason = demotionReason;
	}

	public String getDemotionReasonDetails() {
		return this.demotionReasonDetails;
	}

	public void setDemotionReasonDetails(String demotionReasonDetails) {
		this.demotionReasonDetails = demotionReasonDetails;
	}

	public String getPromotedReportOwner() {
		return this.promotedReportOwner;
	}

	public void setPromotedReportOwner(String promotedReportOwner) {
		this.promotedReportOwner = promotedReportOwner;
	}

	public String getPromotedAeCaseId() {
		return this.promotedAeCaseId;
	}

	public void setPromotedAeCaseId(String promotedAeCaseId) {
		this.promotedAeCaseId = promotedAeCaseId;
	}

	public String getPromotedPcCaseId() {
		return this.promotedPcCaseId;
	}

	public void setPromotedPcCaseId(String promotedPcCaseId) {
		this.promotedPcCaseId = promotedPcCaseId;
	}

	public String getOtherCaseId() {
		return this.otherCaseId;
	}

	public void setOtherCaseId(String otherCaseId) {
		this.otherCaseId = otherCaseId;
	}

	public String getOtherCaseIdType() {
		return this.otherCaseIdType;
	}

	public void setOtherCaseIdType(String otherCaseIdType) {
		this.otherCaseIdType = otherCaseIdType;
	}

	public String getFollowUpToCaseId() {
		return this.followUpToCaseId;
	}

	public void setFollowUpToCaseId(String followUpToCaseId) {
		this.followUpToCaseId = followUpToCaseId;
	}

	public String getReportComments() {
		return this.reportComments;
	}

	public void setReportComments(String reportComments) {
		this.reportComments = reportComments;
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
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauReports.class.getName());
		str.append("[");
		str.append("reportId=");
		str.append(reportId);
		str.append(", lauReportId=");
		str.append(lauReportId);
		str.append(", externalSourceSystemId=");
		str.append(externalSourceSystemId);
		str.append(", reportStatus=");
		str.append(reportStatus);
		str.append(", adverseEventFlag=");
		str.append(adverseEventFlag);
		str.append(", productComplaintFlag=");
		str.append(productComplaintFlag);
		str.append(", pregnancyReportFlag=");
		str.append(pregnancyReportFlag);
		str.append(", reportInitialOrFollowup=");
		str.append(reportInitialOrFollowup);
		str.append(", reportOriginatorType=");
		str.append(reportOriginatorType);
		str.append(", reportContactDate=");
		str.append(reportContactDate);
		str.append(", reportProcessedDate=");
		str.append(reportProcessedDate);
		str.append(", reportReviewStatus=");
		str.append(reportReviewStatus);
		str.append(", reportAlert=");
		str.append(reportAlert);
		str.append(", reportAlertComment=");
		str.append(reportAlertComment);
		str.append(", countryEventOccurred=");
		str.append(countryEventOccurred);
		str.append(", reportCreateDate=");
		str.append(reportCreateDate);
		str.append(", reportCreateUserId=");
		str.append(reportCreateUserId);
		str.append(", reportCreateUserGroup=");
		str.append(reportCreateUserGroup);
		str.append(", reportReservedDate=");
		str.append(reportReservedDate);
		str.append(", reportReservedBy=");
		str.append(reportReservedBy);
		str.append(", reportClosedDate=");
		str.append(reportClosedDate);
		str.append(", routeToUserGroup=");
		str.append(routeToUserGroup);
		str.append(", routeToUser=");
		str.append(routeToUser);
		str.append(", demotionCandidate=");
		str.append(demotionCandidate);
		str.append(", demotionReason=");
		str.append(demotionReason);
		str.append(", demotionReasonDetails=");
		str.append(demotionReasonDetails);
		str.append(", promotedReportOwner=");
		str.append(promotedReportOwner);
		str.append(", promotedAeCaseId=");
		str.append(promotedAeCaseId);
		str.append(", promotedPcCaseId=");
		str.append(promotedPcCaseId);
		str.append(", otherCaseId=");
		str.append(otherCaseId);
		str.append(", otherCaseIdType=");
		str.append(otherCaseIdType);
		str.append(", followUpToCaseId=");
		str.append(followUpToCaseId);
		str.append(", reportComments=");
		str.append(reportComments);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(pendingDataQuery);
		str.append(", pendingDataQuery=");
		str.append(",DEVICE_PRODUCT_COMPLAINT_FLAG=");
		str.append(DEVICE_PRODUCT_COMPLAINT_FLAG);
		str.append(",RUMOR_FLAG=");
		str.append(RUMOR_FLAG);
		str.append(",REPORT_SOURCE_TYPE=");
		str.append(REPORT_SOURCE_TYPE);
		str.append(",SUBMITTED_REPORT_TYPE=");
		str.append(SUBMITTED_REPORT_TYPE);
		str.append(",DATE_OF_EVENT=");
		str.append(DATE_OF_EVENT);
		str.append(",REPORT_RECEIVED_DATE=");
		str.append(REPORT_RECEIVED_DATE);
		str.append(",FOLLOW_UP_TYPE=");
		str.append(FOLLOW_UP_TYPE);
		str.append(",SUBMITTED_FOLLOW_UP_NUMBER=");
		str.append(SUBMITTED_FOLLOW_UP_NUMBER);
		str.append("]");
		return str.toString();
	}

	public String getSUBMITTED_FOLLOW_UP_NUMBER() {
		return SUBMITTED_FOLLOW_UP_NUMBER;
	}

	public void setSUBMITTED_FOLLOW_UP_NUMBER(String sUBMITTED_FOLLOW_UP_NUMBER) {
		SUBMITTED_FOLLOW_UP_NUMBER = sUBMITTED_FOLLOW_UP_NUMBER;
	}

	public String getSUBMITTED_REPORT_TYPE() {
		return SUBMITTED_REPORT_TYPE;
	}

	public void setSUBMITTED_REPORT_TYPE(String sUBMITTED_REPORT_TYPE) {
		SUBMITTED_REPORT_TYPE = sUBMITTED_REPORT_TYPE;
	}

	public String getDATE_OF_EVENT() {
		return DATE_OF_EVENT;
	}

	public void setDATE_OF_EVENT(String dATE_OF_EVENT) {
		DATE_OF_EVENT = dATE_OF_EVENT;
	}

	public String getREPORT_RECEIVED_DATE() {
		return REPORT_RECEIVED_DATE;
	}

	public void setREPORT_RECEIVED_DATE(String rEPORT_RECEIVED_DATE) {
		REPORT_RECEIVED_DATE = rEPORT_RECEIVED_DATE;
	}

	public String getFOLLOW_UP_TYPE() {
		return FOLLOW_UP_TYPE;
	}

	public void setFOLLOW_UP_TYPE(String fOLLOW_UP_TYPE) {
		FOLLOW_UP_TYPE = fOLLOW_UP_TYPE;
	}

	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}

	public void setPendingDataQuery(String pendingDataQuery) {
		this.pendingDataQuery = pendingDataQuery;
	}

	public String getPendingDataQuery() {
		return pendingDataQuery;
	}

}
