package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nrg.lau.commons.IReportsConstants;

public class LauPregnancyOutcome implements Serializable {

	private static final long serialVersionUID = 3295780178561043517L;
	
	private long pregnancyOutcomeId = 0;
	private BigDecimal displayNumber = IReportsConstants.ZERO;
	private String displayNumber2 = "";
	private String sex = "";
	private String weight = "";
	private String length = "";
	private String length2 = "";
	private String headCircumference = "";
	private String apgarScore1 = "";
	private String apgarScore12 = "";
	private String apgarScore2 = "";
	private String abnormalitiesFlag = "";
	private String abnormalitiesComments = "";
	private String deliveryDate = "";
	private String deliveryTime = "";
	private String deliveryMode = "";
	private String deliveryDetails = "";
	private String deliveryComplications = "";
	private String placentaCondition = "";
	private String placentaConditionComments = "";
	private String deliveryComments = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long reportId = 0;
	private long transactionType = -1;
	

	private String pregOutcome = "";
	private String pregOutcomeComments= "";
	private String pregOutcomeDate= "";
	private String pregTermAtOutcome= "";
	private String pregTermAtOutcomeUnits= "";
	


	public long getPregnancyOutcomeId() {
		return this.pregnancyOutcomeId;
	}

	public void setPregnancyOutcomeId(long pregnancyOutcomeId) {
		this.pregnancyOutcomeId = pregnancyOutcomeId;
	}

	public BigDecimal getDisplayNumber() {
		return this.displayNumber;
	}

