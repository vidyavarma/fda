package com.nrg.lau.regulatory.beans;

import java.io.Serializable;

import org.json.JSONArray;

public class LauFetchedRecords implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean fetchComplete = false;
	private JSONArray jason;	
	private String errorMsg = "";
	private boolean errorStatus = false;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public boolean isErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(boolean errorStatus) {
		this.errorStatus = errorStatus;
	}
	public boolean isFetchComplete() {
		return fetchComplete;
	}
	public void setFetchComplete(boolean fetchComplete) {
		this.fetchComplete = fetchComplete;
	}
	public JSONArray getJason() {
		return jason;
	}
	public void setJason(JSONArray jason) {
		this.jason = jason;
	}
	
	public String toString(){
		
		StringBuffer str = new StringBuffer(LauFetchedRecords.class.getName());		
		str.append("[");
		str.append("fetchComplete=");
		str.append(fetchComplete);
		str.append(", errorMsg=");
		str.append(errorMsg);
		str.append(", errorStatus=");
		str.append(errorStatus);
		str.append("]");
		return str.toString();
	}
}