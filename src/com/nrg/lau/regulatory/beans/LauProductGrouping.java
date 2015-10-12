package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauProductGrouping implements Serializable {
	
	private static Logger log	= Logger.getLogger(LauProductGrouping.class);
	private static final long serialVersionUID = 1L;
	
	private String prodGroup = "";
	private String prodCode = "";
	private String prodCodeType = "";
	private String medicalSpeciality = "";
	private String description = "";
	
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	
	public LauProductGrouping(){
		super();
	}
	

	public String getProdGroup() {
		return prodGroup;
	}





	public void setProdGroup(String prodGroup) {
		this.prodGroup = prodGroup;
	}





	public String getProdCode() {
		return prodCode;
	}





	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}





	public String getProdCodeType() {
		return prodCodeType;
	}





	public void setProdCodeType(String prodCodeType) {
		this.prodCodeType = prodCodeType;
	}





	public String getMedicalSpeciality() {
		return medicalSpeciality;
	}





	public void setMedicalSpeciality(String medicalSpeciality) {
		this.medicalSpeciality = medicalSpeciality;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
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





	public long getTransactionType() {
		return transactionType;
	}





	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer(LauProductGrouping.class.getName());
		
		str.append("[");
		str.append(",prodGroup=");
		str.append(prodGroup);
		str.append(", prodCode=");
		str.append(prodCode);
		str.append(",prodCodeType=");
		str.append(prodCodeType);
		str.append(", medicalSpeciality=");
		str.append(medicalSpeciality);
		str.append(", description=");
		str.append(description);
		str.append(", transactionType=");
		str.append(transactionType);
		str.append(", updateUserId=");
		str.append(updateUserId);
		str.append(", updateTimeStamp=");
		str.append(updateTimeStamp);
		str.append("]");
		return str.toString();
	}
	
	
	
}
	
