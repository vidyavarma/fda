package com.nrg.lau.beans;

import java.io.Serializable;

public class LauDeviceProblemCodes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long DEVICE_REPORT_CODE_ID = 0;
	private long DEVICE_DETAILS_ID = 0;
	private long PRODUCT_ID = 0;
	private long REPORT_ID = 0;
	private String DISPLAY_NUMBER = "";
	private String CODING_SOURCE = "";
	private String DEVICE_REPORT_CODE_TYPE = "";
	private String DEVICE_REPORT_CODE_VALUE = "";
	private String UPDATE_USER_ID = "";
	private Object UPDATE_TIMESTAMP;
	private long transactionType = -1;
	private String productName = "";
	private String INTERNAL_LINK_ID="";
	
	public long getDEVICE_REPORT_CODE_ID() {
		return DEVICE_REPORT_CODE_ID;
	}
	public void setDEVICE_REPORT_CODE_ID(long dEVICE_REPORT_CODE_ID) {
		DEVICE_REPORT_CODE_ID = dEVICE_REPORT_CODE_ID;
	}
	public long getDEVICE_DETAILS_ID() {
		return DEVICE_DETAILS_ID;
	}
	public void setDEVICE_DETAILS_ID(long dEVICE_DETAILS_ID) {
		DEVICE_DETAILS_ID = dEVICE_DETAILS_ID;
	}
	public long getPRODUCT_ID() {
		return PRODUCT_ID;
	}
	public void setPRODUCT_ID(long pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}
	public long getREPORT_ID() {
		return REPORT_ID;
	}
	public void setREPORT_ID(long rEPORT_ID) {
		REPORT_ID = rEPORT_ID;
	}
	public String getDISPLAY_NUMBER() {
		return DISPLAY_NUMBER;
	}
	public void setDISPLAY_NUMBER(String dISPLAY_NUMBER) {
		DISPLAY_NUMBER = dISPLAY_NUMBER;
	}
	public String getCODING_SOURCE() {
		return CODING_SOURCE;
	}
	public void setCODING_SOURCE(String cODING_SOURCE) {
		CODING_SOURCE = cODING_SOURCE;
	}
	public String getDEVICE_REPORT_CODE_TYPE() {
		return DEVICE_REPORT_CODE_TYPE;
	}
	public void setDEVICE_REPORT_CODE_TYPE(String dEVICE_REPORT_CODE_TYPE) {
		DEVICE_REPORT_CODE_TYPE = dEVICE_REPORT_CODE_TYPE;
	}
	public String getDEVICE_REPORT_CODE_VALUE() {
		return DEVICE_REPORT_CODE_VALUE;
	}
	public void setDEVICE_REPORT_CODE_VALUE(String dEVICE_REPORT_CODE_VALUE) {
		DEVICE_REPORT_CODE_VALUE = dEVICE_REPORT_CODE_VALUE;
	}
	public String getUPDATE_USER_ID() {
		return UPDATE_USER_ID;
	}
	public void setUPDATE_USER_ID(String uPDATE_USER_ID) {
		UPDATE_USER_ID = uPDATE_USER_ID;
	}
	public Object getUPDATE_TIMESTAMP() {
		return UPDATE_TIMESTAMP;
	}
	public void setUPDATE_TIMESTAMP(Object uPDATE_TIMESTAMP) {
		UPDATE_TIMESTAMP = uPDATE_TIMESTAMP;
	}
	public long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getINTERNAL_LINK_ID() {
		return INTERNAL_LINK_ID;
	}
	public void setINTERNAL_LINK_ID(String iNTERNAL_LINK_ID) {
		INTERNAL_LINK_ID = iNTERNAL_LINK_ID;
	}	
	

}
