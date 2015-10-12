package com.nrg.lau.beans;

import java.io.Serializable;

public class LauSiebelReporterDetails implements Serializable {

	private static final long serialVersionUID = -6667746458001629820L;

	private long reporterDetailsId = 0;
	private String reporterSourceType = "";
	private String reporterType = "";
	private String reporterIsPatient = "";
	private String reporterContactDate = "";
	private String reporterContactMethod = "";
	private String reporterContactReason = "";
	private String relationshipToPatient = "";
	private String reporterIsPrescriber = "";
	private String reporterTitle = "";
	private String reporterFirstName = "";
	private String reporterMiddleName = "";
	private String reporterLastName = "";
	private String reporterAddress1 = "";
	private String reporterAddress2 = "";
	private String reporterAddress3 = "";
	private String reporterCity = "";
	private String reporterState = "";
	private String reporterPostalCode = "";
	private String reporterCountry = "";
	private String reporterPhoneNumber = "";
	private String reporterFaxNumber = "";
	private String reporterEmail = "";
	private String reporterSpecialty = "";
	private String reporterOccupation = "";
	private String contactType = "";

	private String updateUserId = "";
	private Object updateTimeStamp;
	private long reportId = 0;
	private long transactionType = -1;
	private String reporterTreatingMd = "";
	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getReporterTreatingMd() {
		return reporterTreatingMd;
	}

	public void setReporterTreatingMd(String reporterTreatingMd) {
		this.reporterTreatingMd = reporterTreatingMd;
	}

	public long getReporterDetailsId() {
		return this.reporterDetailsId;
	}

	public void setReporterDetailsId(long reporterDetailsId) {
		this.reporterDetailsId = reporterDetailsId;
	}

	public String getReporterSourceType() {
		return this.reporterSourceType;
	}

	public void setReporterSourceType(String reporterSourceType) {
		this.reporterSourceType = reporterSourceType;
	}

	public String getReporterType() {
		return this.reporterType;
	}

	public void setReporterType(String reporterType) {
		this.reporterType = reporterType;
	}

	public String getReporterIsPatient() {
		return this.reporterIsPatient;
	}

	public void setReporterIsPatient(String reporterIsPatient) {
		this.reporterIsPatient = reporterIsPatient;
	}

	public String getReporterContactDate() {
		return this.reporterContactDate;
	}

	public void setReporterContactDate(String reporterContactDate) {
		this.reporterContactDate = reporterContactDate;
	}

	public String getReporterContactMethod() {
		return this.reporterContactMethod;
	}

	public void setReporterContactMethod(String reporterContactMethod) {
		this.reporterContactMethod = reporterContactMethod;
	}

	public String getReporterContactReason() {
		return this.reporterContactReason;
	}

	public void setReporterContactReason(String reporterContactReason) {
		this.reporterContactReason = reporterContactReason;
	}

	public String getRelationshipToPatient() {
		return this.relationshipToPatient;
	}

	public void setRelationshipToPatient(String relationshipToPatient) {
		this.relationshipToPatient = relationshipToPatient;
	}

	public String getReporterIsPrescriber() {
		return this.reporterIsPrescriber;
	}

	public void setReporterIsPrescriber(String reporterIsPrescriber) {
		this.reporterIsPrescriber = reporterIsPrescriber;
	}

	public String getReporterTitle() {
		return this.reporterTitle;
	}

	public void setReporterTitle(String reporterTitle) {
		this.reporterTitle = reporterTitle;
	}

	public String getReporterFirstName() {
		return this.reporterFirstName;
	}

	public void setReporterFirstName(String reporterFirstName) {
		this.reporterFirstName = reporterFirstName;
	}

	public String getReporterMiddleName() {
		return this.reporterMiddleName;
	}

	public void setReporterMiddleName(String reporterMiddleName) {
		this.reporterMiddleName = reporterMiddleName;
	}

	public String getReporterLastName() {
		return this.reporterLastName;
	}

	public void setReporterLastName(String reporterLastName) {
		this.reporterLastName = reporterLastName;
	}

	public String getReporterAddress1() {
		return this.reporterAddress1;
	}

	public void setReporterAddress1(String reporterAddress1) {
		this.reporterAddress1 = reporterAddress1;
	}

	public String getReporterAddress2() {
		return this.reporterAddress2;
	}

	public void setReporterAddress2(String reporterAddress2) {
		this.reporterAddress2 = reporterAddress2;
	}

