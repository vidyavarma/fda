package com.nrg.lau.beans;

import java.io.Serializable;
public class LauUserGroupAccess implements Serializable {

	private static final long serialVersionUID 	= 1L;
	private String ACCESSIBLE_USER_GROUP_ID 	= "";
	private String TRANSACTION_TYPE 			= "";
	private String USER_GROUP_ID 				= "";
	private String VIEW_REPORTS 				= "";
	private String EDIT_REPORTS 				= "";
	private String VIEW_PRIVATE_DATA			= "";
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauUserGroupAccess.class.getName());
		str.append("[");
		str.append("ACCESSIBLE_USER_GROUP_ID=");
		str.append(ACCESSIBLE_USER_GROUP_ID);
		str.append(", TRANSACTION_TYPE=");
		str.append(TRANSACTION_TYPE);
		str.append(", USER_GROUP_ID=");
		str.append(USER_GROUP_ID);
		str.append(", USER_GROUP_ID=");
		str.append(VIEW_REPORTS);
		str.append(", VIEW_REPORTS=");
		str.append(EDIT_REPORTS);
		str.append(", EDIT_REPORTS=");
		str.append(VIEW_PRIVATE_DATA);
		str.append(", VIEW_PRIVATE_DATA=");	
		str.append("]");
		return str.toString();
	}	
	
	public String getACCESSIBLE_USER_GROUP_ID() {
		return ACCESSIBLE_USER_GROUP_ID;
	}
	public void setACCESSIBLE_USER_GROUP_ID(String aCCESSIBLEUSERGROUPID) {
		ACCESSIBLE_USER_GROUP_ID = aCCESSIBLEUSERGROUPID;
	}
	public String getTRANSACTION_TYPE() {
		return TRANSACTION_TYPE;
	}
	public void setTRANSACTION_TYPE(String tRANSACTIONTYPE) {
		TRANSACTION_TYPE = tRANSACTIONTYPE;
	}
	public String getUSER_GROUP_ID() {
		return USER_GROUP_ID;
	}
	public void setUSER_GROUP_ID(String uSERGROUPID) {
		USER_GROUP_ID = uSERGROUPID;
	}
	public String getVIEW_REPORTS() {
		return VIEW_REPORTS;
	}
	public void setVIEW_REPORTS(String vIEWREPORTS) {
		VIEW_REPORTS = vIEWREPORTS;
	}
	public String getEDIT_REPORTS() {
		return EDIT_REPORTS;
	}
	public void setEDIT_REPORTS(String eDITREPORTS) {
		EDIT_REPORTS = eDITREPORTS;
	}

	public String getVIEW_PRIVATE_DATA() {
		return VIEW_PRIVATE_DATA;
	}

	public void setVIEW_PRIVATE_DATA(String vIEWPRIVATEDATA) {
		VIEW_PRIVATE_DATA = vIEWPRIVATEDATA;
	}	

}
