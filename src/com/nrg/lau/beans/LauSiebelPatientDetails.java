package com.nrg.lau.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nrg.lau.commons.Constants;

public class LauSiebelPatientDetails implements Serializable{
	private static final long serialVersionUID = -3701868255495768992L;
	private long patientDetailsId = 0;
	private long reportId = 0;
	private String ptTitle = "";
	private String ptFirstName = "";
	private String ptMiddleName = "";
	private String ptLastName = "";
	private String ptSex = "";
	private String ptAddress1 = "";
	private String ptAddress2 = "";
	private String ptCity = "";
	private String ptState = "";
	private String ptPostalCode = "";
	private String ptCountry = "";
	private String ptPhoneNumber = "";
	private String ptEmail = "";
	private String ptDOB = "";
	private String ptIdentifier1 = "";
	private String ptIndicator1 = "";
	private String ptIndicator2 = "";
	private String ptStudyEnrollmentStatus = "";
	private String ptStudyEnrollmentDate = "";
	private String ptStudyWithdrawnDate = "";
	private String marketBIIB = "";
	private SimpleDateFormat dtFormat = new SimpleDateFormat("mm/dd/yyyy");
	private SimpleDateFormat prmoFormat = new SimpleDateFormat("yyyymmdd");

	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	public String getPtTitle() {
		return ptTitle;
	}
	public void setPtTitle(String ptTitle) {
		this.ptTitle = ptTitle;
	}
	public String getPtFirstName() {
		return ptFirstName;
	}
	public void setPtFirstName(String ptFirstName) {
		this.ptFirstName = ptFirstName;
	}
	public String getPtMiddleName() {
		return ptMiddleName;
	}
	public void setPtMiddleName(String ptMiddleName) {
		this.ptMiddleName = ptMiddleName;
	}
	public String getPtLastName() {
		return ptLastName;
	}
	public void setPtLastName(String ptLastName) {
		this.ptLastName = ptLastName;
	}
	public String getPtSex() {
		return ptSex;
	}
	public void setPtSex(String ptSex) {
		if (ptSex.equalsIgnoreCase("M"))
			this.ptSex = "M";
		else if (ptSex.equalsIgnoreCase("F"))
			this.ptSex = "F";
		else
			this.ptSex = "U";

	}
	public String getPtAddress1() {
		return ptAddress1;
	}
	public void setPtAddress1(String ptAddress1) {
		this.ptAddress1 = ptAddress1;
	}
	public String getPtAddress2() {
		return ptAddress2;
	}
	public void setPtAddress2(String ptAddress2) {
		this.ptAddress2 = ptAddress2;
	}
	public String getPtCity() {
		return ptCity;
	}
	public void setPtCity(String ptCity) {
		this.ptCity = ptCity;
	}
	public String getPtState() {
		return ptState;
	}
	public void setPtState(String ptState) {
		this.ptState = ptState;
	}
	public String getPtPostalCode() {
		return ptPostalCode;
	}
	public void setPtPostalCode(String ptPostalCode) {
		this.ptPostalCode = ptPostalCode;
	}
	public String getPtCountry() {
		return ptCountry;
	}
	public void setPtCountry(String ptCountry) {
		if (ptCountry.length() > 0) {
			if (ptCountry.equalsIgnoreCase("United States of America"))
					this.ptCountry = "USA";
			else if (ptCountry.equalsIgnoreCase("Puerto Rico"))
					this.ptCountry = "PRI";
			else if (ptCountry.equalsIgnoreCase("Canada"))
					this.ptCountry = "CAN";
			else
				this.ptCountry = ptCountry;
		}
		
	}
	public String getPtPhoneNumber() {
		return ptPhoneNumber;
	}
	public void setPtPhoneNumber(String ptPhoneNumber) {
		this.ptPhoneNumber = ptPhoneNumber;
	}
	public String getPtEmail() {
		return ptEmail;
	}
	public void setPtEmail(String ptEmail) {
		this.ptEmail = ptEmail;
	}
	public String getPtDOB() {
		return ptDOB;
	}
	public void setPtDOB(String ptDOB) {
		if (ptDOB.length() > 1) {
			try {
				Date parseDate = dtFormat.parse(ptDOB);
				System.out.println("start date parsed");
				this.ptDOB = prmoFormat.format(parseDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.ptDOB = "";
			}
		}
	}
	public String getPtIdentifier1() {
		return ptIdentifier1;
	}
	public void setPtIdentifier1(String ptIdentifier1) {
		this.ptIdentifier1 = ptIdentifier1;
	}
	public String getPtIndicator1() {
		return ptIndicator1;
	}
	public void setPtIndicator1(String ptIndicator1) {
		
		if (this.marketBIIB.equalsIgnoreCase(Constants.SIEBEL_STATUS_TYGRIS)&& 
				(ptIndicator1.equalsIgnoreCase("Enrolled")|| ptIndicator1.equalsIgnoreCase("Withdrawn")))
			this.ptIndicator1 = "Y";
		else
			this.ptIndicator1 = ptIndicator1;
	}
	public String getPtIndicator2() {
		return ptIndicator2;
	}
	public void setPtIndicator2(String ptIndicator2) {
		if (this.marketBIIB.equalsIgnoreCase(Constants.SIEBEL_STATUS_INFORM)&& 
				(ptIndicator2.equalsIgnoreCase("Enrolled")|| ptIndicator2.equalsIgnoreCase("Withdrawn")))
			this.ptIndicator2 = "Y";
		else
			this.ptIndicator2 = ptIndicator2;
	}
	public String getPtStudyEnrollmentStatus() {
		return ptStudyEnrollmentStatus;
	}
	public void setPtStudyEnrollmentStatus(String ptStudyEnrollmentStatus) {
		
			this.ptStudyEnrollmentStatus = ptStudyEnrollmentStatus.trim().toUpperCase();
	}
	public String getPtStudyEnrollmentDate() {
		return ptStudyEnrollmentDate;
		
	}
	public void setPtStudyEnrollmentDate(String ptStudyEnrollmentDate) {

		if (ptStudyEnrollmentDate.length() > 1 && this.ptStudyEnrollmentStatus.equals("ENROLLED")) {
			try {
				Date parseDate = dtFormat.parse(ptStudyEnrollmentDate );
				System.out.println("ENROLL date parsed");
				this.ptStudyEnrollmentDate = prmoFormat.format(parseDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.ptStudyEnrollmentDate  = "";
			}
		}
		
	}
	public String getPtStudyWithdrawnDate() {
		return ptStudyWithdrawnDate;
	}
	public void setPtStudyWithdrawnDate(String ptStudyWithdrawnDate) {
		if (ptStudyWithdrawnDate.length() > 1 && this.ptStudyEnrollmentStatus.equals("WITHDRAWN")) {
			try {
				Date parseDate = dtFormat.parse(ptStudyWithdrawnDate);
				System.out.println("withdrawn date parsed");
				this.ptStudyWithdrawnDate = prmoFormat.format(parseDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.ptStudyWithdrawnDate = "";
			}
		}
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauRelatedReports.class.getName());
		str.append("[");
		str.append("patientDetailsId=");
		str.append(patientDetailsId); 
		str.append(", reportId=");
		str.append(reportId); 
		str.append(", ptTitle=");
		str.append(ptTitle);
		str.append(", ptFirstName=");
		str.append(ptFirstName);
		str.append(", ptMiddleName=");
		str.append(ptMiddleName);
		str.append(", ptLastName=");
		str.append(ptLastName);
		str.append(", ptSex=");
		str.append(ptSex);
		str.append(", ptAddress1=");
		str.append(ptAddress1);
		str.append(", ptAddress2=");
		str.append(ptAddress2);
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
		str.append(", ptEmail=");
		str.append(ptEmail);
		str.append(", ptDOB=");
		str.append(ptDOB);
		str.append(", ptIdentifier1=");
		str.append(ptIdentifier1);
		str.append(", ptIndicator1=");
		str.append(ptIndicator1);
		str.append(", ptIndicator2=");
		str.append(ptIndicator2);
		str.append(", ptStudyEnrollmentStatus=");
		str.append(ptStudyEnrollmentStatus);
		str.append(", ptStudyEnrollmentDate=");
		str.append(ptStudyEnrollmentDate);
		str.append(", ptStudyWithdrawnDate=");
		str.append(ptStudyWithdrawnDate);
		str.append("]");
		return str.toString();
	}
	public long getPatientDetailsId() {
		return patientDetailsId;
	}
	public void setPatientDetailsId(long patientDetailsId) {
		this.patientDetailsId = patientDetailsId;
	}
	public void setMarketBIIB(String marketBIIB) {
		this.marketBIIB = marketBIIB;
	}
	public String getMarketBIIB() {
		return marketBIIB;
	}
}
