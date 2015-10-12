package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nrg.lau.commons.IReportsConstants;

@SuppressWarnings("unused")
public class LauPregnancyDetails implements Serializable {

	private static final long serialVersionUID = 541658414731340074L;
	
	private long pregnancyDetailsId = 0;
	private String pregTestPerformed = "";
	private String pregTestDetails = "";
	private String pregExposureStatus = "";
	private String eligibleForPregRegistry = "";
	private String consentToContactPt = "";
	private String pastPregnancyCount = "";
	private String pastPregnancyComments = "";
	private String pastLiveBirthCount = "";
	private String pastFullTermCount = "";
	private String pastPrematureCount = "";
	private String pastMultipleBirthCount = "";
	private String pastMiscarriageCount = "";
	private String pastEctopicCount = "";
	private String pastAbortionCount = "";
	private String firstDayLastMenses = "";
	private String expectedDeliveryDate = "";
	private String pregDurationExposure = "";
	private String pregDurationExposureUnits = "";
	private String pregOutcome = "";
	private String pregOutcomeDate = "";
	private String pregOutcomeComments = "";
	private String pregTermAtOutcome = "";
	private String pregTermAtOutcomeUnits = "";
	private String electiveTermReason = "";
	private String multipleBirthFlag = "";
	private String multipleBirthDetials = "";
	private String pregComplications = "";
	private String updateUserId = "";
	private Object updateTimeStamp;	
	private String pastPregOutcomeComments = "";
	private long reportId = 0;
	private long transactionType = -1;
	private String paternalExposureFlag = "";
	private String pregComplicationsFlag = "";
	private String partnerName = "";
	private String partnerInitials = "";
	private String partnerDob = "";
	private String partnerContactDetails = "";
	private String gestAgeAtFirstDose = "";
	private String gestAgeAtFirstDoseUnits = "";
	private String gestAgeAtLastDose = "";
	private String gestAgeAtLastDoseUnits = "";
	private String gestAgeAtEvent = "";
	private String gestAgeAtEventUnits = "";	
	
	public long getPregnancyDetailsId() {
		return this.pregnancyDetailsId;
	}

	public void setPregnancyDetailsId(long pregnancyDetailsId) {
		this.pregnancyDetailsId = pregnancyDetailsId;
	}

	public String getPregTestPerformed() {
		return this.pregTestPerformed;
	}

	public void setPregTestPerformed(String pregTestPerformed) {
		this.pregTestPerformed = pregTestPerformed;
	}

	public String getPregTestDetails() {
		return this.pregTestDetails;
	}

	public void setPregTestDetails(String pregTestDetails) {
		this.pregTestDetails = pregTestDetails;
	}

	public String getPregExposureStatus() {
		return this.pregExposureStatus;
	}

	public void setPregExposureStatus(String pregExposureStatus) {
		this.pregExposureStatus = pregExposureStatus;
	}

	public String getEligibleForPregRegistry() {
		return this.eligibleForPregRegistry;
	}

	public void setEligibleForPregRegistry(String eligibleForPregRegistry) {
		this.eligibleForPregRegistry = eligibleForPregRegistry;
	}

	public String getConsentToContactPt() {
		return this.consentToContactPt;
	}

	public void setConsentToContactPt(String consentToContactPt) {
		this.consentToContactPt = consentToContactPt;
	}

	public String getPastPregnancyComments() {
		return this.pastPregnancyComments;
	}

	public void setPastPregnancyComments(String pastPregnancyComments) {
		this.pastPregnancyComments = pastPregnancyComments;
	}

	public String getFirstDayLastMenses() {
		return this.firstDayLastMenses;
	}

	public void setFirstDayLastMenses(String firstDayLastMenses) {
		this.firstDayLastMenses = firstDayLastMenses;
	}

