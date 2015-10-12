package com.nrg.lau.beans;

import java.io.Serializable;

public class LauPatientDetails implements Serializable {

	private static final long serialVersionUID = 4131308760097211343L;

	private long patientDetailsId = 0;
	private long reportId = 0;
	private long transactionType = -1;
	private String ptSex = "";
	private String ptDob = "";
	private String ptAge = "";
	private String ptAgeUnits = "";
	private String ptAgeGroup = "";
	private String ptTitle = "";
	private String ptFirstName = "";
	private String ptMiddleName = "";
	private String ptLastName = "";
	private String ptInitials = "";
	private String ptAddress1 = "";
	private String ptAddress2;
	private String ptAddress3 = "";
	private String ptCity = "";
	private String ptState = "";
	private String ptPostalCode = "";
	private String ptCountry = "";
	private String ptPhoneNumber = "";
	private String ptFaxNumber = "";
	private String ptEmail = "";
	private String deathDate = "";
	private String causeOfDeath = "";
	private String autopsyPerformed = "";
	private String studyId = "";
	private String studyPatientId = "";
	private String studyEnrollmentStatus = "";
	private String studyEnrollmentDate = "";
	private String studyWithdrawnDate = "";
	private String patientIdentifier1 = "";
	private String patientIdentifier2 = "";
	private String patientIdentifier3 = "";
	private String patientIndicator1 = "";
	private String patientIndicator2 = "";
	private String patientIndicator3 = "";
	private String consentToContactHcp = "";
	private String consentToUsePersonalDtls = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private String pregnancyStatus = "";
	private String hcpContactDetailsAvailable = "";
	private String STUDY_CENTER_ID = "";
	private String PT_WEIGHT = "";
	private String PT_WEIGHT_UNITS = "";
	private String PATIENT_DIED_FLAG = "";
	private String LIFE_THREATENING_FLAG = "";
	private String HOSPITALIZATION_FLAG = "";
	private String HOSPITALIZATION_NATURE = "";
	private String HOSPITALIZED_FROM_DATE = "";
	private String HOSPITALIZED_TO_DATE = "";
	private String REQUIRED_INTERVENTION = "";
	private String DISABILITY_FLAG = "";
	private String CONGENITAL_ANOMALY_FLAG = "";
	private String MEDICALLY_IMPORTANT_EVENT = "";
	private String OTHER_SERIOUSNESS_DESC = "";
	private String CUSTOM_COMMENT_01 = "";
	private String CUSTOM_COMMENT_02 = "";
	private String DISPLAY_NUMBER = "";
	
	private String INTERNAL_LINK_ID="";
	
	public String getPT_WEIGHT() {
		return PT_WEIGHT;
	}

	public void setPT_WEIGHT(String pT_WEIGHT) {
		PT_WEIGHT = pT_WEIGHT;
	}

	public String getPT_WEIGHT_UNITS() {
		return PT_WEIGHT_UNITS;
	}

	public void setPT_WEIGHT_UNITS(String pT_WEIGHT_UNITS) {
		PT_WEIGHT_UNITS = pT_WEIGHT_UNITS;
	}

	public String getPATIENT_DIED_FLAG() {
		return PATIENT_DIED_FLAG;
	}

	public void setPATIENT_DIED_FLAG(String pATIENT_DIED_FLAG) {
		PATIENT_DIED_FLAG = pATIENT_DIED_FLAG;
	}

	public String getLIFE_THREATENING_FLAG() {
		return LIFE_THREATENING_FLAG;
	}

	public void setLIFE_THREATENING_FLAG(String lIFE_THREATENING_FLAG) {
		LIFE_THREATENING_FLAG = lIFE_THREATENING_FLAG;
	}

	public String getHOSPITALIZATION_FLAG() {
		return HOSPITALIZATION_FLAG;
	}

	public void setHOSPITALIZATION_FLAG(String hOSPITALIZATION_FLAG) {
		HOSPITALIZATION_FLAG = hOSPITALIZATION_FLAG;
	}

