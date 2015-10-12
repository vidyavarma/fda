package com.nrg.lau.beans;

import java.io.Serializable;

public class LauUsers implements Serializable {
	
	private static final long serialVersionUID 	= 1L;
	private String USER_GROUP_ID 		= "";
	private String USER_ID 				= "";
	private String USER_NAME 			= "";
	private String INACTIVE_USER		= "";
	private String USER_EMAIL 			= "";
	private String USER_DEPARTMENT 		= "";
	private String LDAP_EMAIL_ID 		= "";
	private String LDAP_CORPORATE_ID 	= "";	
	private String AE_SYSTEM_USER_ID 	= "";
	private String TRANSACTION_TYPE 	= "";
	private String UPDATE_USER_ID 		= "";
	private String UPDATE_TIMESTAMP		= "";
	private String USER_ORGANIZATION	= ""; 
	private String USER_PHONE_NUMBER	= ""; 
	private String USER_COMMENTS		= ""; 
	private String USER_SIGNATURE		= ""; 
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauUsers.class.getName());
		str.append("[");
		str.append("USER_GROUP_ID=");
		str.append(USER_GROUP_ID);
		str.append(", USER_ID=");
		str.append(USER_ID);
		str.append(", USER_NAME=");
		str.append(USER_NAME);
		str.append(", USER_EMAIL=");
		str.append(INACTIVE_USER);
		str.append(", INACTIVE_USER=");
		str.append(USER_EMAIL);
		str.append(", USER_DEPARTMENT=");
		str.append(USER_DEPARTMENT);		
		str.append(", LDAP_EMAIL_ID=");
		str.append(LDAP_EMAIL_ID);
		str.append(", LDAP_CORPORATE_ID=");
		str.append(LDAP_CORPORATE_ID);
		str.append(", AE_SYSTEM_USER_ID=");
		str.append(AE_SYSTEM_USER_ID);
		str.append(", USER_ORGANIZATION=");
		str.append(USER_ORGANIZATION);
		str.append(", USER_PHONE_NUMBER=");
		str.append(USER_PHONE_NUMBER);
		str.append(", USER_COMMENTS=");
		str.append(USER_COMMENTS);
		str.append(", USER_SIGNATURE=");
		str.append(USER_SIGNATURE);
		str.append(", TRANSACTION_TYPE=");
		str.append(TRANSACTION_TYPE);		
		str.append(", UPDATE_USER_ID=");
		str.append(UPDATE_USER_ID);
		str.append(", UPDATE_TIMESTAMP=");
		str.append(UPDATE_TIMESTAMP);
		str.append("]");
		return str.toString();
	}	
	
	public String getUSER_GROUP_ID() {
		return USER_GROUP_ID;
	}
	public void setUSER_GROUP_ID(String uSERGROUPID) {
		USER_GROUP_ID = uSERGROUPID;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSERID) {
		USER_ID = uSERID;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSERNAME) {
		USER_NAME = uSERNAME;
	}
	public String getUSER_EMAIL() {
		return USER_EMAIL;
	}
	public void setUSER_EMAIL(String uSEREMAIL) {
		USER_EMAIL = uSEREMAIL;
	}
	public String getUSER_DEPARTMENT() {
		return USER_DEPARTMENT;
	}
	public void setUSER_DEPARTMENT(String uSERDEPARTMENT) {
		USER_DEPARTMENT = uSERDEPARTMENT;
	}
	public String getLDAP_EMAIL_ID() {
		return LDAP_EMAIL_ID;
	}
	public void setLDAP_EMAIL_ID(String lDAPEMAILID) {
		LDAP_EMAIL_ID = lDAPEMAILID;
	}
	public String getLDAP_CORPORATE_ID() {
		return LDAP_CORPORATE_ID;
	}
	public void setLDAP_CORPORATE_ID(String lDAPCORPORATEID) {
		LDAP_CORPORATE_ID = lDAPCORPORATEID;
	}
	public String getAE_SYSTEM_USER_ID() {
		return AE_SYSTEM_USER_ID;
	}
	public void setAE_SYSTEM_USER_ID(String aESYSTEMUSERID) {
		AE_SYSTEM_USER_ID = aESYSTEMUSERID;
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

	public String getINACTIVE_USER() {
		return INACTIVE_USER;
	}

	public void setINACTIVE_USER(String iNACTIVEUSER) {
		INACTIVE_USER = iNACTIVEUSER;
	}

	/**
	 * @return the uSER_ORGANIZATION
	 */
	public String getUSER_ORGANIZATION() {
		return USER_ORGANIZATION;
	}

	/**
	 * @param uSER_ORGANIZATION the uSER_ORGANIZATION to set
	 */
	public void setUSER_ORGANIZATION(String uSER_ORGANIZATION) {
		USER_ORGANIZATION = uSER_ORGANIZATION;
	}

	/**
	 * @return the uSER_PHONE_NUMBER
	 */
	public String getUSER_PHONE_NUMBER() {
		return USER_PHONE_NUMBER;
	}

	/**
	 * @param uSER_PHONE_NUMBER the uSER_PHONE_NUMBER to set
	 */
	public void setUSER_PHONE_NUMBER(String uSER_PHONE_NUMBER) {
		USER_PHONE_NUMBER = uSER_PHONE_NUMBER;
	}

	/**
	 * @return the uSER_COMMENTS
	 */
	public String getUSER_COMMENTS() {
		return USER_COMMENTS;
	}

	/**
	 * @param uSER_COMMENTS the uSER_COMMENTS to set
	 */
	public void setUSER_COMMENTS(String uSER_COMMENTS) {
		USER_COMMENTS = uSER_COMMENTS;
	}

	/**
	 * @return the uSER_SIGNATURE
	 */
	public String getUSER_SIGNATURE() {
		return USER_SIGNATURE;
	}

	/**
	 * @param uSER_SIGNATURE the uSER_SIGNATURE to set
	 */
	public void setUSER_SIGNATURE(String uSER_SIGNATURE) {
		USER_SIGNATURE = uSER_SIGNATURE;
	}	
	
}
