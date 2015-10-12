package com.nrg.lau.beans;

import java.io.Serializable;

public class LauConPrevMedication implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long productId = 0;
	private long reportId = 0;
	private String productName = "";
	private String tradeName = "";
	private String patientDetailsID = "";
	private String displayNumber = "";
	private String productType = "";
	private String indicationVerbatim = "";
	private long transactionType = -1;
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long doseId = 0;
	private String startDate = "";
	private String stopDate = "";
	private String INTERNAL_LINK_ID="";
	private String ONGOING_FLAG = "";
	//private int DOSE = 0;
	private String DOSE = "";
	private String DOSE_UNIT = "";
	private String ROUTE = "";
	private String FREQUENCY = "";
	
	public long getProductId() {
		return productId;
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauNarrativeText.class.getName());
		str.append("[");
		str.append("productId=");
		str.append(productId);
		str.append("patientDetailsId=");
		str.append(patientDetailsID);
		str.append(", productName=");
		str.append(productName);
		str.append(", tradeName=");
		str.append(tradeName);
		str.append(", displayNumber=");
		str.append(displayNumber);
		str.append(", productType=");
		str.append(productType);
		str.append(", indicationVerbatim=");
		str.append(indicationVerbatim);
		str.append(", doseId=");
		str.append(doseId);
		str.append(", startDate=");
		str.append(startDate);
		str.append(", stopDate=");
		str.append(stopDate);
		str.append(", DOSE=");
		str.append(DOSE);
		str.append(", DOSE_UNIT=");
		str.append(DOSE_UNIT);
		str.append(", ONGOING_FLAG=");
		str.append(ONGOING_FLAG);
		str.append(", ROUTE=");
		str.append(ROUTE);
		str.append(", FREQUENCY=");
		str.append(FREQUENCY);
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
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getDisplayNumber() {
		return displayNumber;
	}
	public void setDisplayNumber(String displayNumber) {
		this.displayNumber = displayNumber;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getIndicationVerbatim() {
		return indicationVerbatim;
	}
	public void setIndicationVerbatim(String indicationVerbatim) {
		this.indicationVerbatim = indicationVerbatim;
	}
	public long getDoseId() {
		return doseId;
	}
	public void setDoseId(long doseId) {
		this.doseId = doseId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStopDate() {
		return stopDate;
	}
	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}
	public String getONGOING_FLAG() {
		return ONGOING_FLAG;
	}

	public void setONGOING_FLAG(String oNGOING_FLAG) {
		ONGOING_FLAG = oNGOING_FLAG;
	}

	/*public int getDOSE() {
		return DOSE;
	}

	public void setDOSE(int dOSE) {
		DOSE = dOSE;
	}*/
	
	public String getDOSE() {
		return DOSE;
	}

	public void setDOSE(String dOSE) {
		DOSE = dOSE;
	}

	public String getDOSE_UNIT() {
		return DOSE_UNIT;
	}

	

	public void setDOSE_UNIT(String dOSE_UNIT) {
		DOSE_UNIT = dOSE_UNIT;
	}

	public String getROUTE() {
		return ROUTE;
	}

	public void setROUTE(String rOUTE) {
		ROUTE = rOUTE;
	}

	public String getFREQUENCY() {
		return FREQUENCY;
	}

	public void setFREQUENCY(String fREQUENCY) {
		FREQUENCY = fREQUENCY;
	}

	public long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Object getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Object updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	public String getPatientDetailsID() {
		return patientDetailsID;
	}
	public void setPatientDetailsID(String patientDetailsID) {
		this.patientDetailsID = patientDetailsID;
	}

	public String getINTERNAL_LINK_ID() {
		return INTERNAL_LINK_ID;
	}

	public void setINTERNAL_LINK_ID(String iNTERNAL_LINK_ID) {
		INTERNAL_LINK_ID = iNTERNAL_LINK_ID;
	}
	
	
	
}