	public String getHOSPITALIZATION_NATURE() {
		return HOSPITALIZATION_NATURE;
	}

	public void setHOSPITALIZATION_NATURE(String hOSPITALIZATION_NATURE) {
		HOSPITALIZATION_NATURE = hOSPITALIZATION_NATURE;
	}

	public String getHOSPITALIZED_FROM_DATE() {
		return HOSPITALIZED_FROM_DATE;
	}

	public void setHOSPITALIZED_FROM_DATE(String hOSPITALIZED_FROM_DATE) {
		HOSPITALIZED_FROM_DATE = hOSPITALIZED_FROM_DATE;
	}

	public String getHOSPITALIZED_TO_DATE() {
		return HOSPITALIZED_TO_DATE;
	}

	public void setHOSPITALIZED_TO_DATE(String hOSPITALIZED_TO_DATE) {
		HOSPITALIZED_TO_DATE = hOSPITALIZED_TO_DATE;
	}

	public String getREQUIRED_INTERVENTION() {
		return REQUIRED_INTERVENTION;
	}

	public void setREQUIRED_INTERVENTION(String rEQUIRED_INTERVENTION) {
		REQUIRED_INTERVENTION = rEQUIRED_INTERVENTION;
	}

	public String getDISABILITY_FLAG() {
		return DISABILITY_FLAG;
	}

	public void setDISABILITY_FLAG(String dISABILITY_FLAG) {
		DISABILITY_FLAG = dISABILITY_FLAG;
	}

	public String getCONGENITAL_ANOMALY_FLAG() {
		return CONGENITAL_ANOMALY_FLAG;
	}

	public void setCONGENITAL_ANOMALY_FLAG(String cONGENITAL_ANOMALY_FLAG) {
		CONGENITAL_ANOMALY_FLAG = cONGENITAL_ANOMALY_FLAG;
	}

	public String getMEDICALLY_IMPORTANT_EVENT() {
		return MEDICALLY_IMPORTANT_EVENT;
	}

	public void setMEDICALLY_IMPORTANT_EVENT(String mEDICALLY_IMPORTANT_EVENT) {
		MEDICALLY_IMPORTANT_EVENT = mEDICALLY_IMPORTANT_EVENT;
	}

	public String getOTHER_SERIOUSNESS_DESC() {
		return OTHER_SERIOUSNESS_DESC;
	}

	public void setOTHER_SERIOUSNESS_DESC(String oTHER_SERIOUSNESS_DESC) {
		OTHER_SERIOUSNESS_DESC = oTHER_SERIOUSNESS_DESC;
	}

	public String getCUSTOM_COMMENT_01() {
		return CUSTOM_COMMENT_01;
	}

	public void setCUSTOM_COMMENT_01(String cUSTOM_COMMENT_01) {
		CUSTOM_COMMENT_01 = cUSTOM_COMMENT_01;
	}

	public String getCUSTOM_COMMENT_02() {
		return CUSTOM_COMMENT_02;
	}

	public void setCUSTOM_COMMENT_02(String cUSTOM_COMMENT_02) {
		CUSTOM_COMMENT_02 = cUSTOM_COMMENT_02;
	}

	public String getPregnancyStatus() {
		return pregnancyStatus;
	}

	public void setPregnancyStatus(String pregnancyStatus) {
		this.pregnancyStatus = pregnancyStatus;
	}

	public long getPatientDetailsId() {
		return this.patientDetailsId;
	}

	public void setPatientDetailsId(long patientDetailsId) {
		this.patientDetailsId = patientDetailsId;
	}

	public String getPtSex() {
		return this.ptSex;
	}

	public String getSTUDY_CENTER_ID() {
		return STUDY_CENTER_ID;
	}

	public void setSTUDY_CENTER_ID(String sTUDY_CENTER_ID) {
		STUDY_CENTER_ID = sTUDY_CENTER_ID;
	}

