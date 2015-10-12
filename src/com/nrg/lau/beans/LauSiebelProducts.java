package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nrg.lau.commons.Constants;
import com.nrg.lau.commons.IReportsConstants;

public class LauSiebelProducts implements Serializable {
	private static final long serialVersionUID = -2687030579053256879L;
	private long productId = 0;
	private String productName = "";
	private String productIndication = "";
	private long reportId = 0;
	private long doseId = 0;

	private String startDate = "";
	private String stopDate = "";
	private static long displayNumber = 1000;
	private long doseDisplayNumber = 1;
	
	private String dose = "";
	private String doseUnit = "";
	private String route = "";
	private String frequency = "";
	private String formulation = "";

	private String ongoingFlag = "";
	private SimpleDateFormat dtFormat = new SimpleDateFormat("mm/dd/yyyy");
	private SimpleDateFormat prmoFormat = new SimpleDateFormat("yyyymmdd");

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName.toUpperCase();
	}

	public String getProductIndication() {
		return productIndication;
	}

	public void setProductIndication(String productIndication) {
		
		if (productIndication.trim().equalsIgnoreCase(Constants.SIEBEL_STATUS_TYGRIS))
			this.productIndication = "MULTIPLE SCLEROSIS";
		else if (productIndication.trim().equalsIgnoreCase(Constants.SIEBEL_STATUS_INFORM))
			this.productIndication = "CROHN'S DISEASE";
	}

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
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
		if (startDate.length() > 0) {
			try {
				Date parseDate = dtFormat.parse(startDate);
				System.out.println("start date parsed");
				this.startDate = prmoFormat.format(parseDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.startDate = "";
			}
		}
	}

	public String getStopDate() {
		return stopDate;
	}

	public void setStopDate(String stopDate) {
		if (stopDate.length() > 0) {
			try {
				Date parseDate = dtFormat.parse(stopDate);

				this.stopDate = prmoFormat.format(parseDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.stopDate = "";
			}
		}
	}

	public long getDisplayNumber() {
		return displayNumber++;
	}

	public String getOngoingFlag() {
		return ongoingFlag;
	}

	public void setOngoingFlag(String ongoingFlag) {
		if(ongoingFlag.equals("Previous"))
			this.ongoingFlag = "N";
		else if (ongoingFlag.equals("Current"))
			this.ongoingFlag = "Y";
		else
			this.ongoingFlag = "";
				
		//Current, Previous
	}

	public long getDoseDisplayNumber() {
		return doseDisplayNumber;
	}

	public String toString() {
		StringBuffer str = new StringBuffer(LauRelatedReports.class.getName());
		str.append("[");
		str.append("productId=");
		str.append(productId);
		str.append(", productName=");
		str.append(productName);
		str.append(", productIndication=");
		str.append(productIndication);
		str.append(", reportId=");
		str.append(reportId);
		str.append("]");
		return str.toString();
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getDose() {
		return dose;
	}
	/**
	 * @return the doseUnit
	 */
	public String getDoseUnit() {
		return doseUnit;
	}

	/**
	 * @param doseUnit the doseUnit to set
	 */
	public void setDoseUnit(String doseUnit) {
		this.doseUnit = doseUnit;
	}

	/**
	 * @return the route
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * @param route the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the formulation
	 */
	public String getFormulation() {
		return formulation;
	}

	/**
	 * @param formulation the formulation to set
	 */
	public void setFormulation(String formulation) {
		this.formulation = formulation;
	}
}
