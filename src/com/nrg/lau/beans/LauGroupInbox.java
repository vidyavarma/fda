package com.nrg.lau.beans;

import java.io.Serializable;

public class LauGroupInbox implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long EXTERNAL_DOCUMENT_ID = 0;
	private String DOCUMENT_NAME = "";
	private String DOCUMENT_DESCRIPTION = "";
	private String FILE_TYPE = "";
	private String FILE_NAME = "";
	private String OBJECT_ID = "";
	private String DOCUMENT_STATUS = "";
	private String DOCUMENT_URL = "";
	private String RECEIVED_DATE = "";
	private String RECEIVED_FROM = "";
	private String USER_GROUP_ID = "";
	
	public String getDOCUMENT_NAME() {
		return DOCUMENT_NAME;
	}
	public void setDOCUMENT_NAME(String dOCUMENTNAME) {
		DOCUMENT_NAME = dOCUMENTNAME;
	}
	public String getDOCUMENT_DESCRIPTION() {
		return DOCUMENT_DESCRIPTION;
	}
	public void setDOCUMENT_DESCRIPTION(String dOCUMENTDESCRIPTION) {
		DOCUMENT_DESCRIPTION = dOCUMENTDESCRIPTION;
	}
	public String getFILE_TYPE() {
		return FILE_TYPE;
	}
	public void setFILE_TYPE(String fILETYPE) {
		FILE_TYPE = fILETYPE;
	}
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILENAME) {
		FILE_NAME = fILENAME;
	}
	public String getOBJECT_ID() {
		return OBJECT_ID;
	}
	public void setOBJECT_ID(String oBJECTID) {
		OBJECT_ID = oBJECTID;
	}
	public String getDOCUMENT_STATUS() {
		return DOCUMENT_STATUS;
	}
	public void setDOCUMENT_STATUS(String dOCUMENTSTATUS) {
		DOCUMENT_STATUS = dOCUMENTSTATUS;
	}
	public String getDOCUMENT_URL() {
		return DOCUMENT_URL;
	}
	public void setDOCUMENT_URL(String dOCUMENTURL) {
		DOCUMENT_URL = dOCUMENTURL;
	}
	public String getRECEIVED_DATE() {
		return RECEIVED_DATE;
	}
	public void setRECEIVED_DATE(String rECEIVEDDATE) {
		RECEIVED_DATE = rECEIVEDDATE;
	}
	public String getRECEIVED_FROM() {
		return RECEIVED_FROM;
	}
	public void setRECEIVED_FROM(String rECEIVEDFROM) {
		RECEIVED_FROM = rECEIVEDFROM;
	}
	public String getUSER_GROUP_ID() {
		return USER_GROUP_ID;
	}
	public void setUSER_GROUP_ID(String uSERGROUPID) {
		USER_GROUP_ID = uSERGROUPID;
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer(LauGroupInbox.class.getName());
		str.append("[");
		str.append("EXTERNAL_DOCUMENT_ID=");
		str.append(EXTERNAL_DOCUMENT_ID);
		str.append(", DOCUMENT_NAME=");
		str.append(DOCUMENT_NAME);
		str.append(", DOCUMENT_DESCRIPTION=");
		str.append(DOCUMENT_DESCRIPTION);
		str.append(", FILE_TYPE=");
		str.append(FILE_TYPE);
		str.append(", FILE_NAME=");
		str.append(FILE_NAME);
		str.append(", OBJECT_ID=");
		str.append(OBJECT_ID);
		str.append(", DOCUMENT_STATUS=");
		str.append(DOCUMENT_STATUS);
		str.append(", DOCUMENT_URL=");
		str.append(DOCUMENT_URL);
		str.append(", RECEIVED_DATE=");
		str.append(RECEIVED_DATE);
		str.append(", RECEIVED_FROM=");
		str.append(RECEIVED_FROM);
		str.append(", USER_GROUP_ID=");
		str.append(USER_GROUP_ID);
		str.append("]");
		return str.toString();
	}
	public long getEXTERNAL_DOCUMENT_ID() {
		return EXTERNAL_DOCUMENT_ID;
	}
	public void setEXTERNAL_DOCUMENT_ID(long eXTERNALDOCUMENTID) {
		EXTERNAL_DOCUMENT_ID = eXTERNALDOCUMENTID;
	}
}
