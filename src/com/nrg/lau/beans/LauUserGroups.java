package com.nrg.lau.beans;

import java.io.Serializable;

public class LauUserGroups implements Serializable {

	private static final long serialVersionUID 	= 1L;
	private String USER_GROUP_DESCRIPTION 		= "";
	private String VIEW_ALL_OTHERS_REPORTS 		= "";
	private String EDIT_ALL_OTHERS_REPORTS 		= "";
	private String PROMOTE_PRIVATE_DATA			= "";
	private String REPORT_ID_MODIFIER			= "";
	private String USER_GROUP_ID 				= "";
	private String TRANSACTION_TYPE 			= "";
	private String UPDATE_USER_ID 				= "";
	private String UPDATE_TIMESTAMP		 		= "";	
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauUserGroups.class.getName());
		str.append("[");
		str.append("USER_GROUP_DESCRIPTION=");
		str.append(USER_GROUP_DESCRIPTION);
		str.append(PROMOTE_PRIVATE_DATA);
		str.append(", PROMOTE_PRIVATE_DATA=");
		str.append(REPORT_ID_MODIFIER);
		str.append(", REPORT_ID_MODIFIER=");
		str.append(", VIEW_ALL_OTHERS_REPORTS=");
		str.append(VIEW_ALL_OTHERS_REPORTS);
		str.append(", EDIT_ALL_OTHERS_REPORTS=");
		str.append(EDIT_ALL_OTHERS_REPORTS);
		str.append(", USER_GROUP_ID=");
		str.append(USER_GROUP_ID);
		str.append(", TRANSACTION_TYPE=");
		str.append(TRANSACTION_TYPE);
		str.append(", UPDATE_USER_ID=");
		str.append(UPDATE_USER_ID);
		str.append(", UPDATE_TIMESTAMP=");
		str.append(UPDATE_TIMESTAMP);
		str.append("]");
		return str.toString();
	}	
	
	public String getUSER_GROUP_DESCRIPTION() {
		return USER_GROUP_DESCRIPTION;
	}
	public void setUSER_GROUP_DESCRIPTION(String uSERGROUPDESCRIPTION) {
		USER_GROUP_DESCRIPTION = uSERGROUPDESCRIPTION;
	}
	public String getVIEW_ALL_OTHERS_REPORTS() {
		return VIEW_ALL_OTHERS_REPORTS;
	}
	public void setVIEW_ALL_OTHERS_REPORTS(String vIEWALLOTHERSREPORTS) {
		VIEW_ALL_OTHERS_REPORTS = vIEWALLOTHERSREPORTS;
	}
	public String getEDIT_ALL_OTHERS_REPORTS() {
		return EDIT_ALL_OTHERS_REPORTS;
	}
	public void setEDIT_ALL_OTHERS_REPORTS(String eDITALLOTHERSREPORTS) {
		EDIT_ALL_OTHERS_REPORTS = eDITALLOTHERSREPORTS;
	}
	public String getUSER_GROUP_ID() {
		return USER_GROUP_ID;
	}
	public void setUSER_GROUP_ID(String uSERGROUPID) {
		USER_GROUP_ID = uSERGROUPID;
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

	public String getPROMOTE_PRIVATE_DATA() {
		return PROMOTE_PRIVATE_DATA;
	}

	public void setPROMOTE_PRIVATE_DATA(String pROMOTEPRIVATEDATA) {
		PROMOTE_PRIVATE_DATA = pROMOTEPRIVATEDATA;
	}

	public String getREPORT_ID_MODIFIER() {
		return REPORT_ID_MODIFIER;
	}

	public void setREPORT_ID_MODIFIER(String rEPORTIDMODIFIER) {
		REPORT_ID_MODIFIER = rEPORTIDMODIFIER;
	}
	
	

}