	public void setDisplayNumber(BigDecimal displayNumber) {
		this.displayNumber = displayNumber;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAbnormalitiesFlag() {
		return this.abnormalitiesFlag;
	}

	public void setAbnormalitiesFlag(String abnormalitiesFlag) {
		this.abnormalitiesFlag = abnormalitiesFlag;
	}

	public String getAbnormalitiesComments() {
		return this.abnormalitiesComments;
	}

	public void setAbnormalitiesComments(String abnormalitiesComments) {
		this.abnormalitiesComments = abnormalitiesComments;
	}

	public String getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getDeliveryDetails() {
		return this.deliveryDetails;
	}

	public void setDeliveryDetails(String deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}

	public String getPlacentaCondition() {
		return this.placentaCondition;
	}

	public void setPlacentaCondition(String placentaCondition) {
		this.placentaCondition = placentaCondition;
	}

	public String getPlacentaConditionComments() {
		return this.placentaConditionComments;
	}

	public void setPlacentaConditionComments(String placentaConditionComments) {
		this.placentaConditionComments = placentaConditionComments;
	}

	public String getDeliveryComments() {
		return this.deliveryComments;
	}

	public void setDeliveryComments(String deliveryComments) {
		this.deliveryComments = deliveryComments;
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
	/**
	 * @return the pregOutcome
	 */
	public String getPregOutcome() {
		return pregOutcome;
	}

	/**
	 * @param pregOutcome the pregOutcome to set
	 */
	public void setPregOutcome(String pregOutcome) {
		this.pregOutcome = pregOutcome;
	}

	/**
	 * @return the pregOutcomeComments
	 */
	public String getPregOutcomeComments() {
		return pregOutcomeComments;
	}

	/**
	 * @param pregOutcomeComments the pregOutcomeComments to set
	 */
	public void setPregOutcomeComments(String pregOutcomeComments) {
		this.pregOutcomeComments = pregOutcomeComments;
	}

	/**
	 * @return the pregOutcomeDate
	 */
	public String getPregOutcomeDate() {
		return pregOutcomeDate;
	}

	/**
	 * @param pregOutcomeDate the pregOutcomeDate to set
	 */
	public void setPregOutcomeDate(String pregOutcomeDate) {
		this.pregOutcomeDate = pregOutcomeDate;
	}

	/**
	 * @return the pregTermAtOutcome
	 */
	public String getPregTermAtOutcome() {
		return pregTermAtOutcome;
	}

	/**
	 * @param pregTermAtOutcome the pregTermAtOutcome to set
	 */
	public void setPregTermAtOutcome(String pregTermAtOutcome) {
		this.pregTermAtOutcome = pregTermAtOutcome;
	}

	/**
	 * @return the pregTermAtOutcomeUnits
	 */
	public String getPregTermAtOutcomeUnits() {
		return pregTermAtOutcomeUnits;
	}

	/**
	 * @param pregTermAtOutcomeUnits the pregTermAtOutcomeUnits to set
	 */
	public void setPregTermAtOutcomeUnits(String pregTermAtOutcomeUnits) {
		this.pregTermAtOutcomeUnits = pregTermAtOutcomeUnits;
	}
	


	public void setDisplayNumber2(String displayNumber2) {
		if(displayNumber2.trim().length() > 0)	{
			this.displayNumber = new BigDecimal(displayNumber2);
		}else this.displayNumber = IReportsConstants.ZERO;
	}

	public long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}

	public String getDeliveryComplications() {
		return deliveryComplications;
	}

	public void setDeliveryComplications(String deliveryComplications) {
		this.deliveryComplications = deliveryComplications;
	}
	public String toString() {
		StringBuffer str = new StringBuffer(LauPregnancyOutcome.class.getName());
		str.append("[");
		str.append("pregnancyOutcomeId=");
		str.append(pregnancyOutcomeId);
		str.append(", displayNumber=");
		str.append(displayNumber);
		str.append(", sex=");
		str.append(sex);
		str.append(", weight=");
		str.append(weight);
		str.append(", length=");
		str.append(length);
		str.append(", headCircumference=");
		str.append(headCircumference);
		str.append(", apgarScore1=");
		str.append(apgarScore1);
		str.append(", apgarScore2=");
		str.append(apgarScore2);
		str.append(", abnormalitiesFlag=");
		str.append(abnormalitiesFlag);
		str.append(", abnormalitiesComments=");
		str.append(abnormalitiesComments);
		str.append(", deliveryDate=");
		str.append(deliveryDate);
		str.append(", deliveryTime=");
		str.append(deliveryTime);
		str.append(", deliveryMode=");
		str.append(deliveryMode);
		str.append(", deliveryDetails=");
		str.append(deliveryDetails);
		str.append(", placentaCondition=");
		str.append(placentaCondition);
		str.append(", placentaConditionComments=");
		str.append(placentaConditionComments);
		str.append(", deliveryComments=");
		str.append(deliveryComments);
		
		str.append(",pregOutcome=");
		str.append(pregOutcome);
		
		str.append(", pregOutcomeComments=");
		str.append(pregOutcomeComments);
		
		str.append(", pregOutcomeDate=");
		str.append(pregOutcomeDate);
		
		str.append(", pregTermAtOutcome=");
		str.append(pregTermAtOutcome);
		
		str.append(", pregTermAtOutcomeUnits=");
		str.append(deliveryComments);
		

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

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getLength2() {
		return length2;
	}

	public void setLength2(String length2) {
		this.length2 = length2;
	}

	public String getHeadCircumference() {
		return headCircumference;
	}

	public void setHeadCircumference(String headCircumference) {
		this.headCircumference = headCircumference;
	}

	public String getApgarScore1() {
		return apgarScore1;
	}

	public void setApgarScore1(String apgarScore1) {
		this.apgarScore1 = apgarScore1;
	}

	public String getApgarScore12() {
		return apgarScore12;
	}

	public void setApgarScore12(String apgarScore12) {
		this.apgarScore12 = apgarScore12;
	}

	public String getApgarScore2() {
		return apgarScore2;
	}

	public void setApgarScore2(String apgarScore2) {
		this.apgarScore2 = apgarScore2;
	}

	public String getDisplayNumber2() {
		return displayNumber2;
	}
}
