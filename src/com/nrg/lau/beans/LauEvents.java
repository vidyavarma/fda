package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nrg.lau.commons.IReportsConstants;

public class LauEvents implements Serializable {
	
	private long eventId = 0;
	private String eventVerbatim = "";
	private BigDecimal displayNumber;
	private String displayNumber2 = "";
	private String onsetDate = "";
	private String eventOngoingFlag = "";
	private String endDate = "";
	private String seriousFlag = "";
	private String hospitalizationFlag = "";
	private String medicallySignificantFlag = "";
	private String patientDiedFlag = "";
	private String lifeThreateningFlag = "";
	private String disabilityFlag = "";
	private String congenitalAnomalyFlag = "";
	private String hospitalizedFromDate = "";
	private String hospitalizedToDate = "";
	private String eventOutcome = "";
	private String companyCausality = "";
	private String reporterCausality = "";
	private String priorHistoryOfEvent = "";
	private String priorHistoryComment = "";
	private String relatedToProdComplaint = "";
	private String relatedToLotNumber = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private String hospitalizationNature = "";
	private String OTHER_SERIOUSNESS_DESC = "";
	private String OTHER_SERIOUSNESS_FLAG = "";
	private long reportId = 0;
	private String EVENT_LLT = "";
	private String EVENT_PT = "";
	private String EVENT_SOC = "";
	private String EVENT_HLT = "";
	private String EVENT_HLGT = "";
	private String EVENT_TERM = "";
	private String DEATH_DATE = "";
	private String AUTOPSY_PERFORMED = "";
	private long transactionType = -1;

	private static final long serialVersionUID = 1L;
	
	public String getOTHER_SERIOUSNESS_DESC() {
		return OTHER_SERIOUSNESS_DESC;
	}

	public void setOTHER_SERIOUSNESS_DESC(String oTHER_SERIOUSNESS_DESC) {
		OTHER_SERIOUSNESS_DESC = oTHER_SERIOUSNESS_DESC;
	}
	
