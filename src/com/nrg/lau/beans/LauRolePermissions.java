package com.nrg.lau.beans;

import java.io.Serializable;

public class LauRolePermissions implements Serializable {
	
	private static final long serialVersionUID 	= 1L;
	private String APPLICATION_PERMISSION 		= "";
	private String USER_ROLE 					= "";
	private String TRANSACTION_TYPE 			= "";
	private String UPDATE_USER_ID 				= "";
	private String UPDATE_TIMESTAMP				= "";
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauRolePermissions.class.getName());
		str.append("[");
		str.append("APPLICATION_PERMISSION=");
		str.append(APPLICATION_PERMISSION);
		str.append(", USER_ROLE=");
		str.append(USER_ROLE);
		str.append(", TRANSACTION_TYPE=");
		str.append(TRANSACTION_TYPE);		
		str.append(", UPDATE_USER_ID=");
		str.append(UPDATE_USER_ID);
		str.append(", UPDATE_TIMESTAMP=");
		str.append(UPDATE_TIMESTAMP);
		str.append("]");
		return str.toString();
	}
	
	public String getAPPLICATION_PERMISSION() {
		return APPLICATION_PERMISSION;
	}
	public void setAPPLICATION_PERMISSION(String aPPLICATIONPERMISSION) {
		APPLICATION_PERMISSION = aPPLICATIONPERMISSION;
	}
	public String getUSER_ROLE() {
		return USER_ROLE;
	}
	public void setUSER_ROLE(String uSERROLE) {
		USER_ROLE = uSERROLE;
	}
	public String getTRANSACTION_TYPE() {
		return TRANSACTION_TYPE;
	}
	public void setTRANSACTION_TYPE(String tRANSACTIONTYPE) {
		TRANSACTION_TYPE = tRANSACTIONTYPE;
	}
	public String getUPDATE_USER_ID() {
		return UPDATE_USER_ID;
	}
	public void setUPDATE_USER_ID(String uPDATEUSERID) {
		UPDATE_USER_ID = uPDATEUSERID;
	}
	public String getUPDATE_TIMESTAMP() {
		return UPDATE_TIMESTAMP;
	}
	public void setUPDATE_TIMESTAMP(String uPDATETIMESTAMP) {
		UPDATE_TIMESTAMP = uPDATETIMESTAMP;
	}

	

}
