package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nrg.lau.commons.IReportsConstants;

public class LauDosing implements Serializable {

	private long doseId = 0;
	private long reportId = 0;
	private String startDate = "";
	private String stopDate = "";
	private BigDecimal displayNumber = IReportsConstants.ZERO;
	private String displayNumber2 = "";
	private String ongoingFlag = "";
	private String duration = "";
	private String durationUnits = "";
	private String productType = "";
	private String productDisplayNumber = "";

	private String dose = "";
	private String doseUnit = "";
	private String route = "";
	private String frequency = "";
	private String formulation = "";
	private String lotNumber = "";
	private String lotExpirationDate = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long productId = 0;
	private String productName = "";
	private long transactionType = -1;

	private static final long serialVersionUID = 1L;

	public long getDoseId() {
		return this.doseId;
	}

	public void setDoseId(long doseId) {
		this.doseId = doseId;
	}

	public long getReportId() {
		return this.reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStopDate() {
		return this.stopDate;
	}

	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}

	public BigDecimal getDisplayNumber() {
		return this.displayNumber;
	}

	public void setDisplayNumber(BigDecimal displayNumber) {
		this.displayNumber = displayNumber;
	}

	public String getOngoingFlag() {
		return this.ongoingFlag;
	}

	public void setOngoingFlag(String ongoingFlag) {
		this.ongoingFlag = ongoingFlag;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDurationUnits() {
		return this.durationUnits;
	}

	public void setDurationUnits(String durationUnits) {
		this.durationUnits = durationUnits;
	}

	public String getDose() {
		return this.dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getDoseUnit() {
		return this.doseUnit;
	}

	public void setDoseUnit(String doseUnit) {
		this.doseUnit = doseUnit;
	}

	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFormulation() {
		return this.formulation;
	}

	public void setFormulation(String formulation) {
		this.formulation = formulation;
	}

	public String getLotNumber() {
		return this.lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public String getLotExpirationDate() {
		return this.lotExpirationDate;
	}

	public void setLotExpirationDate(String lotExpirationDate) {
		this.lotExpirationDate = lotExpirationDate;
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

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDisplayNumber() {
		return productDisplayNumber;
	}

	public void setProductDisplayNumber(String productDisplayNumber) {
		this.productDisplayNumber = productDisplayNumber;
	}

	public String toString() {
		StringBuffer str = new StringBuffer(LauDosing.class.getName());
		str.append("[");
		str.append("doseId=");
		str.append(doseId);
		str.append(", reportId=");
		str.append(reportId);
		str.append(", startDate=");
		str.append(startDate);
		str.append(", stopDate=");
		str.append(stopDate);
		str.append(", displayNumber=");
		str.append(displayNumber);
		str.append(", ongoingFlag=");
		str.append(ongoingFlag);
		str.append(", duration=");
		str.append(duration);
		str.append(", durationUnits=");
		str.append(durationUnits);
		str.append(", dose=");
		str.append(dose);
		str.append(", doseUnit=");
		str.append(doseUnit);
		str.append(", route=");
		str.append(route);
		str.append(", frequency=");
		str.append(frequency);
		str.append(", formulation=");
		str.append(formulation);
		str.append(", lotNumber=");
		str.append(lotNumber);
		str.append(", lotExpirationDate=");
		str.append(lotExpirationDate);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append(", productId=");
		str.append(productId);
		str.append(", productName=");
		str.append(productName);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", productType=");
		str.append(productType);
		str.append(", productDisplayNumber=");
		str.append(productDisplayNumber);
		str.append("]");
		return str.toString();
	}
	
	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	
	public void setDisplayNumber2(String displayNumber2) {
		if (displayNumber2.trim().length() > 0) {
			this.displayNumber = new BigDecimal(displayNumber2);
		} else
			this.displayNumber = IReportsConstants.ZERO;
	}


	public String getDisplayNumber2() {
		return displayNumber2;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
}
