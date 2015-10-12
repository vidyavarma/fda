package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LauUserProcodes implements Serializable {
	
	private static Logger log	= Logger.getLogger(LauUserProcodes.class);
	private static final long serialVersionUID = 1L;
	
	private long prodID;
	private String userID = "";
	private String prodGroup = "";
	private String prodCode = "";
	private String label = "";
	private String type = "";
	
	private String 	updateUserId = "";
	private Object updateTimeStamp;
	private long transactionType = -1;
	
	public LauUserProcodes(){
		super();
	}
	
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public long getProdID() {
		return prodID;
	}
	
	public void setProdID(long prodID) {
		this.prodID = prodID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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
		StringBuffer str = new StringBuffer(LauUserProcodes.class.getName());
		
		str.append("[");
		str.append("prodID=");
		str.append(prodID);
		str.append(", userID=");
		str.append(userID);
		str.append(",prodGroup=");
		str.append(prodGroup);
		str.append(", prodCode=");
		str.append(prodCode);
		str.append(",label=");
		str.append(label);
		str.append(", type=");
		str.append(type);
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
	