	public long getEventId() {
		return this.eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getEventVerbatim() {
		return this.eventVerbatim;
	}

	public void setEventVerbatim(String eventVerbatim) {
		this.eventVerbatim = eventVerbatim;
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

	public String getEventOngoingFlag() {
		return this.eventOngoingFlag;
	}

	public void setEventOngoingFlag(String eventOngoingFlag) {
		this.eventOngoingFlag = eventOngoingFlag;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSeriousFlag() {
		return this.seriousFlag;
	}

	public void setSeriousFlag(String seriousFlag) {
		this.seriousFlag = seriousFlag;
	}

	public String getHospitalizationFlag() {
		return this.hospitalizationFlag;
	}

	public void setHospitalizationFlag(String hospitalizationFlag) {
		this.hospitalizationFlag = hospitalizationFlag;
	}

	public String getMedicallySignificantFlag() {
		return this.medicallySignificantFlag;
	}

	public void setMedicallySignificantFlag(String medicallySignificantFlag) {
		this.medicallySignificantFlag = medicallySignificantFlag;
	}

	public String getPatientDiedFlag() {
		return this.patientDiedFlag;
	}

	public void setPatientDiedFlag(String patientDiedFlag) {
		this.patientDiedFlag = patientDiedFlag;
	}

	public String getLifeThreateningFlag() {
		return this.lifeThreateningFlag;
	}

	public void setLifeThreateningFlag(String lifeThreateningFlag) {
		this.lifeThreateningFlag = lifeThreateningFlag;
	}

	public String getDisabilityFlag() {
		return this.disabilityFlag;
	}

	public void setDisabilityFlag(String disabilityFlag) {
		this.disabilityFlag = disabilityFlag;
	}

	public String getCongenitalAnomalyFlag() {
		return this.congenitalAnomalyFlag;
	}

	public void setCongenitalAnomalyFlag(String congenitalAnomalyFlag) {
		this.congenitalAnomalyFlag = congenitalAnomalyFlag;
	}

	public String getHospitalizedFromDate() {
		return this.hospitalizedFromDate;
	}

	public void setHospitalizedFromDate(String hospitalizedFromDate) {
		this.hospitalizedFromDate = hospitalizedFromDate;
	}

	public String getHospitalizedToDate() {
		return this.hospitalizedToDate;
	}

	public void setHospitalizedToDate(String hospitalizedToDate) {
		this.hospitalizedToDate = hospitalizedToDate;
	}

	public String getEventOutcome() {
		return this.eventOutcome;
	}

	public void setEventOutcome(String eventOutcome) {
		this.eventOutcome = eventOutcome;
	}

	public String getCompanyCausality() {
		return this.companyCausality;
	}

	public void setCompanyCausality(String companyCausality) {
		this.companyCausality = companyCausality;
	}

	public String getReporterCausality() {
		return this.reporterCausality;
	}

	public void setReporterCausality(String reporterCausality) {
		this.reporterCausality = reporterCausality;
	}

	public String getPriorHistoryOfEvent() {
		return this.priorHistoryOfEvent;
	}

	public void setPriorHistoryOfEvent(String priorHistoryOfEvent) {
		this.priorHistoryOfEvent = priorHistoryOfEvent;
	}

	public String getPriorHistoryComment() {
		return this.priorHistoryComment;
	}

	public void setPriorHistoryComment(String priorHistoryComment) {
		this.priorHistoryComment = priorHistoryComment;
	}

	public String getRelatedToProdComplaint() {
		return this.relatedToProdComplaint;
	}

	public void setRelatedToProdComplaint(String relatedToProdComplaint) {
		this.relatedToProdComplaint = relatedToProdComplaint;
	}

	public String getRelatedToLotNumber() {
		return this.relatedToLotNumber;
	}

	public void setRelatedToLotNumber(String relatedToLotNumber) {
		this.relatedToLotNumber = relatedToLotNumber;
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

	public String getHospitalizationNature() {
		return this.hospitalizationNature;
	}

	public void setHospitalizationNature(String hospitalizationNature) {
		this.hospitalizationNature = hospitalizationNature;
	}

	public long getReportId() {
		return this.reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauEvents.class.getName());
		str.append("[");
		str.append("eventId=");
		str.append(eventId);
		str.append(", eventVerbatim=");
		str.append(eventVerbatim);
		str.append(", displayNumber=");
		str.append(displayNumber);
		str.append(", onsetDate=");
		str.append(onsetDate);
		str.append(", eventOngoingFlag=");
		str.append(eventOngoingFlag);
		str.append(", endDate=");
		str.append(endDate);
		str.append(", seriousFlag=");
		str.append(seriousFlag);
		str.append(", hospitalizationFlag=");
		str.append(hospitalizationFlag);
		str.append(", medicallySignificantFlag=");
		str.append(medicallySignificantFlag);
		str.append(", patientDiedFlag=");
		str.append(patientDiedFlag);
		str.append(", lifeThreateningFlag=");
		str.append(lifeThreateningFlag);
		str.append(", disabilityFlag=");
		str.append(disabilityFlag);
		str.append(", congenitalAnomalyFlag=");
		str.append(congenitalAnomalyFlag);
		str.append(", hospitalizedFromDate=");
		str.append(hospitalizedFromDate);
		str.append(", hospitalizedToDate=");
		str.append(hospitalizedToDate);
		str.append(", eventOutcome=");
		str.append(eventOutcome);
		str.append(", companyCausality=");
		str.append(companyCausality);
		str.append(", reporterCausality=");
		str.append(reporterCausality);
		str.append(", priorHistoryOfEvent=");
		str.append(priorHistoryOfEvent);
		str.append(", priorHistoryComment=");
		str.append(priorHistoryComment);
		str.append(", relatedToProdComplaint=");
		str.append(relatedToProdComplaint);
		str.append(", relatedToLotNumber=");
		str.append(relatedToLotNumber);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", hospitalizationNature=");
		str.append(hospitalizationNature);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", OTHER_SERIOUSNESS_DESC=");
		str.append(OTHER_SERIOUSNESS_DESC);
		str.append(", OTHER_SERIOUSNESS_FLAG=");
		str.append(OTHER_SERIOUSNESS_FLAG);
		str.append(", EVENT_LLT=");
		str.append(EVENT_LLT);
		str.append(", EVENT_PT=");
		str.append(EVENT_PT);
		str.append(", EVENT_SOC=");
		str.append(EVENT_SOC);
		str.append(", EVENT_HLT=");
		str.append(EVENT_HLT);
		str.append(", EVENT_HLGT=");
		str.append(EVENT_HLGT);
		str.append(", EVENT_TERM=");
		str.append(EVENT_TERM);
		str.append(", DEATH_DATE=");
		str.append(DEATH_DATE);
		str.append(", AUTOPSY_PERFORMED=");
		str.append(AUTOPSY_PERFORMED);
		str.append("]");
		return str.toString();
	}
	
	public String getDEATH_DATE() {
		return DEATH_DATE;
	}

	public void setDEATH_DATE(String dEATH_DATE) {
		DEATH_DATE = dEATH_DATE;
	}

	public String getAUTOPSY_PERFORMED() {
		return AUTOPSY_PERFORMED;
	}

	public void setAUTOPSY_PERFORMED(String aUTOPSY_PERFORMED) {
		AUTOPSY_PERFORMED = aUTOPSY_PERFORMED;
	}

	public String getEVENT_TERM() {
		return EVENT_TERM;
	}

	public void setEVENT_TERM(String eVENT_TERM) {
		EVENT_TERM = eVENT_TERM;
	}

	public String getOTHER_SERIOUSNESS_FLAG() {
		return OTHER_SERIOUSNESS_FLAG;
	}

	public void setOTHER_SERIOUSNESS_FLAG(String oTHER_SERIOUSNESS_FLAG) {
		OTHER_SERIOUSNESS_FLAG = oTHER_SERIOUSNESS_FLAG;
	}

	public String getEVENT_LLT() {
		return EVENT_LLT;
	}

	public void setEVENT_LLT(String eVENT_LLT) {
		EVENT_LLT = eVENT_LLT;
	}

	public String getEVENT_PT() {
		return EVENT_PT;
	}

	public void setEVENT_PT(String eVENT_PT) {
		EVENT_PT = eVENT_PT;
	}

	public String getEVENT_SOC() {
		return EVENT_SOC;
	}

	public void setEVENT_SOC(String eVENT_SOC) {
		EVENT_SOC = eVENT_SOC;
	}

	public String getEVENT_HLT() {
		return EVENT_HLT;
	}

	public void setEVENT_HLT(String eVENT_HLT) {
		EVENT_HLT = eVENT_HLT;
	}

	public String getEVENT_HLGT() {
		return EVENT_HLGT;
	}

	public void setEVENT_HLGT(String eVENT_HLGT) {
		EVENT_HLGT = eVENT_HLGT;
	}

	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}

}