	public String getReporterAddress3() {
		return this.reporterAddress3;
	}

	public void setReporterAddress3(String reporterAddress3) {
		this.reporterAddress3 = reporterAddress3;
	}

	public String getReporterCity() {
		return this.reporterCity;
	}

	public void setReporterCity(String reporterCity) {
		this.reporterCity = reporterCity;
	}

	public String getReporterState() {
		return this.reporterState;
	}

	public void setReporterState(String reporterState) {
		this.reporterState = reporterState;
	}

	public String getReporterPostalCode() {
		return this.reporterPostalCode;
	}

	public void setReporterPostalCode(String reporterPostalCode) {
		this.reporterPostalCode = reporterPostalCode;
	}

	public String getReporterCountry() {
		return this.reporterCountry;
	}

	public void setReporterCountry(String reporterCountry) {
		if (reporterCountry.equalsIgnoreCase("United States of America"))
			this.reporterCountry = "USA";
	else if (reporterCountry.equalsIgnoreCase("Puerto Rico"))
			this.reporterCountry = "PRI";
	else if (reporterCountry.equalsIgnoreCase("Canada"))
			this.reporterCountry = "CAN";
	else
		this.reporterCountry = reporterCountry;
	}

	public String getReporterPhoneNumber() {
		return this.reporterPhoneNumber;
	}

	public void setReporterPhoneNumber(String reporterPhoneNumber) {
		this.reporterPhoneNumber = reporterPhoneNumber;
	}

	public String getReporterFaxNumber() {
		return this.reporterFaxNumber;
	}

	public void setReporterFaxNumber(String reporterFaxNumber) {
		this.reporterFaxNumber = reporterFaxNumber;
	}

	public String getReporterEmail() {
		return this.reporterEmail;
	}

	public void setReporterEmail(String reporterEmail) {
		this.reporterEmail = reporterEmail;
	}

	public String getReporterSpecialty() {
		return this.reporterSpecialty;
	}

	public void setReporterSpecialty(String reporterSpecialty) {
		this.reporterSpecialty =  reporterSpecialty.toUpperCase().trim();
	}

	public String getReporterOccupation() {
		return this.reporterOccupation;
	}

	public void setReporterOccupation(String reporterOccupation) {
		this.reporterOccupation = reporterOccupation;
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
		StringBuffer str = new StringBuffer(LauSiebelReporterDetails.class.getName());
		str.append("[");
		str.append("reporterDetailsId=");
		str.append(reporterDetailsId);
		str.append(", reporterSourceType=");
		str.append(reporterSourceType);
		str.append(", reporterType=");
		str.append(reporterType);
		str.append(", reporterIsPatient=");
		str.append(reporterIsPatient);
		str.append(", reporterContactDate=");
		str.append(reporterContactDate);
		str.append(", reporterContactMethod=");
		str.append(reporterContactMethod);
		str.append(", reporterContactReason=");
		str.append(reporterContactReason);
		str.append(", relationshipToPatient=");
		str.append(relationshipToPatient);
		str.append(", reporterIsPrescriber=");
		str.append(reporterIsPrescriber);
		str.append(", reporterTitle=");
		str.append(reporterTitle);
		str.append(", reporterFirstName=");
		str.append(reporterFirstName);
		str.append(", reporterMiddleName=");
		str.append(reporterMiddleName);
		str.append(", reporterLastName=");
		str.append(reporterLastName);
		str.append(", reporterAddress1=");
		str.append(reporterAddress1);
		str.append(", reporterAddress2=");
		str.append(reporterAddress2);
		str.append(", reporterAddress3=");
		str.append(reporterAddress3);
		str.append(", reporterCity=");
		str.append(reporterCity);
		str.append(", reporterState=");
		str.append(reporterState);
		str.append(", reporterPostalCode=");
		str.append(reporterPostalCode);
		str.append(", reporterCountry=");
		str.append(reporterCountry);
		str.append(", reporterPhoneNumber=");
		str.append(reporterPhoneNumber);
		str.append(", reporterFaxNumber=");
		str.append(reporterFaxNumber);
		str.append(", reporterEmail=");
		str.append(reporterEmail);
		str.append(", reporterSpecialty=");
		str.append(reporterSpecialty);
		str.append(", reporterOccupation=");
		str.append(reporterOccupation);
		str.append(", hcpContactDetailsAvailable=");

		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(",reporterTreatingMd=");
		str.append(reporterTreatingMd);
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
