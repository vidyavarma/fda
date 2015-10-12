package com.nrg.lau.beans;

import java.io.Serializable;

public class LauReferenceLists implements Serializable {

	private static final long serialVersionUID 	= 1L;
	
	private String REFERENCE_LIST_TYPE 			= "";
	private String DESCRIPTION 					= "";
	private String EXTERNAL_LIST_NAME 			= "";
	private String LIST_SQL 					= "";
	private String UPDATE_USER_ID 				= "";
	private String UPDATE_TIMESTAMP 			= "";
	private String REFERENCE_LIST_NAME 			= "";
	private String TRANSACTION_TYPE 			= "";
		
	public String toString() {
		StringBuffer str = new StringBuffer(LauReferenceLists.class.getName());
		str.append("[");
		str.append("REFERENCE_LIST_TYPE=");
		str.append(REFERENCE_LIST_TYPE);
		str.append(", DESCRIPTION=");
		str.append(DESCRIPTION);
		str.append(", EXTERNAL_LIST_NAME=");
		str.append(EXTERNAL_LIST_NAME);
		str.append(", LIST_SQL=");
		str.append(LIST_SQL);
		str.append(", UPDATE_USER_ID=");
		str.append(UPDATE_USER_ID);
		str.append(", UPDATE_TIMESTAMP=");
		str.append(UPDATE_TIMESTAMP);
		str.append(", REFERENCE_LIST_NAME=");
		str.append(REFERENCE_LIST_NAME);
		str.append(", TRANSACTION_TYPE=");
		str.append(TRANSACTION_TYPE);
		str.append("]");
		return str.toString();
	}
	
	public String getREFERENCE_LIST_TYPE() {
		return REFERENCE_LIST_TYPE;
	}

	public void setREFERENCE_LIST_TYPE(String rEFERENCELISTTYPE) {
		REFERENCE_LIST_TYPE = rEFERENCELISTTYPE;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getEXTERNAL_LIST_NAME() {
		return EXTERNAL_LIST_NAME;
	}

	public void setEXTERNAL_LIST_NAME(String eXTERNALLISTNAME) {
		EXTERNAL_LIST_NAME = eXTERNALLISTNAME;
	}

	public String getLIST_SQL() {
		return LIST_SQL;
	}

	public void setLIST_SQL(String lISTSQL) {
		LIST_SQL = lISTSQL;
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

	public String getREFERENCE_LIST_NAME() {
		return REFERENCE_LIST_NAME;
	}

	public void setREFERENCE_LIST_NAME(String rEFERENCELISTNAME) {
		REFERENCE_LIST_NAME = rEFERENCELISTNAME;
	}

	public String getTRANSACTION_TYPE() {
		return TRANSACTION_TYPE;
	}

	public void setTRANSACTION_TYPE(String tRANSACTIONTYPE) {
		TRANSACTION_TYPE = tRANSACTIONTYPE;
	}

}