	public String getExpectedDeliveryDate() {
		return this.expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(String expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public String getPregOutcome() {
		return this.pregOutcome;
	}

	public void setPregOutcome(String pregOutcome) {
		this.pregOutcome = pregOutcome;
	}

	public String getPregOutcomeDate() {
		return this.pregOutcomeDate;
	}

	public void setPregOutcomeDate(String pregOutcomeDate) {
		this.pregOutcomeDate = pregOutcomeDate;
	}

	public String getPregOutcomeComments() {
		return this.pregOutcomeComments;
	}

	public void setPregOutcomeComments(String pregOutcomeComments) {
		this.pregOutcomeComments = pregOutcomeComments;
	}

	public String getPregTermAtOutcomeUnits() {
		return this.pregTermAtOutcomeUnits;
	}

	public void setPregTermAtOutcomeUnits(String pregTermAtOutcomeUnits) {
		this.pregTermAtOutcomeUnits = pregTermAtOutcomeUnits;
	}

	public String getElectiveTermReason() {
		return this.electiveTermReason;
	}

	public void setElectiveTermReason(String electiveTermReason) {
		this.electiveTermReason = electiveTermReason;
	}

	public String getMultipleBirthFlag() {
		return this.multipleBirthFlag;
	}

	public void setMultipleBirthFlag(String multipleBirthFlag) {
		this.multipleBirthFlag = multipleBirthFlag;
	}

	public String getMultipleBirthDetials() {
		return this.multipleBirthDetials;
	}

	public void setMultipleBirthDetials(String multipleBirthDetials) {
		this.multipleBirthDetials = multipleBirthDetials;
	}

	public String getPregComplications() {
		return this.pregComplications;
	}

	public void setPregComplications(String pregComplications) {
		this.pregComplications = pregComplications;
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
	public String getPastPregOutcomeComments() {
		return pastPregOutcomeComments;
	}

	public void setPastPregOutcomeComments(String pastPregOutcomeComments) {
		this.pastPregOutcomeComments = pastPregOutcomeComments;
	}
	public String toString() {
		StringBuffer str = new StringBuffer(LauPregnancyDetails.class.getName());
		str.append("[");
		str.append("pregnancyDetailsId=");
		str.append(pregnancyDetailsId);
		str.append(", pregnantFlag=");
		str.append(", pregTestPerformed=");
		str.append(pregTestPerformed);
		str.append(", pregTestDetails=");
		str.append(pregTestDetails);
		str.append(", pregExposureStatus=");
		str.append(pregExposureStatus);
		str.append(", eligibleForPregRegistry=");
		str.append(eligibleForPregRegistry);
		str.append(", consentToContactPt=");
		str.append(consentToContactPt);
		str.append(", pastPregnancyCount=");
		str.append(pastPregnancyCount);
		str.append(", pastPregnancyComments=");
		str.append(pastPregnancyComments);
		str.append(", pastLiveBirthCount=");
		str.append(pastLiveBirthCount);
		str.append(", pastFullTermCount=");
		str.append(pastFullTermCount);
		str.append(", pastPrematureCount=");
		str.append(pastPrematureCount);
		str.append(", pastMultipleBirthCount=");
		str.append(pastMultipleBirthCount);
		str.append(", pastMiscarriageCount=");
		str.append(pastMiscarriageCount);
		str.append(", pastEctopicCount=");
		str.append(pastEctopicCount);
		str.append(", pastAbortionCount=");
		str.append(pastAbortionCount);
		str.append(", firstDayLastMenses=");
		str.append(firstDayLastMenses);
		str.append(", expectedDeliveryDate=");
		str.append(expectedDeliveryDate);
		str.append(", pregDurationExposure=");
		str.append(pregDurationExposure);
		str.append(", pregDurationExposureUnits=");
		str.append(pregDurationExposureUnits);
		str.append(", pregOutcome=");
		str.append(pregOutcome);
		str.append(", pregOutcomeDate=");
		str.append(pregOutcomeDate);
		str.append(", pregOutcomeComments=");
		str.append(pregOutcomeComments);
		str.append(", pregTermAtOutcome=");
		str.append(pregTermAtOutcome);
		str.append(", pregTermAtOutcomeUnits=");
		str.append(pregTermAtOutcomeUnits);
		str.append(", electiveTermReason=");
		str.append(electiveTermReason);
		str.append(", multipleBirthFlag=");
		str.append(multipleBirthFlag);
		str.append(", multipleBirthDetials=");
		str.append(multipleBirthDetials);
		str.append(", pregComplications=");
		str.append(pregComplications);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);		
		str.append(", pastPregOutcomeComments=");
		str.append(pastPregOutcomeComments);
		str.append(", paternalExposureFlag=");
		str.append(paternalExposureFlag);
		str.append(", pregComplicationsFlag=");
		str.append(pregComplicationsFlag);
		str.append(", partnerName=");
		str.append(partnerName);
		str.append(", partnerInitials=");
		str.append(partnerInitials);
		str.append(", partnerDob=");
		str.append(partnerDob);
		str.append(", partnerContactDetails=");
		str.append(partnerContactDetails);
		str.append(", gestAgeAtFirstDose=");
		str.append(gestAgeAtFirstDose);	
		str.append(", gestAgeAtFirstDoseUnits=");
		str.append(gestAgeAtFirstDoseUnits);		
		str.append(", gestAgeAtLastDose=");
		str.append(gestAgeAtLastDose);
		str.append(", gestAgeAtLastDoseUnits=");
		str.append(gestAgeAtLastDoseUnits);
		str.append(", gestAgeAtEvent=");
		str.append(gestAgeAtEvent);
		str.append(", gestAgeAtEventUnits=");
		str.append(gestAgeAtEventUnits);
		
		str.append("]");
		return str.toString();		
	}
	
	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}

	public String getPaternalExposureFlag() {
		return paternalExposureFlag;
	}

	public void setPaternalExposureFlag(String paternalExposureFlag) {
		this.paternalExposureFlag = paternalExposureFlag;
	}

	public String getPregComplicationsFlag() {
		return pregComplicationsFlag;
	}

	public void setPregComplicationsFlag(String pregComplicationsFlag) {
		this.pregComplicationsFlag = pregComplicationsFlag;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerInitials() {
		return partnerInitials;
	}

	public void setPartnerInitials(String partnerInitials) {
		this.partnerInitials = partnerInitials;
	}

	public String getPartnerDob() {
		return partnerDob;
	}

	public void setPartnerDob(String partnerDob) {
		this.partnerDob = partnerDob;
	}

	public String getPartnerContactDetails() {
		return partnerContactDetails;
	}

	public void setPartnerContactDetails(String partnerContactDetails) {
		this.partnerContactDetails = partnerContactDetails;
	}

	public String getGestAgeAtFirstDoseUnits() {
		return gestAgeAtFirstDoseUnits;
	}

	public void setGestAgeAtFirstDoseUnits(String gestAgeAtFirstDoseUnits) {
		this.gestAgeAtFirstDoseUnits = gestAgeAtFirstDoseUnits;
	}

	public String getGestAgeAtLastDoseUnits() {
		return gestAgeAtLastDoseUnits;
	}

	public void setGestAgeAtLastDoseUnits(String gestAgeAtLastDoseUnits) {
		this.gestAgeAtLastDoseUnits = gestAgeAtLastDoseUnits;
	}

	public String getGestAgeAtEventUnits() {
		return gestAgeAtEventUnits;
	}

	public void setGestAgeAtEventUnits(String gestAgeAtEventUnits) {
		this.gestAgeAtEventUnits = gestAgeAtEventUnits;
	}

	public String getPastPregnancyCount() {
		return pastPregnancyCount;
	}

	public void setPastPregnancyCount(String pastPregnancyCount) {
		this.pastPregnancyCount = pastPregnancyCount;
	}

	public String getPastLiveBirthCount() {
		return pastLiveBirthCount;
	}

	public void setPastLiveBirthCount(String pastLiveBirthCount) {
		this.pastLiveBirthCount = pastLiveBirthCount;
	}

	public String getPastFullTermCount() {
		return pastFullTermCount;
	}

	public void setPastFullTermCount(String pastFullTermCount) {
		this.pastFullTermCount = pastFullTermCount;
	}

	public String getPastPrematureCount() {
		return pastPrematureCount;
	}

	public void setPastPrematureCount(String pastPrematureCount) {
		this.pastPrematureCount = pastPrematureCount;
	}

	public String getPastMultipleBirthCount() {
		return pastMultipleBirthCount;
	}

	public void setPastMultipleBirthCount(String pastMultipleBirthCount) {
		this.pastMultipleBirthCount = pastMultipleBirthCount;
	}

	public String getPastMiscarriageCount() {
		return pastMiscarriageCount;
	}

	public void setPastMiscarriageCount(String pastMiscarriageCount) {
		this.pastMiscarriageCount = pastMiscarriageCount;
	}

	public String getPastEctopicCount() {
		return pastEctopicCount;
	}

	public void setPastEctopicCount(String pastEctopicCount) {
		this.pastEctopicCount = pastEctopicCount;
	}

	public String getPastAbortionCount() {
		return pastAbortionCount;
	}

	public void setPastAbortionCount(String pastAbortionCount) {
		this.pastAbortionCount = pastAbortionCount;
	}

	public String getPregDurationExposure() {
		return pregDurationExposure;
	}

	public void setPregDurationExposure(String pregDurationExposure) {
		this.pregDurationExposure = pregDurationExposure;
	}

	public String getPregDurationExposureUnits() {
		return pregDurationExposureUnits;
	}

	public void setPregDurationExposureUnits(String pregDurationExposureUnits) {
		this.pregDurationExposureUnits = pregDurationExposureUnits;
	}

	public String getPregTermAtOutcome() {
		return pregTermAtOutcome;
	}

	public void setPregTermAtOutcome(String pregTermAtOutcome) {
		this.pregTermAtOutcome = pregTermAtOutcome;
	}

	public String getGestAgeAtFirstDose() {
		return gestAgeAtFirstDose;
	}

	public void setGestAgeAtFirstDose(String gestAgeAtFirstDose) {
		this.gestAgeAtFirstDose = gestAgeAtFirstDose;
	}

	public String getGestAgeAtLastDose() {
		return gestAgeAtLastDose;
	}

	public void setGestAgeAtLastDose(String gestAgeAtLastDose) {
		this.gestAgeAtLastDose = gestAgeAtLastDose;
	}

	public String getGestAgeAtEvent() {
		return gestAgeAtEvent;
	}

	public void setGestAgeAtEvent(String gestAgeAtEvent) {
		this.gestAgeAtEvent = gestAgeAtEvent;
	}
}
