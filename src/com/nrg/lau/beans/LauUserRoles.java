package com.nrg.lau.beans;

import java.io.Serializable;

public class LauUserRoles implements Serializable {
	
	private static final long serialVersionUID 	= 1L;	
	private String USER_ROLE 					= "";
	private String USER_ROLE_DESCRIPTION		= "";
	private String USER_ID 						= "";
	private String TRANSACTION_TYPE 			= "";
	private String UPDATE_USER_ID 				= "";
	private String UPDATE_TIMESTAMP				= "";
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauUserRoles.class.getName());
		str.append("[");
		str.append("USER_ROLE=");
		str.append(USER_ROLE);
		str.append(", USER_ROLE_DESCRIPTION=");
		str.append(USER_ROLE_DESCRIPTION);
		str.append(", USER_ID=");
		str.append(USER_ID);
		str.append(", TRANSACTION_TYPE=");
		str.append(TRANSACTION_TYPE);		
		str.append(", UPDATE_USER_ID=");
		str.append(UPDATE_USER_ID);
		str.append(", UPDATE_TIMESTAMP=");
		str.append(UPDATE_TIMESTAMP);
		str.append("]");
		return str.toString();
	}	
	
	public String getUSER_ROLE() {
		return USER_ROLE;
	}
	public void setUSER_ROLE(String uSERROLE) {
		USER_ROLE = uSERROLE;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSERID) {
		USER_ID = uSERID;
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

	public String getUSER_ROLE_DESCRIPTION() {
		return USER_ROLE_DESCRIPTION;
	}

	public void setUSER_ROLE_DESCRIPTION(String uSERROLEDESCRIPTION) {
		USER_ROLE_DESCRIPTION = uSERROLEDESCRIPTION;
	}	

}