	public void setPtSex(String ptSex) {
		this.ptSex = ptSex;
	}

	public String getPtDob() {
		return this.ptDob;
	}

	public void setPtDob(String ptDob) {
		this.ptDob = ptDob;
	}

	public String getPtAge() {
		return this.ptAge;
	}

	public void setPtAge(String ptAge) {
		this.ptAge = ptAge;
	}

	public String getPtAgeUnits() {
		return this.ptAgeUnits;
	}

	public void setPtAgeUnits(String ptAgeUnits) {
		this.ptAgeUnits = ptAgeUnits;
	}

	public String getPtAgeGroup() {
		return this.ptAgeGroup;
	}

	public void setPtAgeGroup(String ptAgeGroup) {
		this.ptAgeGroup = ptAgeGroup;
	}

	public String getPtTitle() {
		return this.ptTitle;
	}

	public void setPtTitle(String ptTitle) {
		this.ptTitle = ptTitle;
	}

	public String getPtFirstName() {
		return this.ptFirstName;
	}

	public void setPtFirstName(String ptFirstName) {
		this.ptFirstName = ptFirstName;
	}

	public String getPtMiddleName() {
		return this.ptMiddleName;
	}

	public void setPtMiddleName(String ptMiddleName) {
		this.ptMiddleName = ptMiddleName;
	}

	public String getPtLastName() {
		return this.ptLastName;
	}

	public void setPtLastName(String ptLastName) {
		this.ptLastName = ptLastName;
	}

	public String getPtInitials() {
		return this.ptInitials;
	}

	public void setPtInitials(String ptInitials) {
		this.ptInitials = ptInitials;
	}

	public String getPtAddress1() {
		return this.ptAddress1;
	}

	public void setPtAddress1(String ptAddress1) {
		this.ptAddress1 = ptAddress1;
	}

	public String getPtAddress2() {
		return this.ptAddress2;
	}

	public void setPtAddress2(String ptAddress2) {
		this.ptAddress2 = ptAddress2;
	}

	public String getPtAddress3() {
		return this.ptAddress3;
	}

	public void setPtAddress3(String ptAddress3) {
		this.ptAddress3 = ptAddress3;
	}

	public String getPtCity() {
		return this.ptCity;
	}

	public void setPtCity(String ptCity) {
		this.ptCity = ptCity;
	}

	public String getPtState() {
		return this.ptState;
	}

	public void setPtState(String ptState) {
		this.ptState = ptState;
	}

	public String getPtPostalCode() {
		return this.ptPostalCode;
	}

	public void setPtPostalCode(String ptPostalCode) {
		this.ptPostalCode = ptPostalCode;
	}

	public String getPtCountry() {
		return this.ptCountry;
	}

	public void setPtCountry(String ptCountry) {
		this.ptCountry = ptCountry;
	}

	public String getPtPhoneNumber() {
		return this.ptPhoneNumber;
	}

	public void setPtPhoneNumber(String ptPhoneNumber) {
		this.ptPhoneNumber = ptPhoneNumber;
	}

	public String getPtFaxNumber() {
		return this.ptFaxNumber;
	}

	public void setPtFaxNumber(String ptFaxNumber) {
		this.ptFaxNumber = ptFaxNumber;
	}

	public String getPtEmail() {
		return this.ptEmail;
	}

	public void setPtEmail(String ptEmail) {
		this.ptEmail = ptEmail;
	}

