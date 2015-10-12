package com.nrg.lau.beans;

import java.io.Serializable;

public class LauReferenceListDetails implements Serializable {

	private static final long serialVersionUID 	= 1L;
	private String REFERENCE_LIST_NAME 			= "";
	private String CODE 						= "";
	private String CODE_VALUE 					= "";
	private String LANGUAGE_CODE 				= "";
	private int DISPLAY_NUMBER 					= 1;
	private String VISIBLE_FLAG 				= "Y";
	private String UPDATE_USER_ID 				= "";
	private String UPDATE_TIMESTAMP 			= "";
	private String TRANSACTION_TYPE 			= "";
	private String VALUE_DESCRIPTION			= "";
	private String PARENT_CODE					= "";
	private String RETIRED_FLAG					= "";
	private String IMPORT_VALUE_FLAG			= "";
	private String CONTEXT_CODE					= "";
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauReferenceListDetails.class.getName());
		str.append("[");
		str.append("REFERENCE_LIST_NAME=");
		str.append(REFERENCE_LIST_NAME);
		str.append(", CODE=");
		str.append(CODE);
		str.append(", CODE_VALUE=");
		str.append(CODE_VALUE);
		str.append(", LANGUAGE_CODE=");
		str.append(LANGUAGE_CODE);
		str.append(", DISPLAY_NUMBER=");
		str.append(DISPLAY_NUMBER);
		str.append(", VISIBLE_FLAG=");
		str.append(VISIBLE_FLAG);
		str.append(", RETIRED_FLAG=");
		str.append(RETIRED_FLAG);
		str.append(", PARENT_CODE=");
		str.append(PARENT_CODE);
		str.append(", IMPORT_VALUE_FLAG=");
		str.append(IMPORT_VALUE_FLAG);
		str.append(", CONTEXT_CODE=");
		str.append(CONTEXT_CODE);
		str.append(", VALUE_DESCRIPTION=");
		str.append(VALUE_DESCRIPTION);
		str.append(", UPDATE_USER_ID=");
		str.append(UPDATE_USER_ID);
		str.append(", UPDATE_TIMESTAMP=");
		str.append(UPDATE_TIMESTAMP);
		str.append(", TRANSACTION_TYPE=");
		str.append(TRANSACTION_TYPE);
		str.append("]");
		return str.toString();
	}

	public String getVALUE_DESCRIPTION() {
		return VALUE_DESCRIPTION;
	}

	public void setVALUE_DESCRIPTION(String vALUE_DESCRIPTION) {
		VALUE_DESCRIPTION = vALUE_DESCRIPTION;
	}

	public String getPARENT_CODE() {
		return PARENT_CODE;
	}

	public void setPARENT_CODE(String pARENT_CODE) {
		PARENT_CODE = pARENT_CODE;
	}

	public String getRETIRED_FLAG() {
		return RETIRED_FLAG;
	}

	public void setRETIRED_FLAG(String rETIRED_FLAG) {
		RETIRED_FLAG = rETIRED_FLAG;
	}

	public String getIMPORT_VALUE_FLAG() {
		return IMPORT_VALUE_FLAG;
	}

	public void setIMPORT_VALUE_FLAG(String iMPORT_VALUE_FLAG) {
		IMPORT_VALUE_FLAG = iMPORT_VALUE_FLAG;
	}

	public String getCONTEXT_CODE() {
		return CONTEXT_CODE;
	}

	public void setCONTEXT_CODE(String cONTEXT_CODE) {
		CONTEXT_CODE = cONTEXT_CODE;
	}

	public String getREFERENCE_LIST_NAME() {
		return REFERENCE_LIST_NAME;
	}

	public void setREFERENCE_LIST_NAME(String rEFERENCELISTNAME) {
		REFERENCE_LIST_NAME = rEFERENCELISTNAME;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getCODE_VALUE() {
		return CODE_VALUE;
	}

	public void setCODE_VALUE(String cODEVALUE) {
		CODE_VALUE = cODEVALUE;
	}

	public String getLANGUAGE_CODE() {
		return LANGUAGE_CODE;
	}

	public void setLANGUAGE_CODE(String lANGUAGECODE) {
		LANGUAGE_CODE = lANGUAGECODE;
	}

	public int getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}

	public void setDISPLAY_NUMBER(int dISPLAYNUMBER) {
		DISPLAY_NUMBER = dISPLAYNUMBER;
	}

	public String getVISIBLE_FLAG() {
		return VISIBLE_FLAG;
	}

	public void setVISIBLE_FLAG(String vISIBLEFLAG) {
		VISIBLE_FLAG = vISIBLEFLAG;
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

	public String getTRANSACTION_TYPE() {
		return TRANSACTION_TYPE;
	}

	public void setTRANSACTION_TYPE(String tRANSACTIONTYPE) {
		TRANSACTION_TYPE = tRANSACTIONTYPE;
	}
	
}
