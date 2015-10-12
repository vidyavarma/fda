package com.nrg.lau.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nrg.lau.commons.IReportsConstants;

public class LauExternalReferences implements Serializable{

	private static final long serialVersionUID = 1L;
	private long EXTERNAL_REFERENCE_ID = 0;
	private long REPORT_ID = 0;
	private BigDecimal DISPLAY_NUMBER;
	private String displayNumber2 = "";
	private String EXTERNAL_REFERENCE_TYPE = "";
	private String EXTERNAL_REFERENCE_NUMBER = "";
	private String EXTERNAL_REFERENCE_SOURCE = "";
	private String EXTERNAL_REF_REPORT_FORM ="";
	private String UPDATE_USER_ID = "";
	private Object UPDATE_TIMESTAMP;
	private long transactionType = -1;
	private String INTERNAL_LINK_ID="";
	
	public long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	public long getEXTERNAL_REFERENCE_ID() {
		return EXTERNAL_REFERENCE_ID;
	}
	public void setEXTERNAL_REFERENCE_ID(long eXTERNALREFERENCEID) {
		EXTERNAL_REFERENCE_ID = eXTERNALREFERENCEID;
	}
	public long getREPORT_ID() {
		return REPORT_ID;
	}
	public void setREPORT_ID(long rEPORTID) {
		REPORT_ID = rEPORTID;
	}
	public BigDecimal getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}
	public void setDISPLAY_NUMBER(BigDecimal dISPLAYNUMBER) {
		DISPLAY_NUMBER = dISPLAYNUMBER;
	}
	public String getDisplayNumber2() {
		return displayNumber2;
	}
	public void setDisplayNumber2(String displayNumber2) {
		if(displayNumber2.trim().length() > 0)	{
			this.DISPLAY_NUMBER = new BigDecimal(displayNumber2);
		}else this.DISPLAY_NUMBER = IReportsConstants.ZERO;
	}
	public String getEXTERNAL_REFERENCE_TYPE() {
		return EXTERNAL_REFERENCE_TYPE;
	}
	public void setEXTERNAL_REFERENCE_TYPE(String eXTERNALREFERENCETYPE) {
		EXTERNAL_REFERENCE_TYPE = eXTERNALREFERENCETYPE;
	}
	public String getEXTERNAL_REFERENCE_NUMBER() {
		return EXTERNAL_REFERENCE_NUMBER;
	}
	public void setEXTERNAL_REFERENCE_NUMBER(String eXTERNALREFERENCENUMBER) {
		EXTERNAL_REFERENCE_NUMBER = eXTERNALREFERENCENUMBER;
	}
	public String getEXTERNAL_REFERENCE_SOURCE() {
		return EXTERNAL_REFERENCE_SOURCE;
	}
	public void setEXTERNAL_REFERENCE_SOURCE(String eXTERNALREFERENCESOURCE) {
		EXTERNAL_REFERENCE_SOURCE = eXTERNALREFERENCESOURCE;
	}
	public String getUPDATE_USER_ID() {
		return UPDATE_USER_ID;
	}
	public void setUPDATE_USER_ID(String uPDATEUSERID) {
		UPDATE_USER_ID = uPDATEUSERID;
	}
	public Object getUPDATE_TIMESTAMP() {
		return UPDATE_TIMESTAMP;
	}
	public void setUPDATE_TIMESTAMP(Object uPDATETIMESTAMP) {
		UPDATE_TIMESTAMP = uPDATETIMESTAMP;
	}
	
	public String getEXTERNAL_REF_REPORT_FORM() {
		return EXTERNAL_REF_REPORT_FORM;
	}
	public void setEXTERNAL_REF_REPORT_FORM(String eXTERNAL_REF_REPORT_FORM) {
		EXTERNAL_REF_REPORT_FORM = eXTERNAL_REF_REPORT_FORM;
	}	
	
	public String getINTERNAL_LINK_ID() {
		return INTERNAL_LINK_ID;
	}
	public void setINTERNAL_LINK_ID(String iNTERNAL_LINK_ID) {
		INTERNAL_LINK_ID = iNTERNAL_LINK_ID;
	}
	public String toString() {
		StringBuffer str = new StringBuffer(LauExternalReferences.class.getName());
		str.append("[");	
		str.append("EXTERNAL_REFERENCE_ID=");
		str.append(EXTERNAL_REFERENCE_ID);
		str.append("REPORT_ID=");
		str.append(REPORT_ID);
		str.append("DISPLAY_NUMBER=");
		str.append(DISPLAY_NUMBER);
		str.append("EXTERNAL_REFERENCE_TYPE=");
		str.append(EXTERNAL_REFERENCE_TYPE);
		str.append("EXTERNAL_REFERENCE_NUMBER=");
		str.append(EXTERNAL_REFERENCE_NUMBER);
		str.append("EXTERNAL_REFERENCE_SOURCE=");
		str.append(EXTERNAL_REFERENCE_SOURCE);
		str.append("UPDATE_USER_ID=");
		str.append(UPDATE_USER_ID);
		str.append("UPDATE_TIMESTAMP=");
		str.append(UPDATE_TIMESTAMP);
		str.append("]");
		return str.toString();
	}

}