	public String getDeathDate() {
		return this.deathDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public String getCauseOfDeath() {
		return this.causeOfDeath;
	}

	public void setCauseOfDeath(String causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}

	public String getAutopsyPerformed() {
		return this.autopsyPerformed;
	}

	public void setAutopsyPerformed(String autopsyPerformed) {
		this.autopsyPerformed = autopsyPerformed;
	}

	public String getStudyId() {
		return this.studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getStudyPatientId() {
		return this.studyPatientId;
	}

	public void setStudyPatientId(String studyPatientId) {
		this.studyPatientId = studyPatientId;
	}

	public String getStudyEnrollmentStatus() {
		return this.studyEnrollmentStatus;
	}

	public void setStudyEnrollmentStatus(String studyEnrollmentStatus) {
		this.studyEnrollmentStatus = studyEnrollmentStatus;
	}

	public String getStudyEnrollmentDate() {
		return this.studyEnrollmentDate;
	}

	public void setStudyEnrollmentDate(String studyEnrollmentDate) {
		this.studyEnrollmentDate = studyEnrollmentDate;
	}

	public String getStudyWithdrawnDate() {
		return this.studyWithdrawnDate;
	}

	public void setStudyWithdrawnDate(String studyWithdrawnDate) {
		this.studyWithdrawnDate = studyWithdrawnDate;
	}

	public String getPatientIdentifier1() {
		return this.patientIdentifier1;
	}

	public void setPatientIdentifier1(String patientIdentifier1) {
		this.patientIdentifier1 = patientIdentifier1;
	}

	public String getPatientIdentifier2() {
		return this.patientIdentifier2;
	}

	public void setPatientIdentifier2(String patientIdentifier2) {
		this.patientIdentifier2 = patientIdentifier2;
	}

	public String getPatientIdentifier3() {
		return this.patientIdentifier3;
	}

	public void setPatientIdentifier3(String patientIdentifier3) {
		this.patientIdentifier3 = patientIdentifier3;
	}

	public String getPatientIndicator1() {
		return this.patientIndicator1;
	}

	public void setPatientIndicator1(String patientIndicator1) {
		this.patientIndicator1 = patientIndicator1;
	}

	public String getPatientIndicator2() {
		return this.patientIndicator2;
	}

	public void setPatientIndicator2(String patientIndicator2) {
		this.patientIndicator2 = patientIndicator2;
	}

	public String getPatientIndicator3() {
		return this.patientIndicator3;
	}

	public void setPatientIndicator3(String patientIndicator3) {
		this.patientIndicator3 = patientIndicator3;
	}

	public String getConsentToContactHcp() {
		return this.consentToContactHcp;
	}

	public void setConsentToContactHcp(String consentToContactHcp) {
		this.consentToContactHcp = consentToContactHcp;
	}

	public String getConsentToUsePersonalDtls() {
		return this.consentToUsePersonalDtls;
	}

	public void setConsentToUsePersonalDtls(String consentToUsePersonalDtls) {
		this.consentToUsePersonalDtls = consentToUsePersonalDtls;
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

	public String getHcpContactDetailsAvailable() {
		return this.hcpContactDetailsAvailable;
	}

	public void setHcpContactDetailsAvailable(String hcpContactDetailsAvailable) {
		this.hcpContactDetailsAvailable = hcpContactDetailsAvailable;
	}

	public String toString() {
		StringBuffer str = new StringBuffer(LauReports.class.getName());
		str.append("[");
		str.append("patientDetailsId=");
		str.append(patientDetailsId);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", ptSex=");
		str.append(ptSex);
		str.append(", ptDob=");
		str.append(ptDob);
		str.append(", ptAge=");
		str.append(ptAge);
		str.append(", ptAgeUnits=");
		str.append(ptAgeUnits);
		str.append(", ptAgeGroup=");
		str.append(ptAgeGroup);
		str.append(", ptTitle=");
		str.append(ptTitle);
		str.append(", ptFirstName=");
		str.append(ptFirstName);
		str.append(", ptMiddleName=");
		str.append(ptMiddleName);
		str.append(", ptLastName=");
		str.append(ptLastName);
		str.append(", ptInitials=");
		str.append(ptInitials);
		str.append(", ptAddress1=");
		str.append(ptAddress1);
		str.append(", ptAddress2=");
		str.append(ptAddress2);
		str.append(", ptAddress3=");
		str.append(ptAddress3);
		str.append(", ptCity=");
		str.append(ptCity);
		str.append(", ptState=");
		str.append(ptState);
		str.append(", ptPostalCode=");
		str.append(ptPostalCode);
		str.append(", ptCountry=");
		str.append(ptCountry);
		str.append(", ptPhoneNumber=");
		str.append(ptPhoneNumber);
		str.append(", ptFaxNumber=");
		str.append(ptFaxNumber);
		str.append(", ptEmail=");
		str.append(ptEmail);
		str.append(", deathDate=");
		str.append(deathDate);
		str.append(", causeOfDeath=");
		str.append(causeOfDeath);
		str.append(", autopsyPerformed=");
		str.append(autopsyPerformed);
		str.append(", studyId=");
		str.append(studyId);
		str.append(", studyPatientId=");
		str.append(studyPatientId);
		str.append(", studyEnrollmentStatus=");
		str.append(studyEnrollmentStatus);
		str.append(", studyEnrollmentDate=");
		str.append(studyEnrollmentDate);
		str.append(", studyWithdrawnDate=");
		str.append(studyWithdrawnDate);
		str.append(", patientIdentifier1=");
		str.append(patientIdentifier1);
		str.append(", patientIdentifier2=");
		str.append(patientIdentifier2);
		str.append(", patientIdentifier3=");
		str.append(patientIdentifier3);
		str.append(", patientIndicator1=");
		str.append(patientIndicator1);
		str.append(", patientIndicator2=");
		str.append(patientIndicator2);
		str.append(", patientIndicator3=");
		str.append(patientIndicator3);
		str.append(", consentToContactHcp=");
		str.append(consentToContactHcp);
		str.append(", consentToUsePersonalDtls=");
		str.append(consentToUsePersonalDtls);
		str.append(", hcpContactDetailsAvailable=");
		str.append(hcpContactDetailsAvailable);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", pregnancyStatus=");
		str.append(pregnancyStatus);
		str.append(", PT_WEIGHT=");
		str.append(PT_WEIGHT);
		str.append(", PT_WEIGHT_UNITS=");
		str.append(PT_WEIGHT_UNITS);
		str.append(", PATIENT_DIED_FLAG=");
		str.append(PATIENT_DIED_FLAG);
		str.append(", LIFE_THREATENING_FLAG=");
		str.append(LIFE_THREATENING_FLAG);
		str.append(", HOSPITALIZATION_FLAG=");
		str.append(HOSPITALIZATION_FLAG);
		str.append(", HOSPITALIZATION_NATURE=");
		str.append(HOSPITALIZATION_NATURE);
		str.append(", HOSPITALIZED_FROM_DATE=");
		str.append(HOSPITALIZED_FROM_DATE);
		str.append(", HOSPITALIZED_TO_DATE=");
		str.append(HOSPITALIZED_TO_DATE);
		str.append(", REQUIRED_INTERVENTION=");
		str.append(REQUIRED_INTERVENTION);
		str.append(", DISABILITY_FLAG=");
		str.append(DISABILITY_FLAG);
		str.append(", CONGENITAL_ANOMALY_FLAG=");
		str.append(CONGENITAL_ANOMALY_FLAG);
		str.append(", MEDICALLY_IMPORTANT_EVENT=");
		str.append(MEDICALLY_IMPORTANT_EVENT);
		str.append(", OTHER_SERIOUSNESS_DESC=");
		str.append(OTHER_SERIOUSNESS_DESC);
		str.append(", CUSTOM_COMMENT_01=");
		str.append(CUSTOM_COMMENT_01);
		str.append(", CUSTOM_COMMENT_02=");
		str.append(CUSTOM_COMMENT_02);
		str.append(", DISPLAY_NUMBER=");
		str.append(DISPLAY_NUMBER);
		str.append("]");
		return str.toString();
	}

	public String getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}

	public void setDISPLAY_NUMBER(String dISPLAY_NUMBER) {
		DISPLAY_NUMBER = dISPLAY_NUMBER;
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
