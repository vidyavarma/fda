package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nrg.lau.commons.IReportsConstants;

public class LauLabTests implements Serializable {

	private long labTestId = 0;
	private BigDecimal displayNbr = IReportsConstants.ZERO;
	private String displayNbr2 = "";
	private String testDate = "";
	private String testTime = "";
	private String testResult = "";
	private String testUnits = "";
	private String testUpperBound = "";
	private String testLowerBound = "";
	private String testComments = "";
	private String testNameVerbatim = "";
	private String updateUserId = "";
	private Object updateTimeStamp;
	private long reportId = 0;
	private long transactionType = -1;

	private static final long serialVersionUID = 1L;

	public long getLabTestId() {
		return this.labTestId;
	}

	public void setLabTestId(long labTestId) {
		this.labTestId = labTestId;
	}

	public BigDecimal getDisplayNbr() {
		return this.displayNbr;
	}

	public void setDisplayNbr(BigDecimal displayNbr) {
		this.displayNbr = displayNbr;
	}
	
	public String getDisplayNbr2() {
		return this.displayNbr2;
	}

	public void setDisplayNbr2(String displayNbr2) {
		if(displayNbr2.trim().length() > 0)	{
			this.displayNbr = new BigDecimal(displayNbr2);
		}else this.displayNbr = IReportsConstants.ZERO;
	}
	
	public String getTestDate() {
		return this.testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTestTime() {
		return this.testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getTestResult() {
		return this.testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getTestUnits() {
		return this.testUnits;
	}

	public void setTestUnits(String testUnits) {
		this.testUnits = testUnits;
	}

	public String getTestUpperBound() {
		return this.testUpperBound;
	}

	public void setTestUpperBound(String testUpperBound) {
		this.testUpperBound = testUpperBound;
	}

	public String getTestLowerBound() {
		return this.testLowerBound;
	}

	public void setTestLowerBound(String testLowerBound) {
		this.testLowerBound = testLowerBound;
	}

	public String getTestComments() {
		return this.testComments;
	}

	public void setTestComments(String testComments) {
		this.testComments = testComments;
	}

	public String getTestNameVerbatim() {
		return this.testNameVerbatim;
	}

	public void setTestNameVerbatim(String testNameVerbatim) {
		this.testNameVerbatim = testNameVerbatim;
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
		StringBuffer str = new StringBuffer(LauLabTests.class.getName());
		str.append("[");
		str.append("labTestId=");
		str.append(labTestId);
		str.append(", displayNbr=");
		str.append(displayNbr);
		str.append(", testDate=");
		str.append(testDate);
		str.append(", testTime=");
		str.append(testTime);
		str.append(", testResult=");
		str.append(testResult);
		str.append(", testUnits=");
		str.append(testUnits);
		str.append(", testUpperBound=");
		str.append(testUpperBound);
		str.append(", testLowerBound=");
		str.append(testLowerBound);
		str.append(", testComments=");
		str.append(testComments);
		str.append(", testNameVerbatim=");
		str.append(testNameVerbatim);
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
